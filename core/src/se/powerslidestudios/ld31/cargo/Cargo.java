package se.powerslidestudios.ld31.cargo;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Cargo extends Entity{

	Body body;
	
	public Cargo(TextureAtlas atlas, World world) {
		super(atlas.getTexture("cargo_box"));
	
		createPhysicsBody(world);
	}

	private void createPhysicsBody(World world) {
		BodyDef bodyDefinition = new BodyDef();
		bodyDefinition.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ConvertUnits.toSim(getSource().width), ConvertUnits.toSim(getSource().height));
				
		Body body = world.createBody(bodyDefinition);
		body.createFixture(shape, 0.05f);
	
		shape.dispose();
	
		this.body = body;
	}
	
	public Body getBody() {
		return body;
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		body.setTransform(ConvertUnits.toSim(x), ConvertUnits.toSim(y),0);
	}
	
	@Override
	public void update(float dt) {

		transform.position.x = ConvertUnits.fromSim(body.getPosition().x);
		transform.position.y = ConvertUnits.fromSim(body.getPosition().y);
		transform.rotation = body.getAngle();
		
		super.update(dt);
	}
}
