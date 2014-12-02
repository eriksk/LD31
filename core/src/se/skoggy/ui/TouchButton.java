package se.skoggy.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import se.skoggy.atlases.DynamicTexture;
import se.skoggy.entity.Entity;
import se.skoggy.input.ITouchInput;
import se.skoggy.utils.ServiceLocator;

public class TouchButton extends Entity implements UIElement{

	List<TouchButtonEventListener> listeners;
	private Entity icon;
	
	// TODO: derive and create touchbuttonwithicon and touchbuttonwithtext.
	
	public TouchButton(DynamicTexture texture, DynamicTexture iconTexture) {
		super(texture);
		listeners = new ArrayList<TouchButtonEventListener>();
		icon = new Entity(iconTexture);
	}
	
	public void addListener(TouchButtonEventListener listener) {
		listeners.add(listener);
	}

	private void clicked() {
		for (TouchButtonEventListener l : listeners) {
			l.clicked(this);
		}
	}
	
	public Entity getIcon(){
		return icon;
	}

	/**
	 * Scaled collision check
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean contains(float x, float y) {
		float width = width() * transform.scale.x;
		float height = height() * transform.scale.y;
		
		float posX = transform.position.x - width / 2f;
		float posY = transform.position.y - height / 2f;
		
		if(x < posX) return false;
		if(y < posY) return false;
		if(x > posX + width) return false;
		if(y > posY + height) return false;
		
		return true;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		ITouchInput touch = ServiceLocator.context.locate(ITouchInput.class);
		
		if(touch.pressed() && contains(touch.x(), touch.y())){
			clicked();
		}
	}
	
	@Override
	public void draw(BitmapFont font, SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
		
		icon.setScale(transform.scale.x, transform.scale.y);
		icon.setPosition(transform.position.x, transform.position.y);
		icon.draw(spriteBatch);
	}
}
