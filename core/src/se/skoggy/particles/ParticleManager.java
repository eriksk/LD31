package se.skoggy.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ParticleManager {
	public abstract void load();
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch sb);
}
