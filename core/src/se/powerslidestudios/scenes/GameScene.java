package se.powerslidestudios.scenes;

import se.powerslidestudios.buildings.LoadingArea;
import se.powerslidestudios.ld31.GameController;
import se.powerslidestudios.ld31.cargo.Cargo;
import se.powerslidestudios.ld31.cargo.CargoRope;
import se.powerslidestudios.ld31.gamestates.GameStateMachine;
import se.powerslidestudios.ld31.gamestates.implementations.AbleToPickupCargoState;
import se.powerslidestudios.ld31.gamestates.implementations.CarryingCargoState;
import se.powerslidestudios.ld31.gamestates.implementations.NormalState;
import se.powerslidestudios.ld31.input.DirectionalInputTouchPanels;
import se.powerslidestudios.ld31.input.TouchArea;
import se.powerslidestudios.ld31.particles.ExplosionManager;
import se.powerslidestudios.ld31.particles.ParticleManager;
import se.powerslidestudios.ld31.particles.backgrounds.Border;
import se.powerslidestudios.ld31.particles.backgrounds.Starfield;
import se.powerslidestudios.ld31.particles.backgrounds.TiledGround;
import se.powerslidestudios.ld31.shaders.ColorFilterShader;
import se.powerslidestudios.ld31.ui.CargoButton;
import se.powerslidestudios.physics.ConvertUnits;
import se.powerslidestudios.physics.TypedContactListener;
import se.powerslidestudios.physics.TypedContactListenerCollection;
import se.powerslidestudios.ships.CargoVessel;
import se.powerslidestudios.ships.PlayerShip;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.audio.IAudio;
import se.skoggy.entity.Entity;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.SceneState;
import se.skoggy.ui.Text;
import se.skoggy.ui.TextAlign;
import se.skoggy.ui.TouchButton;
import se.skoggy.ui.TouchButtonEventListener;
import se.skoggy.utils.ServiceLocator;
import se.skoggy.utils.TimerTrig;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

public class GameScene extends GuiScene {

	TiledGround ground;
	Starfield starfield;
	Border border;

	PlayerShip player;
	CargoVessel cargoVessel;
	Cargo cargo;

	CargoButton buttonPickup, buttonDrop;

	DirectionalInputTouchPanels directionalInput;

	ParticleManager particleManager;
	ExplosionManager explosionManager;

	GameStateMachine state;
	GameController gameController;

	CargoRope rope;

	TypedContactListenerCollection contactListeners;

	World world;
	Box2DDebugRenderer debugRenderer;

	LoadingArea loadingArea;

	int score;

	BitmapFont font;
	Text scoreText;

	TimerTrig gameStartTrig = new TimerTrig(2000f);
	
	ColorFilterShader colorFilter;

	public GameScene(IGameContext context) {
		super(context);
	}

