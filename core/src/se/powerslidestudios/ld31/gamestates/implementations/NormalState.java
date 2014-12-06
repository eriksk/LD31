package se.powerslidestudios.ld31.gamestates.implementations;

import se.powerslidestudios.ld31.GameController;
import se.powerslidestudios.ld31.gamestates.GameState;
import se.powerslidestudios.ld31.gamestates.GameStateMachine;

public class NormalState extends GameState{

	public NormalState(GameStateMachine machine) {
		super(machine);
	}

	@Override
	public void enter(GameController gameController) {
		super.enter(gameController);

		gameController.showPickupCargoButton = false;
		gameController.showDropCargoButton = false;
	}
	
	@Override
	public void update(float dt, GameController gameController) {
		super.update(dt, gameController);
	
		if(gameController.shipIsNearCargoPickup()){
			machine.setState(AbleToPickupCargoState.class);
		}
	}
	
	
	
}
