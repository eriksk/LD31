package se.powerslidestudios.ld31;

public class CargoDelivery {

	int boxes;
	int delivered;
	int handled;
	
	public CargoDelivery(int boxes) {
		this.boxes = boxes;
		delivered = 0;
		handled = 0;
	}
	
	public void deliver(){
		delivered++;
		handled++;
	}
	
	public void failDelivery(){
		handled++;
	}
	
	public int getBoxes() {
		return boxes;
	}
	
	public boolean isDone(){
		return handled >= boxes;
	}

	public int getDelivered() {
		return delivered;
	}
}
