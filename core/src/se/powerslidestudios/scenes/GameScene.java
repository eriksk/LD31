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
import se.skoggy.utils.ServiceLocator;
import se.skoggy.utils.Timeline;
import se.skoggy.utils.TimelineEvent;

public class GameScene extends Scene{

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
	public void update(float dt) {
	
		logo.update(dt);
		
		if(Gdx.input.isTouched()){
			tween(new ScaleXYTween(logo, new BackAndForthInterpolation(Interpolation.pow2), 300f, 1, 0.8f));
		}
		
		super.update(dt);
	}

	@Override
	public void draw() {
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		logo.draw(spriteBatch);
		spriteBatch.end();
	}
}
