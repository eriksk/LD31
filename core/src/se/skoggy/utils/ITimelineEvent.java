package se.skoggy.utils;

public interface ITimelineEvent {
	public void trig();
	public float time(float time);
	public float time();
	public boolean isTrigged();
	public void reset();
}
