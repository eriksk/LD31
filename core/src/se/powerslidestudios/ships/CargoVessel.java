package se.powerslidestudios.ships;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CargoVessel extends Entity{

	Vector2 loadingAreaoffset;
	private Entity loadingArea;
	private TextureAtlas atlas;
	private Body body;

	public CargoVessel(TextureAtlas atlas, World world) {
		super(atlas.getTexture("cargo_vessel_body"));
		this.atlas = atlas;
		
		loadingArea = new Entity(atlas.getTexture("loading_area"));
		loadingAreaoffset = new Vector2(-100, -200);
		
		createPhysicsBody(world);
	}
	
	private void createPhysicsBody(World world) {
		BodyDef bodyDefinition = new BodyDef();
		bodyDefinition.type = BodyType.StaticBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ConvertUnits.toSim(getSource().width * 0.8f)/ 2f, ConvertUnits.toSim(getSource().height * 0.6f)/ 2f, new Vector2(ConvertUnits.toSim(140), ConvertUnits.toSim(0f)), 0f);
		
		PolygonShape loadingAreaBorderLeft = new PolygonShape();
		loadingAreaBorderLeft.setAsBox(ConvertUnits.toSim(16/2f), ConvertUnits.toSim(128/2f), new Vector2(ConvertUnits.toSim(-800)/2f, ConvertUnits.toSim(-400)/2f), 0f);

		PolygonShape loadingAreaBorderRight = new PolygonShape();
		loadingAreaBorderRight.setAsBox(ConvertUnits.toSim(16/2f), ConvertUnits.toSim(128/2f), new Vector2(ConvertUnits.toSim(420)/2f, ConvertUnits.toSim(-400)/2f), 0f);
		
		Body body = world.createBody(bodyDefinition);
		body.createFixture(shape, 1f);
		body.createFixture(loadingAreaBorderLeft, 1f);
		body.createFixture(loadingAreaBorderRight, 1f);
		
		body.setGravityScale(0f);
		body.setUserData(this);
		
		body.setFixedRotation(true);
		
		shape.dispose();
	
		this.body = body;
	}

	public Body getBody() {
		return body;
	}

	public Rectangle getDropZone() {
		float width = (transform.position.x + 220) - (transform.position.x - 410);
		float height = 120;
		return new Rectangle(transform.position.x - 410, transform.position.y - 260, width, height);
	}

	public Vector2 getThrusterPosition(int index) {
		if(index == 0)
			return new Vector2(transform.position.x + 820, transform.position.y + -84);
		if(index == 1)
			return new Vector2(transform.position.x + 820, transform.position.y + 43);
		throw new RuntimeException("Index does not exist");
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
		
		loadingArea.setPosition(transform.position.x + loadingAreaoffset.x, transform.position.y + loadingAreaoffset.y);
	}
	
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
		loadingArea.draw(spriteBatch);
	}
}
