package se.skoggy.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GLHelpers {

    public static void enableAdditiveBlending(SpriteBatch spriteBatch){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    public static void disableAdditiveBlending(SpriteBatch spriteBatch){
    	spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    public static void enableAlphaBlending(SpriteBatch spriteBatch){
        Gdx.gl.glEnable(GL20.GL_BLEND);
    	spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
