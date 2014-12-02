package se.skoggy.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface UIElement {
	public void update(float dt);
	public void draw(BitmapFont font, SpriteBatch spriteBatch);
}
