package se.skoggy.tweens;

import java.util.ArrayList;
import java.util.List;

public class TweenManager {

	private final List<Tween> tweens;
	
	public TweenManager() {
		tweens = new ArrayList<Tween>();
	}

	public void add(Tween tween){
		tweens.add(tween);
	}
	
	public void update(float dt){
		for (int i = 0; i < tweens.size(); i++) {
			Tween t = tweens.get(i);
			t.update(dt);
			if(t.done()){
				tweens.remove(i--);
			}
		}
	}
}
