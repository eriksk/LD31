package se.powerslidestudios.ld31.gamestates.implementations;

import se.powerslidestudios.ld31.GameController;
import se.powerslidestudios.ld31.gamestates.GameState;
import se.powerslidestudios.ld31.gamestates.GameStateMachine;

public class AbleToPickupCargoState extends GameState{

	public AbleToPickupCargoState(GameStateMachine machine) {
		super(machine);
	}

	@Override
	public void enter(GameController gameController) {
		super.enter(gameController);

		gameController.showDropCargoButton = false;
		gameController.showPickupCargoButton = true;
	}
	
	@Override
	public void update(float dt, GameController gameController) {
	
		if(!gameController.shipIsNearCargoPickup()){
			machine.setState(NormalState.class);
			return;
		}
		
		if(gameController.isCarryingCargo()){
			machine.setState(CarryingCargoState.class);
			return;
		}
		
		super.update(dt, gameController);
	}
	
}
