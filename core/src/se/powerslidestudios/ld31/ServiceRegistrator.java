package se.powerslidestudios.ld31;

import se.skoggy.audio.AudioManager;
import se.skoggy.audio.IAudio;
import se.skoggy.game.IGameContext;
import se.skoggy.input.ITouchInput;
import se.skoggy.input.TouchInputManager;
import se.skoggy.utils.ServiceLocator;

public class ServiceRegistrator {

	public void RegisterServices(IGameContext context){
		
		AudioManager audioManager = new AudioManager();
		
		// TODO: register sounds
		
		audioManager.load(context.getContent());
		
		ServiceLocator.context.registerSingleton(IAudio.class, audioManager);
		ServiceLocator.context.registerSingleton(ITouchInput.class, new TouchInputManager());
		
	}
}
