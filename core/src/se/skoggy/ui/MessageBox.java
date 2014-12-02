package se.skoggy.ui;

import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MessageBox extends Entity implements UIElement{

	public MessageBox(TextureAtlas uiAtlas) {
		super(uiAtlas.getTexture("textbox_small"));
	}

	@Override
	public void draw(BitmapFont font, SpriteBatch spriteBatch) {
		super.draw(spriteBatch);		
	}
}
