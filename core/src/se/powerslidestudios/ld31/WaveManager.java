package se.powerslidestudios.ld31;

import se.skoggy.utils.Rand;

public class WaveManager {

	int currentWave;
	
	CargoDelivery currentDelivery;
	
	public WaveManager() {
		restart();
	}

	public void restart(){
		currentWave = 1;
		currentDelivery = new CargoDelivery(3);
	}
	
	public void gotoNextWave(){
		currentWave++;
		currentDelivery = new CargoDelivery(2 + (int)(Rand.rand() * 5f));
	}
	
	public boolean waveDone(){
		return currentDelivery.isDone();
	}
	
	public int getCurrentWave() {
		return currentWave;
	}	
	
	public CargoDelivery getCurrentDelivery() {
		return currentDelivery;
	}
}
