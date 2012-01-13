package com.karolmajta.procprox;

import java.util.ArrayList;

public class DragDetector {

	private ArrayList<Drag> drags;
	private ArrayList<IEventFilter<Drag>> filters;
	private boolean mousePressed;
	private float startX;
	private float startY;
	private float lastX;
	private float lastY;
	private int duration;
	
	public DragDetector() {
		drags = new ArrayList<Drag>();
		filters = new ArrayList<IEventFilter<Drag>>();
		mousePressed = false;
	}
	
	public boolean hasDrags() {
		if(drags.size() > 0) {
			return true;
		}else{
			return false;
		}
	}

	public ArrayList<Drag> getDrags() {
		return drags;
	}
	
	public void flush() {
		drags = new ArrayList<Drag>();
	}

	public void update(float mouseX, float mouseY, boolean mousePressed, int milis) {
		if(!this.mousePressed && mousePressed){
			this.mousePressed = true;
			startX = mouseX;
			startY = mouseY;
			duration = 0;
		}
		if(mousePressed){
			lastX = mouseX;
			lastY = mouseY;
			duration += milis;
		}
		if(this.mousePressed && !mousePressed){
			this.mousePressed = false;
			Drag d = new Drag
					(
							startX,
							startY,
							lastX,
							lastY,
							duration
					);
			boolean shouldAdd = true;
			for(IEventFilter<Drag> f : filters){
				if(f.filter(d)){
					shouldAdd = false;
					break;
				}
			}
			if(shouldAdd){
				drags.add(d);
			}
		}
	}

	public void addFilter(IEventFilter<Drag> filter) {
		filters.add(filter);
	}
}
