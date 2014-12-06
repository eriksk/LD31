package se.powerslidestudios.ld31.particles.backgrounds;

import se.skoggy.utils.Camera2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TiledGround {

	private Texture texture;
	private float x;
	private float y;
	private int tiles;

	public TiledGround(Texture texture, float x, float y, int tiles) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.tiles = tiles;
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	
	public void update(float dt){
		
	}
	
	public void draw(SpriteBatch spriteBatch, Camera2D cam){
		spriteBatch.draw(texture, x, y, texture.getWidth() * tiles, texture.getHeight(), 0, 0, texture.getWidth() * tiles * 4, texture.getHeight(), false, true);
	}
}
