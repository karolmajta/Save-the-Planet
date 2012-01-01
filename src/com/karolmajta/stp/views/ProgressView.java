package com.karolmajta.stp.views;

import com.karolmajta.stp.models.IProgress;

import processing.core.PApplet;

public class ProgressView extends View<IProgress> {
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
					float x,
					float y,
					float w,
					float outerHeight,
					float innerHeight,
					int background,
					int inactiveBar,
					int activeBar
			) {
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.outerHeight = outerHeight;
		this.innerHeight = innerHeight;
		this.background = background;
		this.inactiveBar = inactiveBar;
		this.activeBar = activeBar;
	}
	
	@Override
	protected void onDraw(PApplet p) {
		float midpoint = w*((float)model.getProcessed()/model.getTotal());
		
		int prevStroke = p.g.strokeColor;
		float prevWeight = p.g.strokeWeight;
		
		p.stroke(background);
		p.strokeWeight(outerHeight);
		
		p.line(x, y, x+w, y);
		
		p.stroke(inactiveBar);
		p.strokeWeight(innerHeight);
		
		p.line(x, y, x+w, y);
		
		p.stroke(activeBar);
		
		p.line(x, y, x+midpoint, y);
		
		p.stroke(prevStroke);
		p.strokeWeight(prevWeight);
	}
}
