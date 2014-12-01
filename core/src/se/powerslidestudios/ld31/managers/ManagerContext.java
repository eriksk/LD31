package se.powerslidestudios.ld31.managers;

import java.util.ArrayList;
import java.util.List;

import se.skoggy.content.ContentManager;
import se.skoggy.utils.Camera2D;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ManagerContext {

	private RenderContext renderContext;
	private List<Manager> managers;
	
	public ManagerContext(RenderContext renderContext) {
		this.renderContext = renderContext;
		managers = new ArrayList<Manager>();
	}
	
	public void load(ContentManager content){
		for (Manager manager : managers) {
			manager.load(content);
		}
	}
	
	public void update(float dt){
		for (Manager manager : managers) {
			manager.update(dt);	
		}	
	}
	
	public void draw(SpriteBatch spriteBatch, Camera2D cam){
		renderContext.render(spriteBatch, cam);
	}
}
