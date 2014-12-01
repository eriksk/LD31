package se.powerslidestudios.ld31;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class LD31 extends ApplicationAdapter {
	
	Game game;
	
	@Override
	public void create () {		
		game = new Game(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.load();
	}

	@Override
	public void render () {
		game.update(Gdx.graphics.getDeltaTime() * 1000f);
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.draw();
	}
}
