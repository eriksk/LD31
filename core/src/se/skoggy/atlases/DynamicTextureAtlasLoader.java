package se.skoggy.atlases;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;

public class DynamicTextureAtlasLoader {

	public DynamicTextureAtlasData load(String path) {
		Gson gson = new Gson();
		return gson.fromJson(Gdx.files.internal(path).readString(), DynamicTextureAtlasData.class);
	}
}
