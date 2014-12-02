package se.skoggy.utils;

import java.util.Random;

public class Rand {

	static Random r;
	static{
		r = new Random(System.currentTimeMillis());
	}
	
	public static void setSeed(int seed){
		r = new Random(seed);
	}

	public static float rand(){
		return r.nextFloat();
	}
	public static float rand(float min, float max){
		return min + r.nextFloat() * (max - min);
	}
}
