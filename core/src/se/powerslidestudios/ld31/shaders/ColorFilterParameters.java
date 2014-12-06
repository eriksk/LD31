package se.powerslidestudios.ld31.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ColorFilterParameters extends ShaderParameters{
	public  float r = 1.0f;
	public  float g = 0.98f;
	public  float b = 0.85f;
	public  float burn = 1.5f;
	public  float saturation = 0.6f;
	public  float brightness = 0.05f;
	
	public ColorFilterParameters() {
	}

	public void bind(ShaderProgram shader){
		shader.setUniformf("r", r);
		shader.setUniformf("g", g);
		shader.setUniformf("b", b);
		shader.setUniformf("burn", burn);
		shader.setUniformf("saturation", saturation);
		shader.setUniformf("brightness", brightness);
	}
	
}