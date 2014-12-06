package se.powerslidestudios.ld31;

import se.powerslidestudios.buildings.LoadingArea;
import se.powerslidestudios.ld31.cargo.Cargo;
import se.powerslidestudios.ships.PlayerShip;

public class GameController {
	
	public boolean showPickupCargoButton;
	public boolean showDropCargoButton;
	private PlayerShip player;
	private Cargo cargo;
	private LoadingArea loadingArea;
	
	public GameController(PlayerShip player, Cargo cargo, LoadingArea loadingArea) {
		this.player = player;
		this.cargo = cargo;
		this.loadingArea = loadingArea;
	}
	
	public boolean shipIsNearCargoPickup() {
		return loadingArea.transform.position.dst(player.transform.position) < 256f;
	}
	
	public boolean isCarryingCargo(){
		return player.isConnectedToCargo();
	}

}
