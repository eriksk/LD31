package se.powerslidestudios.ld31.particles.backgrounds;

import java.util.ArrayList;
import java.util.List;

import se.powerslidestudios.physics.ConvertUnits;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.content.ContentManager;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Border {

	TextureAtlas atlas;
	List<PhysicsEntity> entities;
	Rectangle border = new Rectangle(0, 0, 3000, 3000);

	private World world;
	
	public Border(World world) {
		this.world = world;
		entities = new ArrayList<PhysicsEntity>();
	}
	
	public void load(ContentManager content){
		
		atlas = new TextureAtlas(content);
		atlas.register("atlases/buildings");
		

		createBase(border.x, border.y + border.height);
		createBase(border.x + border.height, border.y + border.height);
		
		int poleheight = 732;
		for (int i = 0; i < (border.height / poleheight) - 1; i++) {
			createPole(border.x, border.y + (i * poleheight) + poleheight / 2f);
			createPole(border.x + border.width, border.y + (i * poleheight) + poleheight / 2f);
		}
		
		int roofWidth = 742;
		
		for (int i = 0; i < (border.width / roofWidth) - 1; i++) {
			createRoof(border.x + 16 + (i * roofWidth) + roofWidth / 2f, border.y + (186f / 2f));
		}
		
	}
	public Rectangle getBorder() {
		return border;
	}


	private void createRoof(float x, float y) {
		PhysicsEntity base = new PhysicsEntity(atlas.getTexture("border_roof"));
		Body body = createStaticBody(x, y, base.getSource().width, base.getSource().height);
		base.setBody(body);
		body.setUserData(new Entity());
		entities.add(base);
	}

	
	private void createPole(float x, float y) {
		PhysicsEntity base = new PhysicsEntity(atlas.getTexture("border_pole"));
		Body body = createStaticBody(x, y, base.getSource().width, base.getSource().height);
		base.setBody(body);
		body.setUserData(new Entity());
		entities.add(base);
		
	}
	private void createBase(float x, float y) {
		PhysicsEntity base = new PhysicsEntity(atlas.getTexture("border_base"));
		Body body = createStaticBody(x, y, base.getSource().width, base.getSource().height);
		base.setBody(body);
		body.setUserData(new Entity());
		entities.add(base);
	}

	private Body createStaticBody(float x, float y, float width, float height) {
		BodyDef bodyDefinition = new BodyDef();
		bodyDefinition.type = BodyType.StaticBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ConvertUnits.toSim(width / 2f), ConvertUnits.toSim(height / 2f));
				
		Body body = world.createBody(bodyDefinition);
		body.createFixture(shape, 1f);
		body.setTransform(ConvertUnits.toSim(x), ConvertUnits.toSim(y), 0f);
		
		shape.dispose();
	
		return body;
	}

	public void update(float dt) {
		for (PhysicsEntity physicsEntity : entities) {
			physicsEntity.update(dt);
		}		
	}

	public void draw(SpriteBatch spriteBatch){
		for (PhysicsEntity physicsEntity : entities) {
			physicsEntity.draw(spriteBatch);
		}
	}
}
