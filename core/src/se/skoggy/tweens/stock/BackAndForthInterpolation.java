package se.skoggy.tweens.stock;

import com.badlogic.gdx.math.Interpolation;

public class BackAndForthInterpolation extends Interpolation{

    private Interpolation interpolation;

    public BackAndForthInterpolation(Interpolation interpolation){
    	this.interpolation = interpolation;
    }
    
	@Override
	public float apply(float a) {
        if (a <= 0.5f)
            return interpolation.apply(a * 2f);
        return interpolation.apply(2f - (a * 2f));
	}
}
