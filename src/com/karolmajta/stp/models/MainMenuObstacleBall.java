package com.karolmajta.stp.models;

public class MainMenuObstacleBall extends Tickable {
	private float x;
	private float y;
	private float radius;
	private float vx;
	private float vy;
	
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



	@Override
	protected void onTick(long dt) {
		x += vx*dt;
		y += vy*dt;
	}
}