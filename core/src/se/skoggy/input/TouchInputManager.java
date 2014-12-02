package se.skoggy.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TouchInputManager implements ITouchInput{

	TouchState state, oldState;
	
	public TouchInputManager() {
		state= new TouchState();
		oldState = new TouchState();
	}

	@Override
	public float x() {
		return state.x;
	}

	@Override
	public float y() {
		return state.y;
	}
	
	public boolean tapped(){
		return !state.touched && oldState.touched;
	}

	public boolean pressed(){
		return state.touched && !oldState.touched;
	}
	
	public void update(int width, int height){
		oldState.set(state);
		state.touched = Gdx.input.isTouched();
		
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
				
		// this is resolution independent as long as cam used is centered
		state.x = ((x / Gdx.graphics.getWidth()) * (float)width) - (float)width / 2f;
		state.y = ((y / Gdx.graphics.getHeight()) * (float)height) - (float)height / 2f;
	}
}
