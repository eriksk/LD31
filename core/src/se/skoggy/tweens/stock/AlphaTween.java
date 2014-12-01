package se.skoggy.tweens.stock;

import com.badlogic.gdx.math.Interpolation;

import se.skoggy.tweens.ITweenable;
import se.skoggy.tweens.Tween;

public class AlphaTween extends Tween {

	float start, end;
	
	public AlphaTween(ITweenable subject, Interpolation interpolation, float duration, float start, float end) {
		super(subject, interpolation, duration);
		this.start = start;
		this.end = end;
	}

	@Override
	public void tween(float progress, ITweenable subject, Interpolation interpolation) {
		subject.setAlpha(interpolation.apply(start, end, progress));
	}

}
