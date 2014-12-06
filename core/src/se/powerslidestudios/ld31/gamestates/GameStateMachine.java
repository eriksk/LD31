package se.powerslidestudios.ld31.gamestates;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import se.powerslidestudios.ld31.GameController;

public class GameStateMachine {

	private final List<GameState> states;
	private GameState currentState;	
	private GameState targetState;
	
	public GameStateMachine() {
		states = new ArrayList<GameState>();
		currentState = null;
	}
	
	public void addState(GameState state, boolean isDefault){
		states.add(state);
		if(isDefault)
			targetState = state;
	}
	
	public void setState(Class<?> state){
		targetState = getStateByClass(state);
	}
	
	private GameState getStateByClass(Class<?> type) {
		for (GameState gameState : states) {
			if(gameState.getClass() == type)
				return gameState;
		}
		throw new RuntimeException(MessageFormat.format("State {0} does not exist.", type.getName()));
	}

	public void update(float dt, GameController gameController){
		if(targetState != null){
			if(currentState != null)
				currentState.leave(gameController);
			currentState = targetState;
			currentState.enter(gameController);
			System.out.println("State: " + currentState.getClass().getSimpleName());
			targetState = null;
		}
		
		currentState.update(dt, gameController);
	}
}
