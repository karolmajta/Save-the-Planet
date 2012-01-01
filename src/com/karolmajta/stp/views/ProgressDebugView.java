package com.karolmajta.stp.views;

import com.karolmajta.stp.models.IProgress;

import processing.core.PApplet;

public class ProgressDebugView extends View<IProgress> {
	private float x;
	private float y;
	private float w;
	private float h;
	
	public ProgressDebugView(float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	@Override
	protected void onDraw(PApplet p) {
		float greenEnd = w*((float)model.getProcessed()/model.getTotal());
		
		int prevFill = p.g.fillColor;
		int prevStroke = p.g.strokeColor;
		
		p.noStroke();
		
		p.fill(0xffff0000);
		p.rect(x, y, w, h);
		
		p.fill(0xff00ff00);
		p.rect(x,  y, greenEnd, h);
		
		p.fill(prevFill);
		p.stroke(prevStroke);
	}
}
