package se.skoggy.input;

import com.badlogic.gdx.utils.viewport.Viewport;

public interface ITouchInput {
	public boolean tapped();
	public boolean pressed();
	public void update(int width, int height);
	public float x();
	public float y();
}
