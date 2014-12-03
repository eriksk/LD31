package se.skoggy.scenes;

import se.skoggy.content.ContentManager;
import se.skoggy.game.IGameContext;
import se.skoggy.tweens.Tween;
import se.skoggy.tweens.TweenManager;
import se.skoggy.utils.Camera2D;
import se.skoggy.utils.GLHelpers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class Scene extends Stage{

	protected SceneManager manager;
	protected Camera2D cam;
	protected SpriteBatch spriteBatch;
	protected IGameContext context;
	protected TweenManager tweens;
	protected int width;
	protected int height;
	protected SceneState state;
	public float current = 0f;

	public Scene(IGameContext context) {
		super(new StretchViewport(context.virtualResolution().x, context.virtualResolution().y));
		this.context = context;
		this.width = (int)context.virtualResolution().x;
		this.height = (int)context.virtualResolution().y;
		tweens = new TweenManager();
	}

	protected void createCam(Rectangle area){
		cam = new Camera2D(width, height, area);
	}

	void setSceneManager(SceneManager manager){
		this.manager = manager;
	}

	public Rectangle getBoundaryArea(){
		return null;
	}
	
	public void setState(SceneState state) {
		this.state = state;
		current = 0f;
		stateChanged(state);
	}
	
	public SceneState getState() {
		return state;
	}
	
	public boolean isActive(){
		return state == SceneState.Active;
	}
	
	public void stateChanged(SceneState state){}
	
	public float transitionInDuration(){ return 0; };
	public float transitionOutDuration(){ return 0; };
	public boolean isPopup(){ return false; };
	
	protected ContentManager content(){
		return context.getContent();
	}
	
	public void tween(Tween tween){
		tweens.add(tween);
	}
	
	public void load(){
		spriteBatch = new SpriteBatch();
		spriteBatch.enableBlending();
		GLHelpers.enableAlphaBlending(spriteBatch);
		createCam(getBoundaryArea());
		cam.setPosition(0, 0);
	}
	public void updatePassive(float dt){
		tweens.update(dt);
		cam.update();
	}
	public void update(float dt){
		tweens.update(dt);
		cam.update();
	}
	public abstract void draw();
	public void beforeRemoved() {	
	}
}
