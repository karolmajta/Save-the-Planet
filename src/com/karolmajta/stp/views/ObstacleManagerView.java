package com.karolmajta.stp.views;

import com.karolmajta.stp.models.MainMenuObstacleBall;
import com.karolmajta.stp.models.ObstacleManager;

import processing.core.PApplet;

public class ObstacleManagerView extends View<ObstacleManager> {
	public static final int STROKE = 0xffff00ff;
	
	@Override
	protected void onDraw(PApplet p) {
		int prevFill = p.g.fillColor;
		int prevStroke = p.g.strokeColor;
		p.fill(0x55ffffff);
		p.noStroke();
		
		for(MainMenuObstacleBall ob : model.getObstacles())
		p.ellipse(
				ob.getX(),
				ob.getY(),
				ob.getRadius()*2,
				ob.getRadius()*2
		);
		
		
		p.fill(prevFill);
		p.stroke(prevStroke);
	}
}
