package se.skoggy.ui;

import com.badlogic.gdx.graphics.Color;

public class Label extends Text implements UIElement {
	
	public Label(String text, TextAlign align) {
		super(text, align);
		setShadowColor(new Color(0f, 0f, 0f, 0.4f));
	}
}
