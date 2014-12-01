package se.skoggy.entity;

import com.badlogic.gdx.math.Vector2;

public class Transform {

	public Vector2 position, velocity, scale;
	public float rotation;

	public Transform() {
		position = new Vector2();
		scale = new Vector2(1f, 1f);
		velocity = new Vector2();
		rotation = 0f;
	}

	public void setScale(float xy){
		scale.x = xy;
		scale.y = xy;
	}

	/**
	 * Sets properties from a template
	 * @param transform the template
	 */
	public void set(Transform transform) {
		this.position.x = transform.position.x;
		this.position.y = transform.position.y;
		this.velocity.x = transform.velocity.x;
		this.velocity.y = transform.velocity.y;
		this.scale.x = transform.scale.x;
		this.scale.y = transform.scale.y;
		this.rotation = transform.rotation;
	}
}
