package se.powerslidestudios.ld31.particles;

import se.skoggy.atlases.TextureAtlas;
import se.skoggy.content.ContentManager;
import se.skoggy.entity.Entity;
import se.skoggy.utils.GLHelpers;
import se.skoggy.utils.Rand;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ExplosionManager {

	ParticlePool particles;
	Entity template;
	
	public ExplosionManager() {
		particles = new ParticlePool(256);
	}
	
	public void load(ContentManager content){

		TextureAtlas atlas = new TextureAtlas(content);
		atlas.register("atlases/particles");
		
		template = new Entity(atlas.getTexture("explosion"));
	}
	
	public void explode(float x, float y){
		for (int i = 0; i < 32; i++) {
			Particle p = particles.pop();
			p.current = 0f;
			p.duration = Rand.rand(400f, 2000f);
			
			p.startScale = 1f;
			p.endScale = 4f;
			p.rotation = Rand.rand() * 10f;
			p.x = x;
			p.y = y;
			
			float force = 0.05f + Rand.rand() * 0.1f;
			p.rotationSpeed = Rand.rand(-1f, 1f) * 0.01f;

			p.velX = (float)Math.cos(p.rotation) * force;
			p.velY = (float)Math.sin(p.rotation) * force;		
		}
		
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
				
				p.rotation += p.rotationSpeed;

				p.scaleX = p.startScale + (p.endScale - p.startScale) * (p.current / p.duration);
				p.scaleY = p.startScale + (p.endScale - p.startScale) * (p.current / p.duration);
			}
		}
	}
	
	public void draw(SpriteBatch spriteBatch){
		GLHelpers.enableAdditiveBlending(spriteBatch);
		template.setColor(1f, 0.6f, 0.2f, 1f);
		
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
