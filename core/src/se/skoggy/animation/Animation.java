package se.skoggy.animation;

public class Animation {

	protected int[] frames;
	protected float current, interval;
	protected int currentFrame;
	
	public Animation(int[] frames, float interval) {
		this.frames = frames;
		this.interval = interval;
		reset();
	}
	
	public void reset(){
		current = 0f;
		currentFrame = 0;
	}
	
	public int getFrame(){
		return frames[currentFrame];
	}
	
	public void update(float dt){
		current += dt;
		if(current > interval){
			current = 0f;
			currentFrame++;
			if(currentFrame > frames.length - 1){
				reset();
			}
		}
	}
}