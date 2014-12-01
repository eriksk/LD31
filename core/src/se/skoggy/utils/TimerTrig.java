package se.skoggy.utils;

public class TimerTrig {

	float interval;
	float current;

	public TimerTrig(float interval) {
		this.interval = interval;
	}

	public boolean isTrigged(float dt){
		current += dt;
		if(current >= interval){
			current = 0f;
			return true;
		}
		return false;
	}

	public void reset(){
		current = 0f;
	}

	/**
	 * Sets interval and resets the timertrig
	 * @param interval
	 */
	public void setInterval(float interval) {
		this.interval = interval;
		reset();
	}

	/**
	 * Updates without trigging
	 */
	public void update(float dt){
		current += dt;
		if(current > interval){
			current = interval;
		}
	}

	public void setToTrigNextTime(){
		current = interval;
	}

	public float progress(){
		return current > interval ? 1f : current / interval;
	}
}
