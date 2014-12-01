package se.skoggy.tweens;

public interface ITweenable {
	public void setPosition(float x, float y);
	public void setPositionX(float x);
	public void setPositionY(float y);
	public void setScale(float x, float y);
	public void setScale(float scalar);
	public void setScaleX(float x);
	public void setScaleY(float y);
	public void setColor(float r, float g, float b, float a);
	public void setAlpha(float a);
	public void setRotation(float rotation);
}
