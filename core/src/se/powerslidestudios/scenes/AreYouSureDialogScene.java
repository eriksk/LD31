package se.powerslidestudios.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.tweens.stock.PositionXYTween;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.ui.Label;
import se.skoggy.ui.MessageBox;
import se.skoggy.ui.TextAlign;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.ui.UIElement;
import se.skoggy.ui.UiFactory;
import se.skoggy.utils.Camera2D;
import se.skoggy.utils.GLHelpers;

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

		uiCam = new Camera2D(width, height, null);
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
	
	private void close(){

		MessageBox box = (MessageBox)elements.get(0);
		TouchButton btnYes = (TouchButton)elements.get(1);
		TouchButton btnNo = (TouchButton)elements.get(2);
		Label label = (Label)elements.get(3);
		

		tween(new PositionXYTween(
				btnYes, 
				Interpolation.pow2, transitionOutDuration(), 
				btnYes.transform.position.x, btnYes.transform.position.y, 
				-200, btnYes.transform.position.y));
		
		tween(new PositionXYTween(
				btnNo, 
				Interpolation.pow2, transitionOutDuration(), 
				btnNo.transform.position.x, btnNo.transform.position.y, 
				width + 200, btnNo.transform.position.y));
		
		tween(new PositionXYTween(
				box, 
				Interpolation.pow2, transitionOutDuration(), 
				box.transform.position.x, box.transform.position.y, 
				width * 0.5f, box.transform.position.y - height));
		
		tween(new ScaleXYTween(
				label, 
				Interpolation.pow2, transitionOutDuration(), 
				1f, 0f));
		
		manager.popScene();
	}
	
	@Override
	public void beforeRemoved() {
		resultCallback.onClose(result);
	}
	
	
	private void setupUI() {
		
		se.skoggy.ui.Label label = uiFactory.createLabel("Are you sure?");
		
		MessageBox box = uiFactory.createMessageBox(new Vector2(width * 0.5f, height * 0.5f));
		
		TouchButton btnYes = uiFactory.createRoundIconButton("arrow_left", "green");
		TouchButton btnNo = uiFactory.createRoundIconButton("arrow_right", "red");

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
	
		for (UIElement element : elements) {
			element.update(dt);
		}
		
		uiCam.update();
		super.update(dt);
	}
	
	@Override
	public void updateTransitionOut(float dt, float progress) {
		super.update(dt);
		super.updateTransitionOut(dt, progress);
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
	
	@Override
	public void drawTransitionOut(float progress) {	
		super.drawTransitionOut(progress);
		spriteBatch.setProjectionMatrix(uiCam.combined);
		spriteBatch.begin();
		for (UIElement element : elements) {
			element.draw(uiFactory.getFont(), spriteBatch);		
		}		
		spriteBatch.end();
	}
}
