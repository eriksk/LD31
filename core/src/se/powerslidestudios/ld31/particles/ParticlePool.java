package se.powerslidestudios.ld31.particles;

import se.skoggy.utils.Pool;

public class ParticlePool extends Pool<Particle>{

	public ParticlePool(int capacity) {
		super(capacity);
	}
	
	@Override
	public Particle createEmptyObject() {
		return new Particle();
	}

}
