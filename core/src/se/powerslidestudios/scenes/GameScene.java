package se.powerslidestudios.scenes;

import se.powerslidestudios.ld31.input.DirectionalInputTouchPanels;
import se.powerslidestudios.ld31.input.TouchArea;
import se.powerslidestudios.physics.ConvertUnits;
import se.powerslidestudios.ships.PlayerShip;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.SceneState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameScene extends GuiScene{

	Entity staticBackground;
	
	PlayerShip ship;
	DirectionalInputTouchPanels directionalInput;
	
	World world;
	Box2DDebugRenderer debugRenderer;
	
	public GameScene(IGameContext context) {
		super(context);
	}
	
	@Override
	public void load() {
		super.load();
	
		directionalInput = new DirectionalInputTouchPanels(new Vector2(width / 2, height / 2));
		staticBackground = new Entity(content().loadTexture("gfx/background"));
		
		TextureAtlas playerShipAtlas = new TextureAtlas(content());
		playerShipAtlas.register("atlases/player_ship");
		
		ship = new PlayerShip(playerShipAtlas);
		
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0f, 0f), true);

		
		createShipBody();
	}
	
	private void createShipBody() {
		BodyDef bodyDefinition = new BodyDef();
		bodyDefinition.type = BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(ConvertUnits.toSim(ship.getSource().width), ConvertUnits.toSim(ship.getSource().height));
				
		Body body = world.createBody(bodyDefinition);
		body.createFixture(shape, 1f);
	
		shape.dispose();
	
		ship.body = body;
	}

	@Override
	public float transitionInDuration() {
		return 500f;
	}
	
	@Override
	public float transitionOutDuration() {
		return 1000f;
	}
	
	public void close() {
		setState(SceneState.TransitionOut);
	}
	
	@Override
	public void stateChanged(SceneState state) {
		super.stateChanged(state);
		
		
		if(state == SceneState.Done)
			manager.pushScene(new MenuScene(context));
	}
		
	@Override
	protected void createUi() {
		/*
		TouchButton btnSettings = uiFactory.createRoundIconButton("cross", "yellow", transitionInDuration());
	
		btnSettings.setPosition(width * 0.9f, height * 0.2f);
		
		btnSettings.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				manager.pushPopup(new AreYouSureDialogScene(context, new DialogResultListener() {
					@Override
					public void onClose(DialogResult result) {
						if(result == DialogResult.Yes){
							close();
						}
					}
				}));
			}
		});
		
		elements.add(btnSettings);
		*/
	}
	
	@Override
	public void update(float dt) {
	
		directionalInput.update();
		
		TouchArea area = directionalInput.getArea();
		
		if(area != TouchArea.none)
			System.out.println(area.toString());
		
		float force = 1f;
		
		switch (area) {
			case left:
				ship.body.applyForceToCenter(new Vector2(force, 0f), true);
				break;
			case right:
				ship.body.applyForceToCenter(new Vector2(-force, 0f), true);
				break;
			case top:
				ship.body.applyForceToCenter(new Vector2(0f, force), true);
				break;
			case bottom:
				ship.body.applyForceToCenter(new Vector2(0f, -force), true);
				break;
		}
	
		ship.update(dt);
		
	
	
		updatePhysics(dt);
	
		cam.move(ship.transform.position.x, ship.transform.position.y);
		
		staticBackground.update(dt);
		super.update(dt);
	}

	private void updatePhysics(float dt) {
		world.step(1/60f, 6, 2);	
	}
	
	@Override
	public void draw() {
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		staticBackground.draw(spriteBatch);
		ship.draw(spriteBatch);
		spriteBatch.end();
		
		cam.setZoom(cam.zoom * 0.01f);
		cam.update();
		debugRenderer.render(world, cam.getParallax(0.01f));
		cam.setZoom(cam.zoom * 100f);
		
		super.draw();
	}
}
