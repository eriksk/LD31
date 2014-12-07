package se.powerslidestudios.ships;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerShip extends Entity{
	
	private TextureAtlas atlas;
	public Body body;
	Joint cargoJoint;
	
	boolean alive;
	
	Thruster[] thrusters;

	public PlayerShip(TextureAtlas atlas, World world) {
		super(atlas.getTexture("body"));
		this.atlas = atlas;
		
		alive = true;
		createShipBody(world);
		createThrusters();
	}

	public void setCargoJoint(Joint joint) {
		cargoJoint = joint;
	}
	
	public Joint getCargoJoint() {
		return cargoJoint;
	}
	
	public boolean isConnectedToCargo(){
		return cargoJoint != null;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	private void createShipBody(World world) {
		BodyDef bodyDefinition = new BodyDef();
		bodyDefinition.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ConvertUnits.toSim(getSource().width / 2f), ConvertUnits.toSim(getSource().height / 2f));
				
		Body body = world.createBody(bodyDefinition);
		body.setUserData(this);
		body.createFixture(shape, 1f);
	
		shape.dispose();
		
		body.setFixedRotation(true);
		body.setGravityScale(0f);
	
		this.body = body;
	}
	
	public Body getBody() {
		return body;
	}
	
	private void createThrusters() {
		thrusters = new Thruster[6];

		for (int i = 0; i < thrusters.length; i++)
			thrusters[i] = new Thruster(atlas.getTexture("thruster"));

		float width = getSource().width;
		float height = getSource().height;
		
		// top left
		thrusters[0].setOffset(width * -0.34f, height * -0.5f);
		thrusters[0].setRotation((float)Math.toRadians(-90));

		// top right
		thrusters[1].setOffset(width * 0.24f, height * -0.5f);
		thrusters[1].setRotation((float)Math.toRadians(-90));
		
		// right
		thrusters[2].setOffset(width * 0.52f, 0);
		thrusters[2].setRotation((float)Math.toRadians(0));
		
		// left
		thrusters[3].setOffset(width * -0.54f, 0);
		thrusters[3].setRotation((float)Math.toRadians(180));
		
		// bottom left
		thrusters[4].setOffset(width * -0.34f, height * 0.55f);
		thrusters[4].setRotation((float)Math.toRadians(90));

		// bottom right
		thrusters[5].setOffset(width * 0.24f, height * 0.55f);
		thrusters[5].setRotation((float)Math.toRadians(90));
	}

	public Vector2 getThrusterPosition(int index) {
		return transform.position.cpy().add(thrusters[index].getOffset());
	}
	public float getThrusterRotation(int index) {
		return thrusters[index].transform.rotation;
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
		
		for (Thruster t : thrusters) {
			t.updateFromParent(this);
			t.update(dt);
		}
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		for (Thruster thruster : thrusters) {
			thruster.draw(spriteBatch);
		}		
		super.draw(spriteBatch);
	}

	public Vector2 getJointPositionOffset() {
		return new Vector2(0f, getSource().height * 1.2f);
	}

	public Vector2 getRopeJointPosition() {
		Vector2 offset = getJointPositionOffset();
		offset.x *= 0.5f;
		offset.y *= 0.5f;
		
		offset.rotate((float)Math.toDegrees(transform.rotation));
		
		return new Vector2(transform.position.x + offset.x, transform.position.y + offset.y);
	}
}
