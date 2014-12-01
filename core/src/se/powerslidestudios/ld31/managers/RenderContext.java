package se.powerslidestudios.ld31.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import se.skoggy.utils.Camera2D;

public class RenderContext {
	
	List<IRenderPass> passes;
	
	public RenderContext() {
		passes = new ArrayList<IRenderPass>();
	}
	
	public void add(IRenderPass pass){
		passes.add(pass);
	}
	
	public void render(SpriteBatch spriteBatch, Camera2D cam){
		for (IRenderPass pass : passes) {
			pass.Render(spriteBatch, cam);
		}
	}
}
