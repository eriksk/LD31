package se.skoggy.scenes;


public class SceneManager {

	protected Scene scene;
	protected Scene popup;

	public SceneManager() {
	}

	public void pushScene(Scene scene){
		this.scene = scene;
		this.scene.load();
		this.scene.setSceneManager(this);
		this.scene.update(16f);
		startScene(this.scene);
	}
	public void pushScene(Scene scene, boolean preLoaded){
		this.scene = scene;
		this.scene.setSceneManager(this);
		this.scene.update(16f);
		startScene(this.scene);
	}
	
	public void popScene(){
		scene.setState(SceneState.TransitionOut);
		scene.current = 0f;
	}
	
	public void pushPopup(Scene popup){
		this.popup = popup;
		this.popup.load();
		this.popup.update(16f);
		startScene(popup);
	}
	
	private void startScene(Scene scene) {
		scene.setState(SceneState.TransitionIn);
		scene.current = 0f;
	}
	
	public void update(float dt){
		
		if(scene == null)
			return;
		
		scene.current += dt;
	
		if(popup == null){
			scene.update(dt);
		}else{
			scene.updatePassive(dt);
			
			popup.current += dt;
			popup.update(dt);

			if(popup.getState() == SceneState.TransitionIn){
				float progress = popup.current / popup.transitionInDuration();
				if(progress >= 1f){
					popup.setState(SceneState.Active);
				}
			}
			
			if(popup.getState() == SceneState.TransitionOut){
				float progress = popup.current / popup.transitionOutDuration();
				if(progress >= 1f){
					popup.setState(SceneState.Done);
					popup = null;
				}
			}
		}
		
		if(scene.getState() == SceneState.TransitionIn){
			float progress = scene.current / scene.transitionInDuration();
			if(progress >= 1f){
				scene.setState(SceneState.Active);
			}
		}
		
		if(scene.getState() == SceneState.TransitionOut){
			float progress = scene.current / scene.transitionOutDuration();
			if(progress >= 1f){
				scene.setState(SceneState.Done);
			}
		}
	}
	
	public void draw(){
		if(scene == null)
			return;
		
		scene.draw();
		
		if(popup != null)
			popup.draw();
	}
}
