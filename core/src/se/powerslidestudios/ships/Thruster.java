package se.powerslidestudios.ships;

import se.skoggy.atlases.DynamicTexture;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.math.Vector2;

public class Thruster extends Entity{
	
	private Vector2 offset;
	
	public Thruster(DynamicTexture texture) {
		super(texture);
		offset = new Vector2();
	}

	public void setOffset(float x, float y) {
		offset.x = x;
		offset.y = y;
	}	
	
	public Vector2 getOffset() {
		return offset;
	}

	public void updateFromParent(PlayerShip parent) {
		float x = parent.transform.position.x;
		float y = parent.transform.position.y;
		transform.position.x = x + offset.x;
		transform.position.y = y + offset.y;
	}
}
