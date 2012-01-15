package com.karolmajta.stp.models;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleManager extends Tickable  {
	private ArrayList<MainMenuObstacleBall> obstacles;
	
	private float x;
	private float y;
	private float w;
	private float h;

	private long offset;
	
	public ObstacleManager(float x, float y, float w, float h) {
		obstacles = new ArrayList<MainMenuObstacleBall>();
		offset = 0;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public ArrayList<MainMenuObstacleBall> getObstacles() {
		return obstacles;
	}
	
	public void cleanup() {
		// check balls above line y = 0. If vy<0 it means this ball
		// has left the space - remove it.
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getY() < 0 && obstacles.get(i).getVY() < 0){
				obstacles.remove(i);
			}
		}
		// check balls below line y = h. If vy>0 it means this ball
		// has left the space - remove it.
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getY() > h && obstacles.get(i).getVY() > 0){
				obstacles.remove(i);
			}
		}
		// check balls before line x = 0. If vx<0 it means this ball
		// has left the space - remove it.
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getX() < 0 && obstacles.get(i).getVX() < 0){
				obstacles.remove(i);
			}
		}
		// check balls after line x = w. If vx>0 it means this ball
		// has left the space - remove it.
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i).getX() > w && obstacles.get(i).getVX() > 0){
				obstacles.remove(i);
			}
		}
	}
	
	public void addBall() {
		Random r = new Random();
		// choose side of screen to spawn ball
		int side = r.nextInt(4);
		float minX = 0;
		float minY = 0;
		float maxX = 0;
		float maxY = 0;
		switch(side){
			case 0:
				// left
				minX = -50;
				maxX = 0;
				minY = 0;
				maxY = h;
				break;
			case 1:
				// top
				minX = 0;
				maxX = w;
				minY = -50;
				maxY = 0;
				break;
			case 2:
				// right
				minX = w;
				maxX = w+50;
				minY = 0;
				maxY = h;
				break;
			case 3:
				// bottom
				minX = 0;
				maxX = w;
				minY = h;
				maxY = h+50;
				break;
		}
		float x = minX + r.nextInt((int)(maxX-minX));
		float y = minY + r.nextInt((int)(maxY-minY));
		
		float rx = w/2 - x;
		float ry = h/2 - y;
		
		float vx = rx;
		float vy = ry;
		
		float normV = (float)Math.sqrt(vx*vx+vy*vy);
		vx /= normV*3;
		vy /= normV*3;
		
		MainMenuObstacleBall b = new MainMenuObstacleBall
				(
						x,
						y,
						w/20,
						vx,
						vy,
						100
				);
		obstacles.add(b);
	}

	@Override
	protected void onTick(long dt) {
		offset += dt;
		if(offset > 1000) {
			offset = 0;
			addBall();
		}
	}
}
