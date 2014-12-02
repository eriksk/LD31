package se.skoggy.content;

import java.text.MessageFormat;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class ContentManager implements Disposable{

	String contentRoot;
	boolean flipYOnSprites;

	private HashMap<String, Texture> textureCache;
	
	public ContentManager(String contentRoot, boolean flipYOnSprites){
		this.contentRoot = contentRoot;
		this.flipYOnSprites = flipYOnSprites;
		if(!this.contentRoot.endsWith("/") && this.contentRoot != ""){
			this.contentRoot += "/";
		}
		textureCache = new HashMap<String, Texture>();
	}

	public String getRootDirectory() {
		return contentRoot;
	}

	public Texture loadRawTexture(String name) {
		if(textureCache.containsKey(name))
			return textureCache.get(name);
		
		Texture texture = new Texture(Gdx.files.internal(contentRoot + name + ".png"));
		textureCache.put(name, texture);
		return texture;
	}

	public TextureRegion loadTexture(String name){
		return loadTexture(name, ".png");
	}
	
	public TextureRegion loadTexture(String name, String fileEnding){
		
		Texture tex = null;
		if(textureCache.containsKey(name)){
			tex = textureCache.get(name);
		}else{
			tex = new Texture(Gdx.files.internal(MessageFormat.format("{0}{1}{2}", contentRoot, name, fileEnding)));
		}
		
		TextureRegion texture = new TextureRegion(tex);
		texture.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture.flip(false, flipYOnSprites);
		return texture;
	}

	public TextureRegion[] loadTextureSheet(String name, int tileWidth, int tileHeight){
		TextureRegion tex = new TextureRegion(new Texture(Gdx.files.internal(contentRoot + name + ".png")));
		TextureRegion[][] texes = tex.split(tileWidth, tileHeight);
		int count = 0;
		TextureRegion[] textures = new TextureRegion[texes.length * texes.length];
		for (int i = 0; i < texes.length; i++) {
			for (int j = 0; j < texes.length; j++) {
				textures[count] = texes[i][j];
				count++;
			}
		}
		return textures;
	}

	public BitmapFont loadFont(String name){
		return new BitmapFont(Gdx.files.internal(MessageFormat.format("{0}fonts/{1}.fnt", contentRoot, name)), Gdx.files.internal(MessageFormat.format("{0}fonts/{1}.png", contentRoot, name)), true);
	}

	@Override
	public void dispose() {
		// TODO: cache all textures and dispose here
	}
}
