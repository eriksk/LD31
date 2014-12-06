package se.powerslidestudios.ld31.shaders;

import se.skoggy.content.ContentManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ColorFilterShader {

	ShaderProgram shader;
	FrameBuffer buffer;
	SpriteBatch spriteBatch;
	ColorFilterParameters parameters;
	
	
	public ColorFilterShader(int width, int height, ContentManager content) {
		shader = new ShaderProgram(Gdx.files.internal(content.getRootDirectory() + "shaders/color_filter.vertex"), Gdx.files.internal(content.getRootDirectory() + "shaders/color_filter.fragment"));
        System.out.println("Shader loaded: " + shader.getLog());
		buffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		
		spriteBatch = new SpriteBatch();
		spriteBatch.setShader(shader);
		
		parameters = new ColorFilterParameters();		
	}
	
	public ColorFilterParameters getParameters() {
		return parameters;
	}
	
	float totalTime = 0f;
	public void update(float dt){
		totalTime += dt;
	}
	
	public void begin(){
		buffer.begin();
	    Gdx.gl.glClearColor(1, 1, 1, 0);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void end(){
		buffer.end();
	}
	
	public void render(){

		TextureRegion back = new TextureRegion(buffer.getColorBufferTexture());
	
		back.flip(false, true);
		spriteBatch.begin();
		parameters.bind(shader);		
		spriteBatch.draw(back, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.end();
	}
}
