package se.powerslidestudios.scenes;

import java.util.ArrayList;
import java.util.List;

import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneState;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.ui.UIElement;
import se.skoggy.ui.UiFactory;
import se.skoggy.utils.Camera2D;

import com.badlogic.gdx.math.Interpolation;

public class HelpPopup extends Scene{

	Camera2D uiCam;
	
	List<UIElement> elements;
	UiFactory uiFactory;
	DialogResult result;
	
	Entity helpBox;
	
	public HelpPopup(IGameContext context) {
		super(context);
	}
	
	@Override
	public boolean isPopup() {
		return true;
	}

	@Override
	public void load() {
		super.load();

		uiCam = new Camera2D(width, height);
		uiCam.setPosition(width/2, height/2);
		uiCam.update();
		
		uiFactory = new UiFactory(content(), tweens);
		elements = new ArrayList<UIElement>();
		
		helpBox = new Entity(content().loadTexture("gfx/help_box"));
		helpBox.setScale(0.6f);
		helpBox.setPosition(width * 0.5f, height * 0.5f);
		
		setupUI();
	}
	
	@Override
	public float transitionOutDuration() {
		return 300f;
	}
	
	@Override
	public float transitionInDuration() {
		return 1000f;
	}
	
	private void close(){

		TouchButton btnYes = (TouchButton)elements.get(0);
		
		tween(new ScaleXYTween(
				btnYes, 
				Interpolation.pow2, transitionOutDuration(), 
				1f, 0f));
		
		setState(SceneState.TransitionOut);
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
	}
	
	private void setupUI() {
		
		
		
		TouchButton btnYes = uiFactory.createRoundIconButton("ok", "green", 600);

		btnYes.setPosition(width * 0.5f, height * 0.8f);

		elements.add(btnYes);
		
		btnYes.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				result = DialogResult.Yes;
				close();
			}
		});
	}

	@Override
	public void update(float dt) {

		if(isActive()){
			
			for (UIElement element : elements) {
				element.update(dt);
			}
		}
		
		uiCam.update();
		super.update(dt);
	}
	
	@Override
	public void draw() {
		spriteBatch.setProjectionMatrix(uiCam.combined);
		spriteBatch.begin();
		helpBox.draw(spriteBatch);
		for (UIElement element : elements) {
			element.draw(uiFactory.getFont(), spriteBatch);		
		}		
		spriteBatch.end();
	}
}
