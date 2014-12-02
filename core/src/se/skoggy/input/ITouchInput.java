package se.skoggy.input;

import com.badlogic.gdx.math.Vector2;

public interface ITouchInput {
	public boolean tapped();
	public boolean pressed();
	public void update(Vector2 virtualResolution);
	public float x();
	public float y();
}
