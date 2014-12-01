package se.powerslidestudios.ld31.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.skoggy.utils.Camera2D;

public interface IRenderPass {
	public void Render(SpriteBatch spriteBatch, Camera2D cam);
}
