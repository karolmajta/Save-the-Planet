package com.karolmajta.stp.views;

import com.karolmajta.stp.models.IProgress;

import processing.core.PApplet;
import processing.core.PFont;

public class ProgressView extends View<IProgress> {
	private static final int SHOW = 0;
	private static final int SHRINK = 1;
	private static final int HIDE = 2;
	
	private static final float SHRINK_SPEED = 0.001f;
	private static final float ALPHA_SPEED = 0.5f;
	private static final int OVERLAY = 0xff000000;
	
	private int mode;
	private Integer lastTick = null;
	private Integer deltaTime = null;
	private float textX;
	private float alpha = 0;
	
	private String name;
	private PFont pFont;
	private float x;
	private float y;
	private float w;
	private float outerHeight;
	private float innerHeight;
	private int background;
	private int inactiveBar;
	private int activeBar;
	
	public ProgressView
			(
					String name,
					PFont pFont,
					float x,
					float y,
					float w,
					float outerHeight,
					float innerHeight,
					int background,
					int inactiveBar,
					int activeBar
			) {
		this.mode = SHOW;
		
		this.name = name;
		this.pFont = pFont;
		this.x = x;
		this.y = y;
		this.w = w;
		textX = x+(w/2);
		this.outerHeight = outerHeight;
		this.innerHeight = innerHeight;
		this.background = background;
		this.inactiveBar = inactiveBar;
		this.activeBar = activeBar;
	}
	
	@Override
	protected void onDraw(PApplet p) {
		int newTick = p.millis();
		if(lastTick == null){
			lastTick = newTick;
		}
		deltaTime = newTick - lastTick;
		lastTick = newTick;
		
		switch(mode){
			case SHOW:
				show(p);
				break;
			case SHRINK:
				shrink(p);
				break;
			case HIDE:
				hide(p);
				break;
		}
	}
	
	private void show(PApplet p) {
		float midpoint = w*((float)model.getProcessed()/model.getTotal());
		
		int prevStroke = p.g.strokeColor;
		float prevWeight = p.g.strokeWeight;
		int prevTextAlign = p.g.textAlign;
		PFont prevPFont = p.g.textFont;
		
		p.stroke(background);
		p.strokeWeight(outerHeight);
		
		p.line(x, y, x+w, y);
		
		p.stroke(inactiveBar);
		p.strokeWeight(innerHeight);
		
		p.line(x, y, x+w, y);
		
		p.stroke(activeBar);
		
		p.line(x, y, x+midpoint, y);
		
		p.stroke(background);
		if(pFont != null){
			p.textFont(pFont);
		}
		p.textAlign(p.CENTER);
		p.text(name, textX, y+3.5f*outerHeight, p.width);
		
		p.stroke(prevStroke);
		p.strokeWeight(prevWeight);
		p.textAlign(prevTextAlign);
		if(prevPFont != null){
			p.textFont(prevPFont);
		}
		
		if(!model.hasNextTask() && mode == SHOW){
			mode = SHRINK;
		}
	}
	
	private void shrink(PApplet p) {
		float vel = p.width*SHRINK_SPEED;
		if(x < p.width/2){
			x += vel*deltaTime;
		}else{
			x = p.width/2+0.1f;
		}
		if(w > 0){
			w -= 2*vel*deltaTime;
		}else{
			w = -0.1f;
		}
		show(p);
		
		if(w < 1 && x > p.width/2){
			mode = HIDE;
		}
	}
	
	private void hide(PApplet p) {
		show(p);
		int prevColor = p.g.fillColor;
		
		float red = p.red(OVERLAY);
		float green = p.green(OVERLAY);
		float blue = p.blue(OVERLAY);
		
		p.fill(red, green, blue, alpha);
		p.ellipse(textX, y, p.width/3, p.width/3);
		
		p.fill(prevColor);
		
		if(alpha < 255){
			alpha += ALPHA_SPEED*deltaTime;
		}
		if(alpha > 254){
			setVisible(false);
		}
	}
}
