package se.powerslidestudios.ld31;

import se.powerslidestudios.scenes.IntroScene;
import se.powerslidestudios.scenes.MenuScene;
import se.powerslidestudios.scenes.SplashScreen;
import se.powerslidestudios.scenes.WaitScene;
import se.skoggy.content.ContentManager;
import se.skoggy.game.IGameContext;
import se.skoggy.input.ITouchInput;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneManager;
import se.skoggy.utils.BaseGame;
import se.skoggy.utils.ServiceLocator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Game extends BaseGame implements IGameContext{

	SceneManager manager;
	private ContentManager content;
	
	private int width, height;
	private Vector2 virtualResolution;
	
	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		virtualResolution = new Vector2(1280, 720);
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void load() {		
		content = new ContentManager("content", true);
		
		new ServiceRegistrator().RegisterServices(this);
		
		
		manager = new SceneManager();
		//manager.pushScene(new GameScene(this));
		//manager.pushScene(new MenuScene(this));
		
		manager.pushScene(
				new WaitScene(this, 1000f, 
						new SplashScreen(this, "skoggy_logo", 
								new SplashScreen(this, "ludum_dare_31", 
										new IntroScene(this, 
												new MenuScene(this, true))))));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		content.dispose();
	}
	
	@Override
	public Vector2 virtualResolution() {
		return virtualResolution;
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
		ServiceLocator.context.locate(ITouchInput.class).update(virtualResolution);
		manager.update(dt);
	}
	
	@Override
	public void draw() {
		super.draw();
		manager.draw();
	}
}
