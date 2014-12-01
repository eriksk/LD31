package se.powerslidestudios.scenes;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

import se.skoggy.audio.IAudio;
import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.tweens.stock.PositionXYTween;
import se.skoggy.tweens.stock.RotationTween;
import se.skoggy.tweens.stock.ScaleXYTween;
import se.skoggy.utils.ServiceLocator;
import se.skoggy.utils.Timeline;
import se.skoggy.utils.TimelineEvent;

public class GameScene extends Scene{

	Entity entity;
	Timeline timeline;
	
	public GameScene(IGameContext context) {
		super(context);
	}
	
	@Override
	public void load() {
		super.load();
	
		entity = new Entity(content().loadTexture("gfx/badlogic", ".jpg"));

		timeline = new Timeline()
		.add(0f, 1000f, new TimelineEvent(){
			@Override
			public void onEvent() {
				tween(new PositionXYTween(entity, Interpolation.bounceOut, 1000, 0f, -200f, 0f, 0f));
			}
		}).add(0f, 2000f, new TimelineEvent() {
			@Override
			public void onEvent() {
				tween(new RotationTween(entity, Interpolation.exp10Out, 2000, 0f, MathUtils.degreesToRadians * 360f));
			}
		});
		
		//ServiceLocator.context.locate(IAudio.class).play("blip");
		
		
		timeline.start();
	}
	
	@Override
	public void update(float dt) {
	
		
		entity.update(dt);
		
		timeline.update(dt);
		
		super.update(dt);
	}

	@Override
	public void draw() {
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		entity.draw(spriteBatch);
		spriteBatch.end();
	}
}
