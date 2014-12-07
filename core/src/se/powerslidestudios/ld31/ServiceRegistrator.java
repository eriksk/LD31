package se.powerslidestudios.ld31;

import se.skoggy.audio.AudioManager;
import se.skoggy.audio.IAudio;
import se.skoggy.game.IGameContext;
import se.skoggy.input.ITouchInput;
import se.skoggy.input.TouchInputManager;
import se.skoggy.utils.ServiceLocator;

public class ServiceRegistrator {

	public void RegisterServices(IGameContext context){
		
		AudioManager audioManager = new AudioManager("audio");
		
		// TODO: register sounds

		audioManager.registerSound("ui_click.ogg");
		audioManager.registerSound("explosion.ogg");

		audioManager.registerSound("delivery_successful.ogg");
		audioManager.registerSound("delivery_failed.ogg");
		audioManager.registerSound("next_wave.ogg");

		audioManager.registerSong("thrust.ogg");
		audioManager.registerSong("song_1.ogg");
		
		audioManager.load(context.getContent());
		
		ServiceLocator.context.registerSingleton(IAudio.class, audioManager);
		ServiceLocator.context.registerSingleton(ITouchInput.class, new TouchInputManager());
		
	}
}
