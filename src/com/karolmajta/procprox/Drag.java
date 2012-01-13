package com.karolmajta.procprox;

public class Drag {
	private float startX;
	private float startY;
	private float stopX;
	private float stopY;
	private int duration;
	
	public Drag
			(
					float startX,
					float startY,
					float stopX,
					float stopY,
					int duration
			) {
		this.startX = startX;
		this.startY = startY;
		this.stopX = stopX;
		this.stopY = stopY;
		this.duration = duration;
	}
	
	public float getStartX() {
		return startX;
	}
	
	public float getStartY() {
		return startY;
	}
	
	public float getStopX() {
		return stopX;
	}
	
	public float getStopY() {
		return stopY;
	}
	
	public float getDuration() {
		return duration;
	}

	public float getVelocityX() {
		return (stopX-startX)/duration;
	}

	public float getVelocityY() {
		return (stopY-startY)/duration;
	}
}
