package se.powerslidestudios.ld31.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.powerslidestudios.ld31.LD31;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= 1280;
		config.height = 720;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new LD31(), config);
	}
}
