package se.skoggy.utils;

import java.util.ArrayList;
import java.util.List;

public class Timeline {

	List<ITimelineEvent> events;
	boolean running;
	float current = 0f;
	float currentTimeBuilding;
	
	public Timeline() {
		events = new ArrayList<ITimelineEvent>();
		running = false;
	}
	
	public Timeline add(float time, float duration, ITimelineEvent event) {
		events.add(event);
		event.time(currentTimeBuilding + time);
		this.currentTimeBuilding += time + duration;
		return this;
	}
	
	public void start(){
		clear();
		running = true;	
		current = 0f;
	}
	
	public void clear(){
		for (ITimelineEvent event : events) {
			event.reset();
		}
	}
	
	public void update(float dt) {
		if(running){
			float old = current;
			current += dt;
			
			for (ITimelineEvent event : events) {
				if(shouldTrigEvent(old, current, event)){
					event.trig();
				}
			}
		}
	}

	private boolean shouldTrigEvent(float old, float current, ITimelineEvent event) {
		return event.time() <= current && !event.isTrigged();
	}
}
