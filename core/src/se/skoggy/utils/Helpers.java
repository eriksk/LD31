package se.skoggy.utils;

import com.badlogic.gdx.graphics.Color;

public class Helpers {

	public static float lerp(float x, float y, float w){
		return x + (y - x) * w;
	}

	public static Color lerpC(Color color1, Color color2, float w){
		return new Color(
				lerp(color1.r, color2.r, w),
				lerp(color1.g, color2.g, w),
				lerp(color1.b, color2.b, w),
				lerp(color1.a, color2.a, w));
	}
}
