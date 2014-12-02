package se.skoggy.atlases;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class DynamicTexture {

        public Texture texture;
        public Rectanglei source;
        public String atlasName;
        public String name;

        public DynamicTexture(String atlasName, String name, Texture texture, Rectanglei source){
            this.atlasName = atlasName;
            this.texture = texture;
            this.source = source;
            this.name = name;
        }
   }
