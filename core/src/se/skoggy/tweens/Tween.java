package se.skoggy.tweens;

import com.badlogic.gdx.math.Interpolation;

public abstract class Tween {

	private ITweenable subject;
	private Interpolation interpolation;
	private float duration;
	private float current;
	
	public Tween(ITweenable subject, Interpolation interpolation, float duration) {
		this.subject = subject;
		this.interpolation = interpolation;
		this.duration = duration;
	}
	
	public abstract void tween(float progress, ITweenable subject, Interpolation interpolation);
	
	public boolean done(){
		return current >= duration;
	}
	
	public float progress(){
		return current / duration;
	}
	
	public void update(float dt){
		current += dt;
		if(current > duration)
			current = duration;
		
		tween(progress(), subject, interpolation);
	}
}
