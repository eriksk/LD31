package se.skoggy.game;

import com.badlogic.gdx.math.Vector2;

import se.skoggy.content.ContentManager;
import se.skoggy.scenes.Scene;

public interface IGameContext {
	public int width();
	public int height();
	public ContentManager getContent();
	public void changeScene(Scene scene);
	public void changeScene(Scene scene, boolean isPreloaded);
	public Vector2 virtualResolution();
	public void exit();
}
