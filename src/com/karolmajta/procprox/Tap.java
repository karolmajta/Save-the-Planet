package com.karolmajta.procprox;

public class Tap {
	private float startX;
	private float startY;
	private float stopX;
	private float stopY;
	private int duration;
	
	public Tap
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

	public int getDuration() {
		return duration;
	}
	
}
