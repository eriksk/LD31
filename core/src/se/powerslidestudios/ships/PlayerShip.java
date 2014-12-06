package se.powerslidestudios.ships;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerShip extends Entity{
	
	private TextureAtlas atlas;
	public Body body;

	public PlayerShip(TextureAtlas atlas) {
		super(atlas.getTexture("body"));
		this.atlas = atlas;
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);

		transform.position.x = ConvertUnits.fromSim(body.getPosition().x);
		transform.position.y = ConvertUnits.fromSim(body.getPosition().y);
		transform.rotation = body.getAngle();
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}
}
