package se.powerslidestudios.scenes;

import java.util.ArrayList;
import java.util.List;

import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneState;
import se.skoggy.tweens.stock.PositionXYTween;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.ui.Label;
import se.skoggy.ui.MessageBox;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.ui.UIElement;
import se.skoggy.ui.UiFactory;
import se.skoggy.utils.Camera2D;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class AreYouSureDialogScene extends Scene{

	Camera2D uiCam;
	
	DialogResultListener resultCallback;
	List<UIElement> elements;
	UiFactory uiFactory;
	DialogResult result;
		
	public AreYouSureDialogScene(IGameContext context, DialogResultListener resultCallback) {
		super(context);
		
		if(resultCallback == null) throw new RuntimeException("argument null, resultCallback");
		
		this.resultCallback = resultCallback;
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

		MessageBox box = (MessageBox)elements.get(0);
		TouchButton btnYes = (TouchButton)elements.get(1);
		TouchButton btnNo = (TouchButton)elements.get(2);
		Label label = (Label)elements.get(3);
		
		tween(new ScaleXYTween(
				btnYes, 
				Interpolation.pow2, transitionOutDuration(), 
				1f, 0f));
		
		tween(new ScaleXYTween(
				btnNo, 
				Interpolation.pow2, transitionOutDuration(), 
				1f, 0f));
		
		tween(new PositionXYTween(
				box, 
				Interpolation.pow2, transitionOutDuration(), 
				box.transform.position.x, box.transform.position.y, 
				width * 0.5f, box.transform.position.y - height));
		
		tween(new ScaleXYTween(
				label, 
				Interpolation.linear, transitionOutDuration() * 0.5f, 
				1f, 0f));
		
		setState(SceneState.TransitionOut);
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
	
		if(state == SceneState.Done){
			resultCallback.onClose(result);
		}
	}
	
	private void setupUI() {
		
		se.skoggy.ui.Label label = uiFactory.createLabel("Are you sure?", 200f, 200f);
		label.setScale(0f);
		
		MessageBox box = uiFactory.createMessageBox(new Vector2(width * 0.5f, height * 0.5f), 300f);
		
		TouchButton btnYes = uiFactory.createRoundIconButton("ok", "green", 600);
		TouchButton btnNo = uiFactory.createRoundIconButton("cross", "red", 600);

		label.setPosition(width * 0.5f, height * 0.5f);
		btnYes.setPosition(width * 0.3f, height * 0.72f);
		btnNo.setPosition(width * 0.7f, height * 0.72f);

		elements.add(box);
		elements.add(btnYes);
		elements.add(btnNo);
		elements.add(label);
		
		btnYes.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				result = DialogResult.Yes;
				close();
			}
		});

		btnNo.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				result = DialogResult.No;
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
		for (UIElement element : elements) {
			element.draw(uiFactory.getFont(), spriteBatch);		
		}		
		spriteBatch.end();
	}
}
