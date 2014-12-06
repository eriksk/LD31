package se.powerslidestudios.ld31.particles.backgrounds;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.entity.Entity;
import se.skoggy.utils.Camera2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TiledGround {

	private Texture texture;
	private float x;
	private float y;
	private int tiles;
	
	private Body body;

	public TiledGround(Texture texture, float x, float y, int tiles, World world) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.tiles = tiles;
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		createPhysicsObject(world);
	}
	
	private void createPhysicsObject(World world) {

		BodyDef bodyDefinition = new BodyDef();
		bodyDefinition.type = BodyType.StaticBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ConvertUnits.toSim(texture.getWidth()* tiles), ConvertUnits.toSim(texture.getHeight()), new Vector2(ConvertUnits.toSim(x - (texture.getWidth()) + texture.getWidth() * tiles), ConvertUnits.toSim(y + texture.getHeight()* 2f)), 0f);
		
		Body body = world.createBody(bodyDefinition);
		body.createFixture(shape, 1f);
		
		// just use a generic type
		body.setUserData(new Entity());
		
		shape.dispose();
	
		this.body = body;
	}

	public void update(float dt){
		
	}
	
	public void draw(SpriteBatch spriteBatch, Camera2D cam){
		spriteBatch.setColor(1f, 1f, 1f, 1f);
		spriteBatch.draw(texture, x, y, texture.getWidth() * tiles, texture.getHeight(), 0, 0, texture.getWidth() * tiles * 4, texture.getHeight(), false, true);
	}
}
