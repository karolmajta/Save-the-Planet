package com.karolmajta.stp.models;

import java.util.HashSet;

import android.util.Log;
import junit.framework.TestCase;

/**
 * 
 * @author Karol
 *
 */
public class MainMenuObstacleBall extends Tickable
		implements ICanCollide<MainMenuItemBall> {
	private HashSet ignoredForCollision;
	
	private float x; // pixels
	private float y; // pixels
	private float radius; // pixels
	private float vx; // pixels/millisecond
	private float vy; // pixels/millisecond
	
	public MainMenuObstacleBall(float x, float y, float r, float vx, float vy) {
		ignoredForCollision = new HashSet();
		
		this.x = x;
		this.y = y;
		this.radius = r;
		this.vx = vx;
		this.vy = vy;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}



	public float getX() {
		return x;
	}



	public void setX(float x) {
		this.x = x;
	}



	public float getY() {
		return y;
	}



	public void setY(float y) {
		this.y = y;
	}



	public float getVX() {
		return vx;
	}



	public void setVX(float vx) {
		this.vx = vx;
	}



	public float getVY() {
		return vy;
	}



	public void setVY(float vy) {
		this.vy = vy;
	}


	/**
	 * Integrates position based on current speed. Speed does not change
	 */
	@Override
	protected void onTick(long dt) {
		x += vx*dt;
		y += vy*dt;
	}

	/**
	 * Lol! Need to think about this
	 * 
	 * @param other {@link MainMenuItemBall} instance to collide with this
	 */
	@Override
	public void collide(MainMenuItemBall other) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Checks collision condition basing on overlapping circles
	 */
	@Override
	public boolean affectedBy(MainMenuItemBall other) {
		float x0 = x;
		float y0 = y;
		float r0 = radius;
		
		float x1 = other.getCurrentX();
		float y1 = other.getCurrentY();
		float r1 = other.getRadius();
		
		boolean result;
		float dist =(float)Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
		if(dist < r0 + r1){
			result = true;
		}else{
			result = false;
		}
		
		if(result){
			if(ignoredForCollision.contains(other)){
				return false;
			}else{
				ignoredForCollision.add(other);
				return true;
			}
		}else{
			ignoredForCollision.remove(other);
			return false;
		}
	}
}