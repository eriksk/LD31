package se.powerslidestudios.ld31.gamestates;

import se.powerslidestudios.ld31.GameController;

public class GameState {
	
	protected GameStateMachine machine;

	public GameState(GameStateMachine machine) {
		this.machine = machine;
	}
	
	public void enter(GameController gameController){
	}
	
	public void leave(GameController gameController){
	}
	
	public void update(float dt, GameController gameController){
	}
}
