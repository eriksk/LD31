package se.skoggy.tweens.stock;

import se.skoggy.tweens.ITweenable;
import se.skoggy.tweens.Tween;

import com.badlogic.gdx.math.Interpolation;

public class ScaleXTween extends Tween{

	private float start;
	private float end;

	public ScaleXTween(ITweenable subject, Interpolation interpolation, float duration, float start, float end) {
		super(subject, interpolation, duration);
		this.start = start;
		this.end = end;
	}

	@Override
	public void tween(float progress, ITweenable subject, Interpolation interpolation) {
		subject.setScaleX(interpolation.apply(start, end, progress));
	}

}
