package se.powerslidestudios.ld31.particles.backgrounds;

import se.skoggy.utils.Pool;

public class AuroraParticlePool extends Pool<AuroraParticle>{

	public AuroraParticlePool(int capacity) {
		super(capacity);
	}
	
	@Override
	public AuroraParticle createEmptyObject() {
		return new AuroraParticle();
	}

}
