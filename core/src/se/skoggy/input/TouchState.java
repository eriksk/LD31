package se.skoggy.input;

public class TouchState {
	public boolean touched;
	public float x, y;

	public void set(TouchState state) {
		touched = state.touched;
		x = state.x;
		y = state.y;
	}
}
