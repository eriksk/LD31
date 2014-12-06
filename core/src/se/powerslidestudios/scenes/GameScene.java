package se.powerslidestudios.scenes;

import se.powerslidestudios.buildings.LoadingArea;
import se.powerslidestudios.ld31.input.DirectionalInputTouchPanels;
import se.powerslidestudios.ld31.input.TouchArea;
import se.powerslidestudios.ld31.particles.ParticleManager;
import se.powerslidestudios.ld31.particles.backgrounds.TiledGround;
import se.powerslidestudios.physics.ConvertUnits;
import se.powerslidestudios.ships.PlayerShip;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.audio.IAudio;
import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.SceneState;
import se.skoggy.utils.ServiceLocator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameScene extends GuiScene{

	Entity staticBackground;
	TiledGround ground;
	
	PlayerShip ship;
	DirectionalInputTouchPanels directionalInput;
	
	ParticleManager particleManager;
	
	World world;
	Box2DDebugRenderer debugRenderer;
	
	LoadingArea loadingArea;
	
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

		TextureAtlas buildingAtlas = new TextureAtlas(content());
		buildingAtlas.register("atlases/buildings");
		
		
		ground = new TiledGround(content().loadTexture("gfx/ground").getTexture(), -1000, 200, 24);
		
		
		loadingArea = new LoadingArea(buildingAtlas);
		loadingArea.setPosition(0, 190);
		
		particleManager = new ParticleManager();
		particleManager.load(content());
		
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
				
		boolean usingThruster = true;
		float force = 1f;
		// TODO: move to shipcontroller
		switch (area) {
			case left:
				thrust(3);
				ship.body.applyForceToCenter(new Vector2(force, 0f), true);
				break;
			case right:
				thrust(2);
				ship.body.applyForceToCenter(new Vector2(-force, 0f), true);
				break;
			case top:
				thrust(0);
				thrust(1);
				ship.body.applyForceToCenter(new Vector2(0f, force), true);
				break;
			case bottom:
				thrust(4);
				thrust(5);
				ship.body.applyForceToCenter(new Vector2(0f, -force), true);
				break;
			default:
				usingThruster = false;
				break;
		}
		
		IAudio audio = ServiceLocator.context.locate(IAudio.class);
		
		if(usingThruster){
			audio.playSong("thrust", true);
		}else{
			audio.pauseSong("thrust");
		}
	
		updatePhysics(dt);
	
		ship.update(dt);
		loadingArea.update(dt);
		particleManager.update(dt);
		
	
		cam.move(ship.transform.position.x, ship.transform.position.y);
		
		staticBackground.update(dt);
		ground.update(dt);
		super.update(dt);
	}

	private void thrust(int index) {
		Vector2 thrusterPosition = ship.getThrusterPosition(index);
		float thrusterRotation = ship.getThrusterRotation(index);
		
		float distance = 32f;
		particleManager.spawnThruster(
				thrusterPosition.x + (float)Math.cos(thrusterRotation) * distance,
				thrusterPosition.y + (float)Math.sin(thrusterRotation) * distance,
				thrusterRotation);
		
	}

	private void updatePhysics(float dt) {
		world.step(1/60f, 6, 2);	
	}
	
	@Override
	public void draw() {
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		staticBackground.draw(spriteBatch);
		ground.draw(spriteBatch, cam);
		loadingArea.draw(spriteBatch);
		particleManager.draw(spriteBatch);
		ship.draw(spriteBatch);
		spriteBatch.end();
		
		cam.setZoom(cam.zoom * 0.01f);
		cam.update();
		//debugRenderer.render(world, cam.getParallax(0.01f));
		cam.setZoom(cam.zoom * 100f);
		
		super.draw();
	}
}
