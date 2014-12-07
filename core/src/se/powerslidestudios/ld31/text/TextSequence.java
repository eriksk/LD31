package se.powerslidestudios.ld31.text;

import se.skoggy.utils.TimerTrig;

public class TextSequence {

	int currentLine;
	private String[] lines;
	TimerTrig lineTrig;
	
	public TextSequence(String[] lines, float interval) {
		this.lines = lines;
		lineTrig = new TimerTrig(interval);
	}
	
	public boolean isDone(){
		return currentLine >= lines.length - 1;
	}
	
	public void update(float dt){
		if(!isDone()){
			if(lineTrig.isTrigged(dt)){
				currentLine++;
				if(currentLine > lines.length - 1)
					currentLine = lines.length - 1;
			}
		}
	}

	public String getCurrentText() {
		return lines[currentLine];
	}
	
}
