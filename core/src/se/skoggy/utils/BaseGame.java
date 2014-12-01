package se.skoggy.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public abstract class BaseGame extends Stage implements Disposable{
	public BaseGame(){
		super();
	}
	public abstract void load();
	public abstract void update(float dt);
}
