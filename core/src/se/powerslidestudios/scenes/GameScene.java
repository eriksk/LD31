package se.powerslidestudios.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

import se.skoggy.audio.IAudio;
import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.tweens.stock.AlphaTween;
import se.skoggy.tweens.stock.BackAndForthInterpolation;
import se.skoggy.tweens.stock.PositionXYTween;
import se.skoggy.tweens.stock.RotationTween;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.utils.ServiceLocator;
import se.skoggy.utils.Timeline;
import se.skoggy.utils.TimelineEvent;

public class GameScene extends GuiScene{

	Entity logo;
	
	public GameScene(IGameContext context) {
		super(context);
	}
	
	@Override
	public void load() {
		super.load();
	
		logo = new Entity(content().loadTexture("gfx/ludum_dare"));
		tween(new AlphaTween(logo, Interpolation.linear, 2000f, 0f, 1f));
		
	}
	
	@Override
	public float transitionInDuration() {
		return 500f;
	}
	
	@Override
	public float transitionOutDuration() {
		return 1000f;
	}
	
	public void close() {
		manager.popScene();
	}
	
	@Override
	public void beforeRemoved() {
		super.beforeRemoved();
		manager.pushScene(new MenuScene(context));
	}
	
	@Override
	protected void createUi() {
		TouchButton btnSettings = uiFactory.createRoundIconButton("cross", "yellow", transitionInDuration());
	
		btnSettings.setPosition(width * 0.9f, height * 0.2f);
		
		btnSettings.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				manager.pushScene(new AreYouSureDialogScene(context, new DialogResultListener() {
					@Override
					public void onClose(DialogResult result) {
						if(result == DialogResult.Yes){
							close();
						}
					}
				}));
			}
		});
		
		elements.add(btnSettings);
	}
	
	@Override
	public void update(float dt) {
	
		logo.update(dt);
		super.update(dt);
	}

	@Override
	public void draw() {
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		logo.draw(spriteBatch);
		spriteBatch.end();
		super.draw();
		
	}
	
	@Override
	public void drawTransitionOut(float progress) {
		super.drawTransitionOut(progress);
		draw();
	}
}
