package se.powerslidestudios.buildings;

import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingArea extends Entity{
	
	Entity conveyorBelt;
	
	public LoadingArea(TextureAtlas buildingsAtlas) {
		super(buildingsAtlas.getTexture("loading_area_base"));
		conveyorBelt = new Entity(buildingsAtlas.getTexture("loading_area_conveyor_belt"));
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		conveyorBelt.setPosition(x, y);
	}
	
	
	@Override
	public void update(float dt) {
		super.update(dt);
	
		conveyorBelt.update(dt);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		conveyorBelt.draw(spriteBatch);
		super.draw(spriteBatch);
	}
}
