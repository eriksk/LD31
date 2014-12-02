package se.skoggy.atlases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import se.skoggy.content.ContentManager;

public class TextureAtlas {

    private ContentManager content;
    private HashMap<String, DynamicTexture> textures;
    private DynamicTextureAtlasLoader loader;
    private List<String> atlasNames; 

    public TextureAtlas(ContentManager content){
        this.content = content;
        this.textures = new HashMap<String, DynamicTexture>();
        this.loader = new DynamicTextureAtlasLoader();
        this.atlasNames = new ArrayList<String>();
    }

    public Iterator<String> getAll()
    {
        return textures.keySet().iterator();
    }

    public boolean containsAtlasWithName(String name)
    {
        return atlasNames.contains(name);
    }

    public void register(String atlasName)
    {
        atlasNames.add(atlasName);
        DynamicTextureAtlasData atlasData = loader.load(content.getRootDirectory() + atlasName + ".json");
        Texture texture = content.loadRawTexture(atlasName);
        for(DynamicTextureAtlasDataFrame frame : atlasData.frames)
        {
            String assetName = frame.filename.replace(".png", "");

            if (textures.containsKey(assetName))
                throw new RuntimeException(String.format("'{0}' is already defined, loading dynamic atlas '{1}'", assetName, atlasName));

            textures.put(assetName, new DynamicTexture(atlasData.name, assetName, texture, frame.frame));
        }
    }

    public DynamicTexture getTexture(String name)
    {
        if (name == null)
            return null;

        if (!textures.containsKey(name))
            return null;

        return textures.get(name);
    }
}
