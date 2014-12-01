package se.skoggy.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class EntityBehavior {
	public abstract void Update(float dt, Entity owner);
	public abstract void BeforeDraw(SpriteBatch spriteBatch, Entity owner);
	public abstract void AfterDraw(SpriteBatch spriteBatch, Entity owner);
}
