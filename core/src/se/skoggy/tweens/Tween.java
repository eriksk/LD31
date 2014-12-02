package se.skoggy.tweens;

import com.badlogic.gdx.math.Interpolation;

public abstract class Tween {

	private ITweenable subject;
	private Interpolation interpolation;
	private float duration;
	private float current;
	private float waitBeforeStart;
	
	public Tween(ITweenable subject, Interpolation interpolation, float duration) {
		this.subject = subject;
		this.interpolation = interpolation;
		this.duration = duration;
		waitBeforeStart = 0;
	}
	
	public Tween setWait(float waitBeforeStart){
		this.waitBeforeStart = waitBeforeStart;
		return this;
	}
	
	public abstract void tween(float progress, ITweenable subject, Interpolation interpolation);
	
	public boolean done(){
		return current >= duration;
	}
	
	public float progress(){
		return current / duration;
	}
	
	public void update(float dt){
		if(waitBeforeStart >= 0f)
			waitBeforeStart -= dt;
		
		if(waitBeforeStart < 0f){
			current += dt;
			if(current > duration)
				current = duration;
			
			tween(progress(), subject, interpolation);
		}
	}
}
