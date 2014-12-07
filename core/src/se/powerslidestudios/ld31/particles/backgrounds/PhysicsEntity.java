package se.powerslidestudios.ld31.particles.backgrounds;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.atlases.DynamicTexture;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsEntity extends Entity{

	Body body;
	
	public PhysicsEntity(DynamicTexture texture) {
		super(texture);
	}
	
	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		body.setTransform(ConvertUnits.toSim(x), ConvertUnits.toSim(y),0);
	}

	@Override
	public void update(float dt) {
		super.update(dt);

		transform.position.x = ConvertUnits.fromSim(body.getPosition().x);
		transform.position.y = ConvertUnits.fromSim(body.getPosition().y);
		transform.rotation = body.getAngle();
		
	}
	
}
