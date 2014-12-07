package se.powerslidestudios.scenes;

import se.powerslidestudios.ld31.particles.backgrounds.Starfield;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.SceneState;
import se.skoggy.tweens.stock.PositionXYTween;
import se.skoggy.tweens.stock.ScaleXTween;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.tweens.stock.ScaleYTween;
import se.skoggy.ui.Text;
import se.skoggy.ui.TextAlign;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.ui.UIElement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;

public class MenuScene extends GuiScene{
	
	Text gameTitle;
	BitmapFont titleFont;
	
	Starfield starfield;
	private boolean showInitialHelp;
		
	public MenuScene(IGameContext context, boolean showInitialHelp) {
		super(context);
		this.showInitialHelp = showInitialHelp;
	}

	@Override
	public void load() {
		super.load();
				
		
		titleFont = content().loadFont("xirod_64");
		gameTitle = new Text("SPACE CARGO", TextAlign.center);
		
		tween(new ScaleXTween(gameTitle, Interpolation.elasticOut, 1000f, 0f, 1f).setWait(200f));
		tween(new ScaleYTween(gameTitle, Interpolation.elasticOut, 1000f, 0f, 1f));
		tween(new PositionXYTween(gameTitle, Interpolation.pow2, 300f, width * 0.5f, height * -0.25f, width * 0.5f, height * 0.35f));
		
		starfield = new Starfield();
		starfield.load(content());
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
		if(state == SceneState.Active){
			if(showInitialHelp){
				manager.pushPopup(new HelpPopup(context));
			}
		}
	}
	
	@Override
	protected void createUi() {
		final TouchButton btnPlay = uiFactory.createRoundIconButton("play", "yellow", 1000f);
//		final TouchButton btnRestart = uiFactory.createRoundIconButton("restart", "yellow", 1000f);
		final TouchButton btnSettings = uiFactory.createRoundIconButton("cross", "red", 1000f);

		btnPlay.setPosition(width * 0.25f, height * 0.75f);
		//btnRestart.setPosition(width * 0.5f, height * 0.75f);
		btnSettings.setPosition(width * 0.75f, height * 0.75f);
				
		elements.add(btnPlay);
		//elements.add(btnRestart);
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
				//tween(new ScaleXYTween(btnRestart, Interpolation.pow2, transitionOutDuration(), 1f, 0f));
				tween(new ScaleXYTween(btnSettings, Interpolation.pow2, transitionOutDuration(), 1f, 0f));
			}
		});	
	}

	@Override
	public void update(float dt) {	
		starfield.update(dt);
		super.update(dt);
	}
	
		
	@Override
	public void draw() {	
		spriteBatch.setProjectionMatrix(uiCam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		starfield.draw(spriteBatch, cam);
		
		spriteBatch.begin();

		for (UIElement uiElement : elements) {
			uiElement.draw(uiFactory.getFont(), spriteBatch);
		}
		gameTitle.draw(titleFont, spriteBatch);
		spriteBatch.end();
	}
}
