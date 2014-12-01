package se.powerslidestudios.ld31.managers;

import se.skoggy.content.ContentManager;

public abstract class Manager {
	public abstract void load(ContentManager content);
	public abstract void update(float dt);
}
