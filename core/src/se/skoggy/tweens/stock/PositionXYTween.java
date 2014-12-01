package se.skoggy.tweens.stock;

import com.badlogic.gdx.math.Interpolation;

import se.skoggy.tweens.ITweenable;
import se.skoggy.tweens.Tween;

public class PositionXYTween extends Tween{

	private float startX;
	private float startY;
	private float endX;
	private float endY;

	public PositionXYTween(ITweenable subject, Interpolation interpolation, float duration, float startX, float startY, float endX, float endY) {
		super(subject, interpolation, duration);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	@Override
	public void tween(float progress, ITweenable subject, Interpolation interpolation) {
		subject.setPosition(interpolation.apply(startX, endX, progress), interpolation.apply(startY, endY, progress));
	}

}
