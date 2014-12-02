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

public class MenuScene extends Scene{

	Camera2D uiCam;
	UiFactory uiFactory;
	
	List<UIElement> elements;
	
	Text gameTitle;
	BitmapFont titleFont;
		
	public MenuScene(IGameContext context) {
		super(context);
	}

	@Override
	public void load() {
		super.load();
		uiCam = new Camera2D(context.width(), context.height(), null);
		uiCam.setPosition(0, 0);
		
		uiFactory = new UiFactory(content(), tweens);
		elements = new ArrayList<UIElement>();
				
		
		titleFont = content().loadFont("universal_fruitcake_64");
		gameTitle = new Text("Game Title!", TextAlign.center);
		
		tween(new ScaleXTween(gameTitle, Interpolation.elasticOut, 1000f, 0f, 2f).setWait(200f));
		tween(new ScaleYTween(gameTitle, Interpolation.elasticOut, 1000f, 1f, 2f));
		tween(new PositionXYTween(gameTitle, Interpolation.pow2, 300f, 0f, -400f, 0f, -100f));
		
		createUi();
		
		
	}
	
	private void createUi() {

		TouchButton btnPlay = uiFactory.createRoundIconButton("play", "yellow");
		TouchButton btnRestart = uiFactory.createRoundIconButton("restart", "yellow");
		TouchButton btnSettings = uiFactory.createRoundIconButton("settings", "yellow");

		btnPlay.setPositionX(this.getWidth()* -0.25f);
		btnSettings.setPositionX(this.getWidth() * 0.25f);

		btnPlay.setPositionY(getHeight() * 0.25f);
		btnRestart.setPositionY(getHeight() * 0.25f);
		btnSettings.setPositionY(getHeight() * 0.25f);
		
		elements.add(btnPlay);
		elements.add(btnRestart);
		elements.add(btnSettings);
				
		btnSettings.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				context.changeScene(new AreYouSureDialogScene(context, new DialogResultListener() {
					@Override
					public void onClose(DialogResult result) {
						if(result == DialogResult.Yes){
							context.exit();
						}
					}
				}));
			}
		});
	}

	@Override
	public void update(float dt) {
	
		for (UIElement uiElement : elements) {
			uiElement.update(dt);
		}
		
		uiCam.update();
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
