package com.karolmajta.stp;

import com.karolmajta.procprox.FontManager;
import com.karolmajta.stp.exception.FontNotLoadedException;

import android.os.Bundle;
import android.view.Display;
import processing.core.PApplet;

public class LoadingScreenActivity extends PApplet {
	private static final String[] AVAILABLE_FONTS = {
		"LiberationSansNarrow-Bold.ttf"
	};
	
	private int FONT_HUGE;
	private int FONT_BIG;
	private int FONT_NORMAL;
	private int FONT_SMALL;
	private int FONT_TINY;
	
	private FontManager fontManager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fontManager = FontManager.getFontManager();
    }
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
    @Override
    public void setup() {
    	
    	FONT_HUGE = height/5;
    	FONT_BIG = height/10;
    	FONT_NORMAL = height/20;
    	FONT_SMALL = height/40;
    	FONT_TINY = height/60;
    	
    	smooth();
    	
    	background(0xff000000);
    	
    	//progress bar dimensions
    	float margin = width/20;
    	float x0 = margin;
    	float x1 = width-margin;
    	float y0 = height/2;
    	float y1 = height/2;
    	// draw grey background for progressbar
    	stroke(0xffcccccc);
    	strokeWeight(width/50);
    	line(x0, y0, x1, y1);
    	// create fonts and update progressbar
    	stroke(0xffff0000);
    	strokeWeight(width/120);
    	
    	float dist = (x1-x0)/AVAILABLE_FONTS.length;
    	float currentX0 = x0;
    	float currentX1 = x0+dist;
    	
    	for(String fontName : AVAILABLE_FONTS){
    		fontManager.createFont(this, fontName, FONT_HUGE, true);
    		noOp(millis(), 1500);
    		line(currentX0, y0, currentX1, y1);
    		currentX0+=dist;
    		currentX0+=dist;
    	}
    }

    @Override
    public void draw() {
    	//
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
    
    private void noOp(long now, long timeout){
    	while(millis() < now + timeout) {} 
    }
}
