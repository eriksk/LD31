package se.powerslidestudios.scenes;

import se.powerslidestudios.buildings.LoadingArea;
import se.powerslidestudios.ld31.input.DirectionalInputTouchPanels;
import se.powerslidestudios.ld31.input.TouchArea;
import se.powerslidestudios.ld31.particles.ParticleManager;
import se.powerslidestudios.ld31.particles.backgrounds.TiledGround;
import se.powerslidestudios.ships.CargoVessel;
import se.powerslidestudios.ships.PlayerShip;
import se.skoggy.atlases.TextureAtlas;
import se.skoggy.audio.IAudio;
import se.skoggy.game.IGameContext;
import se.skoggy.scenes.SceneState;
import se.skoggy.utils.ServiceLocator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class GameScene extends GuiScene{

	TiledGround ground;
	
	PlayerShip ship;
	DirectionalInputTouchPanels directionalInput;
	CargoVessel cargoVessel;
	
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

		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0f, 0f), true);
		
		directionalInput = new DirectionalInputTouchPanels(new Vector2(width / 2, height / 2));
		
		TextureAtlas playerShipAtlas = new TextureAtlas(content());
		playerShipAtlas.register("atlases/player_ship");
		
		ship = new PlayerShip(playerShipAtlas, world);

		TextureAtlas buildingAtlas = new TextureAtlas(content());
		buildingAtlas.register("atlases/buildings");
		
		TextureAtlas cargoVesselAtlas = new TextureAtlas(content());
		cargoVesselAtlas.register("atlases/cargo_vessel");
		
		cargoVessel = new CargoVessel(cargoVesselAtlas, world);
		cargoVessel.setPosition(300, -1000);
		
		
		ground = new TiledGround(content().loadTexture("gfx/ground").getTexture(), -1000, 200, 24);
		
		
		loadingArea = new LoadingArea(buildingAtlas);
		loadingArea.setPosition(0, 190);
		
		particleManager = new ParticleManager();
		particleManager.load(content());
		

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

		Vector2 cargoThruster1 = cargoVessel.getThrusterPosition(0);
		Vector2 cargoThruster2 = cargoVessel.getThrusterPosition(1);

		largeThrust(cargoThruster1);
		largeThrust(cargoThruster2);
		
		updatePhysics(dt);
	
		ship.update(dt);
		cargoVessel.update(dt);
		loadingArea.update(dt);
		particleManager.update(dt);
		
	
		cam.setZoom(1.2f);
		
		//cam.move(ship.transform.position.x, ship.transform.position.y);
		cam.move(cargoVessel.transform.position.x, cargoVessel.transform.position.y);
		
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

	private void largeThrust(Vector2 position) {		
		float distance = 96f;
		particleManager.spawnThrusterLarge(position.x + (float)Math.cos(0f) * distance, position.y + (float)Math.sin(0f) * distance, 0f);
		
	}

	private void updatePhysics(float dt) {
		world.step(1/60f, 6, 2);	
	}
	
	@Override
	public void draw() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		ground.draw(spriteBatch, cam);
		loadingArea.draw(spriteBatch);
		ship.draw(spriteBatch);
		cargoVessel.draw(spriteBatch);
		particleManager.draw(spriteBatch);
		spriteBatch.end();
		
		cam.setZoom(cam.zoom * 0.01f);
		cam.update();
		debugRenderer.render(world, cam.getParallax(0.01f));
		cam.setZoom(cam.zoom * 100f);
		
		super.draw();
	}
}
