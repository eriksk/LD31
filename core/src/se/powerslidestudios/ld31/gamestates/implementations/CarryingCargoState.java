package se.powerslidestudios.ld31.gamestates.implementations;

import se.powerslidestudios.ld31.GameController;
import se.powerslidestudios.ld31.gamestates.GameState;
import se.powerslidestudios.ld31.gamestates.GameStateMachine;

public class CarryingCargoState extends GameState{

	public CarryingCargoState(GameStateMachine machine) {
		super(machine);
	}

	@Override
	public void enter(GameController gameController) {
		gameController.showDropCargoButton = true;
		gameController.showPickupCargoButton = false;
		super.enter(gameController);
	}
	
	@Override
	public void update(float dt, GameController gameController) {
		super.update(dt, gameController);
	
		if(!gameController.isCarryingCargo()){
			machine.setState(NormalState.class);
			return;
		}
	}
}
