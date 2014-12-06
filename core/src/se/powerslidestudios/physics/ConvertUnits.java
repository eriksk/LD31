package se.powerslidestudios.physics;

public class ConvertUnits {

	static final float WORLD_TO_BOX = 0.005f;
	static final float BOX_WORLD_TO = 100f;

	public static float toSim(float world){
		return world * WORLD_TO_BOX;
	}
	
	public static float fromSim(float box){
		return box * BOX_WORLD_TO;
	}
}
