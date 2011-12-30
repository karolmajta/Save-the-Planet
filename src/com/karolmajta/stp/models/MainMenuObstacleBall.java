package com.karolmajta.stp.models;

/**
 * 
 * @author Karol
 *
 */
public class MainMenuObstacleBall extends Tickable {
	private float x; // pixels
	private float y; // pixels
	private float radius; // pixels
	private float vx; // pixels/millisecond
	private float vy; // pixels/millisecond
	
	public MainMenuObstacleBall(float x, float y, float r, float vx, float vy) {
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
}