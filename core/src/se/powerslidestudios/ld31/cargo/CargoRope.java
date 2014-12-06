package se.powerslidestudios.ld31.cargo;

import se.skoggy.atlases.TextureAtlas;
import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class CargoRope {

	private Entity endpoint, rope;
	
	public CargoRope(TextureAtlas ropeAtlas) {
		endpoint = new Entity(ropeAtlas.getTexture("rope_end"));
		rope = new Entity(ropeAtlas.getTexture("rope"));
		
		endpoint.setScale(0.5f);
		rope.setScaleY(0.5f);
	}
	
	
	public void draw(SpriteBatch spriteBatch, Vector2 start, Vector2 end){
		Vector2 center = new Vector2(Interpolation.linear.apply(start.x, end.x, 0.5f), Interpolation.linear.apply(start.y, end.y, 0.5f));
		
		float length = start.dst(end.x, end.y);
		
		rope.setPosition(center.x, center.y);
		rope.setRotation((float)Math.atan2(end.y - start.y, end.x - start.x));
		rope.setScaleX(length / rope.getSource().width);
		rope.draw(spriteBatch);
		
		endpoint.setPosition(start.x, start.y);
		endpoint.draw(spriteBatch);
		endpoint.setPosition(end.x, end.y);
		endpoint.draw(spriteBatch);
	}
}