	@Override
	public void load() {
		super.load();

		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0f, 9.82f), true);
		contactListeners = new TypedContactListenerCollection();
		world.setContactListener(contactListeners);
		
		border = new Border(world);
		border.load(content());

		scoreText = new Text("Score:", TextAlign.center);
		scoreText.setPosition(width * 0.5f, height * 0.15f);

		font = content().loadFont("universal_fruitcake_64");

		state = createStateMachine();

		directionalInput = new DirectionalInputTouchPanels(new Vector2(
				width / 2, height / 2));

		TextureAtlas playerShipAtlas = new TextureAtlas(content());
		playerShipAtlas.register("atlases/player_ship");

		player = new PlayerShip(playerShipAtlas, world);

		TextureAtlas buildingAtlas = new TextureAtlas(content());
		buildingAtlas.register("atlases/buildings");

		TextureAtlas cargoVesselAtlas = new TextureAtlas(content());
		cargoVesselAtlas.register("atlases/cargo_vessel");

		cargoVessel = new CargoVessel(cargoVesselAtlas, world);

		TextureAtlas cargoAtlas = new TextureAtlas(content());
		cargoAtlas.register("atlases/cargo");

		Rectangle arena = border.getBorder();
		
		ground = new TiledGround(content().loadTexture("gfx/ground").getTexture(), arena.x, arena.y + arena.height, 24, world);

		loadingArea = new LoadingArea(buildingAtlas);

		cargo = new Cargo(cargoAtlas, world);
		cargo.getBody().setActive(false);

		TextureAtlas ropeAtlas = new TextureAtlas(content());
		ropeAtlas.register("atlases/ropes");

		rope = new CargoRope(ropeAtlas);

		particleManager = new ParticleManager();
		particleManager.load(content());
		explosionManager = new ExplosionManager();
		explosionManager.load(content());

		TextureAtlas buttonAtlas = new TextureAtlas(content());
		buttonAtlas.register("atlases/buttons");

		buttonPickup = new CargoButton(buttonAtlas.getTexture("button_pickup"));

		buttonPickup.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				connectCargoToShip(cargo, player);
			}
		});

		buttonDrop = new CargoButton(buttonAtlas.getTexture("button_drop"));

		buttonDrop.addListener(new TouchButtonEventListener() {
			@Override
			public void clicked(TouchButton button) {
				disconnectCargoJoint();
			}
		});

		gameController = new GameController(player, cargo, loadingArea);

		contactListeners.add(new TypedContactListener<PlayerShip, CargoVessel>(PlayerShip.class, CargoVessel.class) {
			@Override
			protected void onCollision(PlayerShip player, CargoVessel vessel) {
				player.setAlive(false);
			}
		});
		contactListeners.add(new TypedContactListener<PlayerShip, Entity>(PlayerShip.class, Entity.class) {
			@Override
			protected void onCollision(PlayerShip player, Entity genericEntity) {
				player.setAlive(false);
			}
		});

		contactListeners.add(new TypedContactListener<Cargo, Entity>(Cargo.class, Entity.class) {
			@Override
			protected void onCollision(Cargo cargo, Entity entity) {
				cargo.setAlive(false);
			}
		});
		
		starfield = new Starfield();
		starfield.load(content());
		
		colorFilter = new ColorFilterShader(width, height, content());

		reset();
	}

	private void reset() {		
		Rectangle arena = this.border.getBorder();
		
		state.setState(NormalState.class);
		player.setPosition(arena.x + arena.width / 2f, arena.y + arena.height - 400);
		player.setAlive(true);
		cargoVessel.setPosition(arena.x + arena.width / 2f, arena.y + arena.height -1500);
		loadingArea.setPosition(arena.x + arena.width / 2f, arena.y + arena.height);
		resetCargo();

		player.getBody().setLinearVelocity(0, 0);
		cargoVessel.getBody().setLinearVelocity(0, 0);
		gameStartTrig.reset();
		
		score = 0;

		IAudio audio = ServiceLocator.context.locate(IAudio.class);
		audio.pauseSong("thrust");
	}

	private void resetCargo() {
		cargo.setPosition(loadingArea.transform.position.x,
				loadingArea.transform.position.y - 100);
		cargo.getBody().setActive(false);
		disconnectCargoJoint();
		cargo.getBody().setLinearVelocity(0, 0);
		cargo.setAlive(true);
	}

	private GameStateMachine createStateMachine() {
		GameStateMachine stateMachine = new GameStateMachine();
		stateMachine.addState(new NormalState(stateMachine), true);
		stateMachine.addState(new AbleToPickupCargoState(stateMachine), false);
		stateMachine.addState(new CarryingCargoState(stateMachine), false);
		return stateMachine;
	}

	private void connectCargoToShip(Cargo cargo, PlayerShip ship) {
		cargo.getBody().setActive(true);

		RopeJointDef rope = new RopeJointDef();
		rope.bodyA = ship.getBody();
		rope.bodyB = cargo.getBody();

		rope.collideConnected = false;

		rope.localAnchorA.x = ConvertUnits.toSim(0);
		rope.localAnchorA.y = ConvertUnits
				.toSim(ship.getJointPositionOffset().y);

		rope.localAnchorB.x = 0f;
		rope.localAnchorB.y = ConvertUnits
				.toSim(cargo.getJointPositionOffset().y);

		rope.maxLength = ConvertUnits.toSim(128f);

		Joint joint = world.createJoint(rope);
		ship.setCargoJoint(joint);
	}

	private void disconnectCargoJoint() {
		if (player.getCargoJoint() == null)
			return;

		world.destroyJoint(player.getCargoJoint());
		player.setCargoJoint(null);
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

		if (state == SceneState.Done)
			manager.pushScene(new MenuScene(context));
	}

	@Override
	protected void createUi() {
		/*
		 * TouchButton btnSettings = uiFactory.createRoundIconButton("cross",
		 * "yellow", transitionInDuration());
		 * 
		 * btnSettings.setPosition(width * 0.9f, height * 0.2f);
		 * 
		 * btnSettings.addListener(new TouchButtonEventListener() {
		 * 
		 * @Override public void clicked(TouchButton button) {
		 * manager.pushPopup(new AreYouSureDialogScene(context, new
		 * DialogResultListener() {
		 * 
		 * @Override public void onClose(DialogResult result) { if(result ==
		 * DialogResult.Yes){ close(); } } })); } });
		 * 
		 * elements.add(btnSettings);
		 */
	}

	@Override
	public void update(float dt) {

		gameStartTrig.update(dt);

		if (gameStartTrig.progress() >= 1f) {
			updatePlayerInput(dt);
			updateCargoThrusters();
			updatePhysics(dt);
			checkIfCargoSuccesfullyDelivered();
			state.update(dt, gameController);

			if (!player.getAlive()) {
				killPlayer();
			}
			if(!cargo.getAlive()){
				killCargo();
			}
		}

		player.update(dt);
		cargoVessel.update(dt);
		cargo.update(dt);
		loadingArea.update(dt);

		particleManager.update(dt);
		explosionManager.update(dt);
		updateControllerSettings(dt);
		starfield.update(dt);
		border.update(dt);

		colorFilter.getParameters().saturation = 0.6f;
		colorFilter.getParameters().r = 0.75f;
		colorFilter.getParameters().g = 0.8f;
		colorFilter.getParameters().b= 1f;
		colorFilter.update(dt);

		cam.setZoom(4f);
		if(gameStartTrig.progress() >= 1f)
			cam.move(player.transform.position.x, player.transform.position.y);
		ground.update(dt);
		super.update(dt);
	}

	private void killCargo() {
		explosionManager.explode(cargo.transform.position.x, cargo.transform.position.y);
		resetCargo();
	}

	private void updateControllerSettings(float dt) {
		if (gameController.showPickupCargoButton) {
			buttonPickup.setPosition(width * 0.1f, height * 0.15f);
			buttonPickup.update(dt);
		}
		if (gameController.showDropCargoButton) {
			buttonDrop.setPosition(width * 0.1f, height * 0.15f);
			buttonDrop.update(dt);
		}
	}

	private void updateCargoThrusters() {
		Vector2 cargoThruster1 = cargoVessel.getThrusterPosition(0);
		Vector2 cargoThruster2 = cargoVessel.getThrusterPosition(1);

		largeThrust(cargoThruster1);
		largeThrust(cargoThruster2);
	}

	private void updatePlayerInput(float dt) {
		IAudio audio = ServiceLocator.context.locate(IAudio.class);
		if (player.getAlive()) {
			directionalInput.update();

			TouchArea area = directionalInput.getArea();

			boolean usingThruster = true;
			float force = 1f;
			// TODO: move to shipcontroller
			switch (area) {
			case left:
				thrust(3);
				player.body.applyForceToCenter(new Vector2(force, 0f), true);
				break;
			case right:
				thrust(2);
				player.body.applyForceToCenter(new Vector2(-force, 0f), true);
				break;
			case top:
				thrust(0);
				thrust(1);
				player.body.applyForceToCenter(new Vector2(0f, force), true);
				break;
			case bottom:
				thrust(4);
				thrust(5);
				player.body.applyForceToCenter(new Vector2(0f, -force), true);
				break;
			default:
				usingThruster = false;
				break;
			}

			if (usingThruster) {
				audio.playSong("thrust", true);
			} else {
				audio.pauseSong("thrust");
			}
		} else {
			audio.pauseSong("thrust");
		}

	}

	private void killPlayer() {
		explosionManager.explode(player.transform.position.x, player.transform.position.y);
		ServiceLocator.context.locate(IAudio.class).play("explosion");
		reset();
	}

	private void checkIfCargoSuccesfullyDelivered() {
		if (!gameController.isCarryingCargo()) {
			if (cargoVessel.getDropZone().contains(cargo.getPosition().x,
					cargo.getPosition().y)) {
				float speed = cargo.getBody().getLinearVelocity().len2();
				if (speed < 0.001f) {
					// success
					score++;
					resetCargo();
					// TODO: reset the cargo and throw sparkles and add score
				}
			}
		}
	}

	private void thrust(int index) {
		Vector2 thrusterPosition = player.getThrusterPosition(index);
		float thrusterRotation = player.getThrusterRotation(index);

		float distance = 32f;
		particleManager.spawnThruster(
				thrusterPosition.x + (float) Math.cos(thrusterRotation)
						* distance,
				thrusterPosition.y + (float) Math.sin(thrusterRotation)
						* distance, thrusterRotation);

	}

	private void largeThrust(Vector2 position) {
		float distance = 96f;
		particleManager.spawnThrusterLarge(position.x + (float) Math.cos(0f)
				* distance, position.y + (float) Math.sin(0f) * distance, 0f);

	}

	private void updatePhysics(float dt) {
		world.step(1 / 60f, 6, 2);
	}

	@Override
	public void draw() {
		colorFilter.begin();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		starfield.draw(spriteBatch, cam);

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		ground.draw(spriteBatch, cam);
		border.draw(spriteBatch);
		loadingArea.draw(spriteBatch);
		if (player.getAlive() && gameStartTrig.progress() >= 1f)
			player.draw(spriteBatch);
		cargo.draw(spriteBatch);
		if (gameController.isCarryingCargo()) {
			rope.draw(spriteBatch, player.getRopeJointPosition(),
					cargo.getRopeJointPosition());
		}
		cargoVessel.draw(spriteBatch);
		particleManager.draw(spriteBatch);
		explosionManager.draw(spriteBatch);

		/**
		 * Rectangle area = cargoVessel.getDropZone();
		 * 
		 * rope.draw(spriteBatch, new Vector2(area.x, area.y), new
		 * Vector2(area.x + area.width, area.y)); rope.draw(spriteBatch, new
		 * Vector2(area.x + area.width, area.y), new Vector2(area.x +
		 * area.width, area.y + area.height)); rope.draw(spriteBatch, new
		 * Vector2(area.x, area.y + area.height), new Vector2(area.x +
		 * area.width, area.y + area.height)); rope.draw(spriteBatch, new
		 * Vector2(area.x, area.y), new Vector2(area.x, area.y + area.height));
		 */

		 Rectangle area = border.getBorder();
		  
		 rope.draw(spriteBatch, new Vector2(area.x, area.y), new
		 Vector2(area.x + area.width, area.y)); rope.draw(spriteBatch, new
		 Vector2(area.x + area.width, area.y), new Vector2(area.x +
		 area.width, area.y + area.height)); rope.draw(spriteBatch, new
		 Vector2(area.x, area.y + area.height), new Vector2(area.x +
		 area.width, area.y + area.height)); rope.draw(spriteBatch, new
		 Vector2(area.x, area.y), new Vector2(area.x, area.y + area.height));
		 

		spriteBatch.end();
		
		colorFilter.end();
		
		colorFilter.render();

		spriteBatch.setProjectionMatrix(uiCam.combined);
		spriteBatch.begin();
		if (gameController.showPickupCargoButton)
			buttonPickup.draw(spriteBatch);
		if (gameController.showDropCargoButton)
			buttonDrop.draw(spriteBatch);

		if(gameStartTrig.progress() < 1f){
			scoreText.setText("Get ready: " + gameStartTrig.getTimeLeftInSeconds());
		}else{		
			scoreText.setText("SCORE: " + score);
		}
		scoreText.draw(font, spriteBatch);
		spriteBatch.end();

		  cam.setZoom(cam.zoom * 0.01f); cam.update();
		  debugRenderer.render(world, cam.getParallax(0.01f));
		 cam.setZoom(cam.zoom * 100f);
		

		super.draw();
	}
}
