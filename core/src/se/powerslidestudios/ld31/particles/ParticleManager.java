package se.powerslidestudios.ld31.particles;

import se.skoggy.atlases.TextureAtlas;
import se.skoggy.content.ContentManager;
import se.skoggy.entity.Entity;
import se.skoggy.utils.GLHelpers;
import se.skoggy.utils.Rand;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleManager {

	TextureAtlas atlas;
	ParticlePool particles;
	Entity template;
	
	public ParticleManager() {
		particles = new ParticlePool(1024);
	}
	
	public void load(ContentManager content){
		atlas = new TextureAtlas(content);
		atlas.register("atlases/particles");
		
		template = new Entity(atlas.getTexture("thruster"));
	}
	
	public void spawnThruster(float x, float y, float rotation){
		Particle p = particles.pop();
		p.current = 0f;
		p.duration = Rand.rand(100f, 300f);
		
		p.scaleX = 0.5f;
		p.scaleY = 0.5f;
		p.rotation = rotation;
		p.x = x;
		p.y = y;
		
		float force = 0.1f;

		p.velX = (float)Math.cos(rotation) * force;
		p.velY = (float)Math.sin(rotation) * force;
		
	}
	
	public void update(float dt){
		for (int i = 0; i < particles.count(); i++) {
			Particle p = particles.get(i);
			p.current += dt;
			if(p.current >= p.duration){
				particles.push(i--);
			}else{
				p.x += p.velX * dt;
				p.y += p.velY * dt;

				p.scaleX = 0.5f + (p.current / p.duration) * 0.5f;
				p.scaleY = 0.5f + (p.current / p.duration) * 0.5f;
			}
		}
	}
	
	public void draw(SpriteBatch spriteBatch){
		
		GLHelpers.enableAdditiveBlending(spriteBatch);
		template.setColor(0.3f, 0.4f, 0.9f, 1f);
		
		for (int i = 0; i < particles.count(); i++) {
			Particle p = particles.get(i);

			template.transform.position.x = p.x;
			template.transform.position.y = p.y;
			template.transform.rotation = p.rotation;
			template.transform.scale.x = p.scaleX;
			template.transform.scale.y = p.scaleY;
			
			template.color.a = 1f - (p.current / p.duration);
			
			template.draw(spriteBatch);
		}
		
		GLHelpers.disableAdditiveBlending(spriteBatch);
	}
}
