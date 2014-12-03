package se.powerslidestudios.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneState;

public class WaitScene extends Scene{

	private float waitTime;
	private Scene gotoScene;

	public WaitScene(IGameContext context, float waitTime, Scene gotoScene) {
		super(context);
		this.waitTime = waitTime;
		this.gotoScene = gotoScene;
	}
	
	@Override
	public float transitionInDuration() {
		return waitTime / 2f;
	}
	
	@Override
	public float transitionOutDuration() {
		return waitTime / 2f;
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
	
		if(state == SceneState.Done){
			manager.pushScene(gotoScene);
		}
	}

	@Override
	public void update(float dt) {
	
		if(isActive())
			setState(SceneState.TransitionOut);
		
		super.update(dt);
	}
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
	}
}
