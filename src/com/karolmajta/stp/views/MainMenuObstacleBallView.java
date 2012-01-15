package com.karolmajta.stp.views;

import com.karolmajta.stp.models.MainMenuObstacleBall;

import processing.core.PApplet;

public class MainMenuObstacleBallView extends View<MainMenuObstacleBall> {
	public static final int STROKE = 0xffff00ff;
	
	@Override
	protected void onDraw(PApplet p) {
		int prevFill = p.g.fillColor;
		p.fill(0x55ffffff);
		
		p.ellipse(
				model.getX(),
				model.getY(),
				model.getRadius()*2,
				model.getRadius()*2
		);
		
		
		p.fill(prevFill);
	}
}
