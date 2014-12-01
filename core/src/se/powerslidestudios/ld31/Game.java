package se.powerslidestudios.ld31;

import se.powerslidestudios.scenes.GameScene;
import se.skoggy.content.ContentManager;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneManager;
import se.skoggy.utils.BaseGame;

public class Game extends BaseGame implements IGameContext{

	SceneManager manager;
	private ContentManager content;
	
	private int width, height;
	
	public Game(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void load() {		
		new ServiceRegistrator().RegisterServices(this);
		
		content = new ContentManager("content", true);
		
		manager = new SceneManager();
		manager.pushScene(new GameScene(this));
	}
	
	@Override
	public void changeScene(Scene scene) {
		manager.pushScene(scene);
	}
	
	@Override
	public void changeScene(Scene scene, boolean isPreloaded) {
		manager.pushScene(scene, isPreloaded);
	}
	
	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}
	
	@Override
	public ContentManager getContent() {
		return content;
	}
	
	@Override
	public void update(float dt) {
		manager.update(dt);
	}
	
	@Override
	public void draw() {
		super.draw();
		manager.draw();
	}
}
