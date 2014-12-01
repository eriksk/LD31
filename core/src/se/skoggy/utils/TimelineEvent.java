package se.skoggy.utils;

public abstract class TimelineEvent implements ITimelineEvent{

	private boolean isTrigged;
	private float time;
	
	public abstract void onEvent();
	
	@Override
	public void trig(){
		onEvent();
		isTrigged = true;
	}
	
	@Override
	public void reset() {
		isTrigged = false;
	}

	@Override
	public float time(float time) {
		this.time = time;
		return time;
	}

	@Override
	public float time() {
		return time;
	}

	@Override
	public boolean isTrigged() {
		return isTrigged;
	}

}
