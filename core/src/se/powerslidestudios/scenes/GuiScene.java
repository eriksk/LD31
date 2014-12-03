package se.powerslidestudios.scenes;

import java.util.ArrayList;
import java.util.List;

import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.ui.UIElement;
import se.skoggy.ui.UiFactory;
import se.skoggy.utils.Camera2D;

public abstract class GuiScene extends Scene{

	Camera2D uiCam;
	protected UiFactory uiFactory;
	protected List<UIElement> elements;
	
	public GuiScene(IGameContext context) {
		super(context);
	}
	
	@Override
	public void load() {
		super.load();

		uiCam = new Camera2D(width, height, null);
		uiCam.setPosition(width/2, height/2);
		
		uiFactory = new UiFactory(content(), tweens);
		elements = new ArrayList<UIElement>();
	
		createUi();
	}

	protected abstract void createUi();
	
	@Override
	public void update(float dt) {

		if(isActive()){
			for (UIElement uiElement : elements) {
				uiElement.update(dt);
			}
		}
		
		uiCam.update();
		super.update(dt);
	}

	@Override
	public void draw() {
		spriteBatch.setProjectionMatrix(uiCam.combined);
		spriteBatch.begin();
		for (UIElement uiElement : elements)
			uiElement.draw(uiFactory.getFont(), spriteBatch);	
		spriteBatch.end();		
	}
}
