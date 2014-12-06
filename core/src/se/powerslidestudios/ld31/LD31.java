package se.powerslidestudios.ld31;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class LD31 extends ApplicationAdapter {
	
	Game game;
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	
		game.resize(width, height);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		game.dispose();
	}
	
	@Override
	public void create () {		
		game = new Game(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.load();
	}

	@Override
	public void render () {
		game.update(Gdx.graphics.getDeltaTime() * 1000f);
		
		Gdx.gl.glClearColor(144f / 255f, 204f / 255f, 238f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.draw();
	}
}
