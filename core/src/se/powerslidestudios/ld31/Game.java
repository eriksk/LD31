package se.powerslidestudios.ld31;

import com.badlogic.gdx.Gdx;

import se.powerslidestudios.scenes.GameScene;
import se.powerslidestudios.scenes.MenuScene;
import se.skoggy.content.ContentManager;
import se.skoggy.game.IGameContext;
import se.skoggy.input.ITouchInput;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneManager;
import se.skoggy.utils.BaseGame;
import se.skoggy.utils.ServiceLocator;

public class Game extends BaseGame implements IGameContext{

	SceneManager manager;
	private ContentManager content;
	
	private int width, height;
	
	public Game(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void load() {		
		new ServiceRegistrator().RegisterServices(this);
		
		content = new ContentManager("content", true);
		
		manager = new SceneManager();
		//manager.pushScene(new GameScene(this));
		manager.pushScene(new MenuScene(this));
	}
	
	@Override
	public void exit() {
		Gdx.app.exit();
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
		ServiceLocator.context.locate(ITouchInput.class).update(width, height);
		manager.update(dt);
	}
	
	@Override
	public void draw() {
		super.draw();
		manager.draw();
	}
}
