package se.powerslidestudios.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;

import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneState;
import se.skoggy.tweens.stock.AlphaTween;

public class SplashScreen extends Scene{
	
	Entity logo;
	private String logoName;
	private Scene gotoScreen;

	public SplashScreen(IGameContext context, String logoName, Scene gotoScreen) {
		super(context);
		this.logoName = logoName;
		this.gotoScreen = gotoScreen;
	}
	
	@Override
	public void load() {
		super.load();
	
		logo = new Entity(content().loadTexture("gfx/" + logoName));
		logo.color.a = 0;
		
		tween(new AlphaTween(logo, Interpolation.pow2, 2000f, 0f, 1f));
	}
	
	@Override
	public float transitionInDuration() {
		return 2000f;
	}
	
	@Override
	public float transitionOutDuration() {
		return 1000f;
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
		if(state == SceneState.TransitionOut){
			tween(new AlphaTween(logo, Interpolation.pow2, transitionOutDuration(), 1f, 0f));
		}
		if(state == SceneState.Done){
			manager.pushScene(gotoScreen);
		}
	}
	
	@Override
	public void update(float dt) {
		
		if(isActive()){
			setState(SceneState.TransitionOut);
		}
		
		
		logo.update(dt);
		
		super.update(dt);
	}
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		logo.draw(spriteBatch);
		spriteBatch.end();
	}
}
