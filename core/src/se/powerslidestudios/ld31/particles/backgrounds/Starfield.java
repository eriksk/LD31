package se.powerslidestudios.ld31.particles.backgrounds;

import se.skoggy.content.ContentManager;
import se.skoggy.entity.Entity;
import se.skoggy.utils.Camera2D;
import se.skoggy.utils.GLHelpers;
import se.skoggy.utils.Rand;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Starfield {
	
	Entity aurora;
	TextureRegion starfield, galaxy;
	
	AuroraParticlePool auroras;
	
	
	
	public Starfield() {
	}
	
	public void load(ContentManager content){

		aurora = new Entity(content.loadTexture("gfx/aurora"));
		
		galaxy = content.loadTexture("gfx/galaxy");		
		starfield = content.loadTexture("gfx/starfield");

		galaxy.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		starfield.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		Color[] colors = new Color[]{
				Color.PURPLE.cpy(),
				Color.BLUE.cpy(),
				Color.PINK.cpy()
		};
		
		float speed = 0.05f;		
		auroras = new AuroraParticlePool(256);
		for (int i = 0; i < 256; i++) {
			AuroraParticle p = auroras.pop();
			p.x = Rand.rand(0f, 3000);
			p.y = Rand.rand(0f, 3000);
			p.color = colors[(int)(Rand.rand() * colors.length)];
			float dir = Rand.rand() * 10f;
			p.velX = (float)Math.cos(dir) * speed;
			p.velY = (float)Math.sin(dir) * speed;
			p.rotationSpeed = Rand.rand(-1f, 1f) * 0.001f;
		}
	}
	
	public void update(float dt){
		for (int i = 0; i < auroras.count(); i++) {
			AuroraParticle p = auroras.get(i);
			p.x += p.velX * dt;
			p.y += p.velY * dt;
			p.rotation += p.rotationSpeed * dt;
			if(p.x < 0)
				p.x = 3000;
			if(p.y < 0)
				p.y = 3000;
			if(p.y > 3000)
				p.y = 0;
			if(p.x > 3000)
				p.x = 0;
		}
	}
	
	public void draw(SpriteBatch spriteBatch, Camera2D cam){
		
		GLHelpers.enableAdditiveBlending(spriteBatch);

		spriteBatch.begin();
		spriteBatch.setColor(1f, 1f, 1f, 0.7f);
		spriteBatch.draw(starfield.getTexture(), 0, 0, 3000, 3000, (int)(cam.position.x * 0.005f), (int)(cam.position.y * 0.005f), 512 * 6, 512 * 6, false, true);
		spriteBatch.setColor(1f, 1f, 1f, 0.2f);
		spriteBatch.draw(galaxy.getTexture(), 0, 0, 3000, 3000, (int)(cam.position.x * 0.5f), (int)(cam.position.y * 0.5f), 512 * 6, 512 * 6, false, true);

		for (int i = 0; i < auroras.count(); i++) {
			AuroraParticle p = auroras.get(i);
			aurora.transform.position.x = p.x;
			aurora.transform.position.y = p.y;
			aurora.transform.rotation = p.rotation;
			aurora.setColor(p.color.r, p.color.g, p.color.b, 0.1f);
			aurora.draw(spriteBatch);
		}
		spriteBatch.end();
		
		GLHelpers.disableAdditiveBlending(spriteBatch);		
	}
}
