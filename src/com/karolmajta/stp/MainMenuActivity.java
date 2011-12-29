package com.karolmajta.stp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;

import processing.core.PApplet;

public class MainMenuActivity extends PApplet {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void setup() {
    	// TODO
    }

    @Override
    public void draw() {
    	// TODO
    }
    @Override
    public int sketchWidth() {
    	Display display = getWindowManager().getDefaultDisplay(); 
    	return display.getWidth();
    }
    @Override
    public int sketchHeight() {
    	Display display = getWindowManager().getDefaultDisplay(); 
    	return display.getHeight();
    }
    @Override
    public String sketchRenderer() {
        return P2D;
    }
    @Override
    public void mousePressed() {}
    @Override
    public void mouseReleased() {}
}