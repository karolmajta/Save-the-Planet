package com.karolmajta.procprox;

import java.util.ArrayList;

public class TapDetector {
	private ArrayList<Tap> taps;
	private ArrayList<IEventFilter<Tap>> filters;
	private boolean mousePressed;
	private float startX;
	private float startY;
	private float lastX;
	private float lastY;
	private int duration;
	
	public TapDetector() {
		taps = new ArrayList<Tap>();
		filters = new ArrayList<IEventFilter<Tap>>();
		mousePressed = false;
		duration = 0;
	}
	
	public ArrayList<Tap> getTaps() {
		return taps;
	}
	
	public boolean hasTaps() {
		if(taps.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public void update
			(
					float mouseX,
					float mouseY,
					boolean mousePressed,
					int milis
			) {
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
			Tap t = new Tap
					(
							startX,
							startY,
							lastX,
							lastY,
							duration
					);
			boolean shouldAdd = true;
			for(IEventFilter<Tap> f : filters){
				if(f.filter(t)){
					shouldAdd = false;
					break;
				}
			}
			if(shouldAdd){
				taps.add(t);
			}
		}
	}

	public void flush() {
		taps = new ArrayList<Tap>();
	}
	
	public void addFilter(IEventFilter<Tap> filter) {
		filters.add(filter);
	}
}
