package se.powerslidestudios.ld31.particles.backgrounds;

import se.skoggy.content.ContentManager;
import se.skoggy.entity.Entity;
import se.skoggy.utils.Camera2D;
import se.skoggy.utils.GLHelpers;
import se.skoggy.utils.Rand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Starfield {
	
	Entity stars, smoke;
	Vector2Color[] smokes;
	
	public Starfield() {
	}
	
	public void load(ContentManager content){
		stars = new Entity(content.loadTexture("gfx/stars"));
		smoke = new Entity(content.loadTexture("gfx/smoke"));
		
		Color[] colors = new Color[]{
				Color.PURPLE.cpy(),
				Color.BLUE.cpy(),
				Color.PINK.cpy()
		};
		
		int smokeCount = 36;
		
		// TODO: change to particles instead and make them in and out
		smokes = new Vector2Color[smokeCount];
		for (int i = 0; i < smokes.length; i++) {
			smokes[i] = new Vector2Color();
			smokes[i].position.x = Rand.rand(-3000, 3000);
			smokes[i].position.y = Rand.rand(-3000, 500);
			smokes[i].color = colors[(int)(Rand.rand() * colors.length)];
			smokes[i].color.a = 0.4f;
		}
	}
	
	public void update(float dt){
		
	}
	
	public void draw(SpriteBatch spriteBatch, Camera2D cam){
		
		stars.setScale(2f);
		smoke.setScale(2f);
		stars.color.a = 0.6f;

		GLHelpers.enableAdditiveBlending(spriteBatch);

		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		for (int i = -8; i < 8; i++) {
			for (int j = -8; j < 8; j++) {
				stars.setPosition(i * 1024, j * 1024);
				stars.draw(spriteBatch);
			}
		}
		spriteBatch.end();
		
		spriteBatch.setProjectionMatrix(cam.getParallax(1.2f));
		spriteBatch.begin();

		for (int i = 0; i < smokes.length; i++) {
			Vector2Color s = smokes[i];
			smoke.setPosition(s.position.x, s.position.y);
			smoke.setColor(s.color.r, s.color.g, s.color.b, s.color.a);
			smoke.draw(spriteBatch);
		}
		spriteBatch.end();

		// TODO: dont draw two passes like this
		spriteBatch.setProjectionMatrix(cam.getParallax(0.8f));
		spriteBatch.begin();

		for (int i = 0; i < smokes.length; i++) {
			Vector2Color s = smokes[i];
			smoke.setPosition(s.position.x, s.position.y);
			smoke.setColor(s.color.r, s.color.g, s.color.b, s.color.a);
			smoke.draw(spriteBatch);
		}
		spriteBatch.end();
		GLHelpers.disableAdditiveBlending(spriteBatch);		
	}
}
