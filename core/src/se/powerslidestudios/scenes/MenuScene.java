package se.powerslidestudios.scenes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;

import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneState;
import se.skoggy.tweens.ITweenable;
import se.skoggy.tweens.Tween;
import se.skoggy.tweens.stock.AlphaTween;
import se.skoggy.tweens.stock.BackAndForthInterpolation;
import se.skoggy.tweens.stock.PositionXYTween;
import se.skoggy.tweens.stock.ScaleXTween;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.tweens.stock.ScaleYTween;
import se.skoggy.ui.Text;
import se.skoggy.ui.TextAlign;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.ui.UIElement;
import se.skoggy.ui.UiFactory;
import se.skoggy.utils.Camera2D;

public class MenuScene extends GuiScene{
	
	Text gameTitle;
	BitmapFont titleFont;
		
	public MenuScene(IGameContext context) {
		super(context);
	}

	@Override
	public void load() {
		super.load();
				
		
		titleFont = content().loadFont("universal_fruitcake_64");
		gameTitle = new Text("Game Title!", TextAlign.center);
		
		tween(new ScaleXTween(gameTitle, Interpolation.elasticOut, 1000f, 0f, 2f).setWait(200f));
		tween(new ScaleYTween(gameTitle, Interpolation.elasticOut, 1000f, 1f, 2f));
		tween(new PositionXYTween(gameTitle, Interpolation.pow2, 300f, width * 0.5f, height * -0.25f, width * 0.5f, height * 0.35f));
		
	}
	
	@Override
	public float transitionOutDuration() {
		return 500f;
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
		if(state == SceneState.Done){
			manager.pushScene(new GameScene(context));
		}
	}
	
	@Override
	protected void createUi() {
		final TouchButton btnPlay = uiFactory.createRoundIconButton("play", "yellow", 1000f);
		final TouchButton btnRestart = uiFactory.createRoundIconButton("restart", "yellow", 1000f);
		final TouchButton btnSettings = uiFactory.createRoundIconButton("settings", "yellow", 1000f);

		btnPlay.setPosition(width * 0.25f, height * 0.75f);
		btnRestart.setPosition(width * 0.5f, height * 0.75f);
		btnSettings.setPosition(width * 0.75f, height * 0.75f);
				
		elements.add(btnPlay);
		elements.add(btnRestart);
		elements.add(btnSettings);
				
		btnSettings.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				manager.pushPopup(new AreYouSureDialogScene(context, new DialogResultListener() {
					@Override
					public void onClose(DialogResult result) {
						if(result == DialogResult.Yes){
							context.exit();
						}
					}
				}));
			}
		});
		
		btnPlay.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				setState(SceneState.TransitionOut);
				tween(new ScaleXYTween(btnPlay, Interpolation.pow2, transitionOutDuration(), 1f, 0f));
				tween(new ScaleXYTween(btnRestart, Interpolation.pow2, transitionOutDuration(), 1f, 0f));
				tween(new ScaleXYTween(btnSettings, Interpolation.pow2, transitionOutDuration(), 1f, 0f));
			}
		});	
	}

	@Override
	public void update(float dt) {	
		super.update(dt);
	}
	
		
	@Override
	public void draw() {	
		spriteBatch.setProjectionMatrix(uiCam.combined);
		spriteBatch.begin();
		for (UIElement uiElement : elements) {
			uiElement.draw(uiFactory.getFont(), spriteBatch);
		}
		gameTitle.draw(titleFont, spriteBatch);
		spriteBatch.end();
	}
}
