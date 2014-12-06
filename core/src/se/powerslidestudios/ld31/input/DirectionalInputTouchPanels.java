package se.powerslidestudios.ld31.input;

import se.skoggy.input.ITouchInput;
import se.skoggy.utils.ServiceLocator;

import com.badlogic.gdx.math.Vector2;

public class DirectionalInputTouchPanels {

	TouchArea area;
	private Vector2 screenCenter;
	
	public DirectionalInputTouchPanels(Vector2 screenCenter) {
		this.screenCenter = screenCenter;
		area = TouchArea.none;
	}
	
	public TouchArea getArea() {
		return area;
	}
	
	public void update(){
		ITouchInput input = ServiceLocator.context.locate(ITouchInput.class);
		
		area = TouchArea.none;
		
		if(input.touching()){
			float x = input.x();
			float y = input.y();
			
			if(x < screenCenter.x){
				// top, bottom or left
				if(y < screenCenter.y){
					// top or left
					if(Math.abs(screenCenter.x - x) > Math.abs(screenCenter.y - y)){
						// left
						area = TouchArea.left;
					}else{
						// top
						area = TouchArea.top;
					}
				}else{
					// bottom or left
					if(Math.abs(screenCenter.x - x) > Math.abs(screenCenter.y - y)){
						// left
						area = TouchArea.left;
					}else{
						// bottom
						area = TouchArea.bottom;
					}
				}
			}else{
				// top bottom or right
				if(y < screenCenter.y){
					// top or right
					if(Math.abs(screenCenter.x - x) > Math.abs(screenCenter.y - y)){
						// right
						area = TouchArea.right;
					}else{
						// top
						area = TouchArea.top;
					}
				}else{
					// bottom or right
					if(Math.abs(screenCenter.x - x) > Math.abs(screenCenter.y - y)){
						// right
						area = TouchArea.right;
					}else{
						// bottom
						area = TouchArea.bottom;
					}
				}
			}
		}
	}
}
