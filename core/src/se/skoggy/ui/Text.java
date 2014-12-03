package se.skoggy.ui;

import se.skoggy.entity.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text extends Entity{

	private String text;
	private TextAlign align;
	private boolean dirty;
	private boolean drawShadow;
	private float wrapWidth;
	private Color shadowColor;
	
	public Text() {
		this("", TextAlign.left);
	}
	
	public Text(String text, TextAlign align){
		super();
		this.text = text;
		this.align = align;
		shadowColor = Color.BLACK.cpy();
		dirty = true;
		drawShadow = true;
	}

	public void setText(String text) {
		this.text = text;
		dirty = true;
	}
	
	public String getText() {
		return text;
	}
	
	public void setAlign(TextAlign align) {
		this.align = align;
		dirty = true;
	}
	
	public void setDrawShadow(boolean drawShadow) {
		this.drawShadow = drawShadow;
	}
	
	public boolean isDrawShadow() {
		return drawShadow;
	}
	
	public void setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setWrap(float wrapWidth){
		this.wrapWidth = wrapWidth;
		dirty = true;
	}
	
	@Override
	public void setScale(float scalar) {
		transform.scale.x = scalar;
		transform.scale.y = scalar;
		dirty = true;
	}

	private void calculateAlign(BitmapFont font) {
		if(transform.scale.x != 0f && transform.scale.y != 0f)
			font.setScale(transform.scale.x, transform.scale.y);
		
		TextBounds bounds = font.getBounds(text);
		
		origin.y = (bounds.height / 2f);
		
		if(align == TextAlign.left){
			origin.x = 0f;	
		}else if(align == TextAlign.center){
			origin.x = (bounds.width / 2f);
			if(wrapWidth > 0f){
				origin.x = wrapWidth / 2f;
				origin.y = (bounds.height * (bounds.width / wrapWidth));
			}
		}if(align == TextAlign.right){
			origin.x = bounds.width;
			if(wrapWidth > 0f){
				origin.x = wrapWidth;
			}
		}
		dirty = false;
	}
	
	public void draw(BitmapFont font, SpriteBatch spriteBatch){
		dirty = true;
		if(dirty){
			calculateAlign(font);
		}
		if(transform.scale.x != 0f && transform.scale.y != 0f)
			font.setScale(transform.scale.x, transform.scale.y);
		
		if(transform.scale.x == 0f || transform.scale.y == 0f)
			return;
		
		HAlignment hAlign = HAlignment.LEFT;
		if(align == TextAlign.center){
			hAlign = HAlignment.CENTER;
		}else if(align == TextAlign.right){
			hAlign = HAlignment.RIGHT;
		}
		
		if(drawShadow){
			font.setColor(shadowColor);
			if(wrapWidth > 0f){
				font.drawWrapped(spriteBatch, text, transform.position.x - origin.x + 3f * transform.scale.x, transform.position.y + origin.y + 3f * -transform.scale.y, wrapWidth, hAlign);
			}else{
				font.draw(spriteBatch, text, transform.position.x - origin.x + 3f * transform.scale.x, transform.position.y - origin.y + 3f * -transform.scale.y);	
			}
		}
		
		font.setColor(color);
		if(wrapWidth > 0f){
			font.drawWrapped(spriteBatch, text, transform.position.x - origin.x, transform.position.y + origin.y, wrapWidth, hAlign);
		}else{
			font.draw(spriteBatch, text, transform.position.x - origin.x, transform.position.y - origin.y);	
		}
	}
}
