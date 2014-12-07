package se.powerslidestudios.scenes;

import se.powerslidestudios.ld31.text.TextSequence;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.Scene;
import se.skoggy.scenes.SceneState;
import se.skoggy.ui.Text;
import se.skoggy.ui.TextAlign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class IntroScene extends GuiScene{

	Text text;
	TextSequence sequence;
	BitmapFont font;
	private Scene gotoScene;
	
	public IntroScene(IGameContext context, Scene gotoScene) {
		super(context);
		this.gotoScene = gotoScene;
	}

	@Override
	public void load() {
		super.load();
	
		font = content().loadFont("xirod_64");
		
		text = new Text("", TextAlign.center);
		text.setScale(0.5f);
		sequence = new TextSequence(new String[]{
				"In the year 2045",
				"Earth was attacked",
				"They came from nowhere",
				"But we fought back",
				"All seemed lost",
				"Only one person could save us",
				"That person",
				"Was not you",
				"You are a worker at a cargo hold",
				"Your job is to deliver supplies",
				"To the people that are actually important",
				"You are...",
				"The cargo person!"
		}, 2000f);
	}
	
	@Override
	public float transitionInDuration() {
		return 1000f;
	}
	
	@Override
	public float transitionOutDuration() {
		return 1000f;
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
	
		if(state == SceneState.Done){
			manager.pushScene(gotoScene);
		}
	}
	
	@Override
	protected void createUi() {
		
	}

	@Override
	public void update(float dt) {
		super.update(dt);

		if(isActive()){		
			sequence.update(dt);
			
			if(sequence.isDone()){
				setState(SceneState.TransitionOut);
			}
		}
	}
	
	@Override
	public void draw() {

		spriteBatch.setProjectionMatrix(uiCam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		
		text.setPosition(width * 0.5f, height * 0.5f);
		text.setText(sequence.getCurrentText());
		text.draw(font, spriteBatch);
		
		
		spriteBatch.end();
		
		super.draw();
	}
}
