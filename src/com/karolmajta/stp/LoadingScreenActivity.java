package com.karolmajta.stp;

import com.karolmajta.procprox.FontManager;
import com.karolmajta.procprox.excepiton.FontNotCreatedException;
import com.karolmajta.stp.exception.NoTasksInProgressQueueException;
import com.karolmajta.stp.exception.UnboundViewException;
import com.karolmajta.stp.models.SyncProgress;
import com.karolmajta.stp.models.SyncTask;
import com.karolmajta.stp.views.ProgressView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class LoadingScreenActivity extends PApplet {
	private static final String[] AVAILABLE_FONTS = {
		"Sansation_Bold.ttf"
	};
	
	private static final String BAR_NAME = "Loading...";
	private static final int BGCOLOR = 0xff000000;
	private static final int BAR_BG = 0xffffffff;
	private static final int BAR_EMPTY = 0xffffffff;
	private static final int BAR_LOADED = 0xffff0000;
	
	private static final String GAME_LOGO = "logo.png";
	private static final String STUDIO_LOGO = "303games.png";
	
	private static float MARGIN_W;
	private static float MARGIN_H;
	private static float BAR_X;
	private static float BAR_Y;
	private static float BAR_WIDTH;
	private static float BAR_OUT;
	private static float BAR_IN;
	private static float LOGO_X;
	private static float LOGO_Y;
	private static float SLOGO_X;
	private static float SLOGO_Y;
	
	private static float FONT_SMALL;
	private static float FONT_MEDIUM;
	private static float FONT_BIG;
	
	private static float[] FONT_SIZES;
	
	private FontManager fontManager;
	
	private SyncProgress progress;
	
	private ProgressView progressView;
	private PImage gameLogo;
	private PImage studioLogo;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
    @Override
    public void setup() {
    	gameLogo = loadImage(GAME_LOGO);
    	studioLogo = loadImage(STUDIO_LOGO);
    	
    	MARGIN_W = width/10;
    	MARGIN_H = MARGIN_W;
    	LOGO_X = MARGIN_W;
    	LOGO_Y = MARGIN_H;
    	SLOGO_X = (width-studioLogo.width)/2;
    	SLOGO_Y = height-MARGIN_H-studioLogo.height;
    	BAR_X = 2*MARGIN_W;
    	BAR_Y = LOGO_Y+gameLogo.width+MARGIN_H;
    	BAR_WIDTH = width-4*MARGIN_W;
    	BAR_OUT = height/50;
    	BAR_IN = 0.5f*BAR_OUT;
    	
    	FONT_SMALL = height/40;
    	FONT_MEDIUM = FONT_SMALL*1.5f;
    	FONT_BIG = FONT_MEDIUM*1.5f;
    	FONT_SIZES = new float[] {
    			FONT_SMALL,
    			FONT_MEDIUM,
    			FONT_BIG
    	};
    	
    	FontManager.getFontManager()
			.createFont(this, "Sansation_Bold.ttf", (int)FONT_SMALL);
    	
    	progress = new SyncProgress();
    	for(final String fontName : AVAILABLE_FONTS){
    		for(final float size : FONT_SIZES){
	    		SyncTask t = new SyncTask() {
					@Override
					public void then() {}
					@Override
					public void doIt() {
						FontManager fm = FontManager.getFontManager();
						fm.createFont
								(
										LoadingScreenActivity.this,
										fontName,
										(int)size
								);
					}
				};
				progress.addTask(t, 1);
    		}
    	}
    	
    	PFont loadingBarPFont = null;
		try {
			loadingBarPFont = FontManager.getFontManager()
					.getFont("Sansation_Bold.ttf", (int)FONT_SMALL);
		} catch (FontNotCreatedException e) {
			loadingBarPFont = g.textFont;
		}
    	
    	progressView = new ProgressView
    			(
    					BAR_NAME,
    					loadingBarPFont,
    					BAR_X,
    					BAR_Y,
    					BAR_WIDTH,
    					BAR_OUT,
    					BAR_IN,
    					BAR_BG,
    					BAR_EMPTY,
    					BAR_LOADED
    			);
    	progressView.bindModel(progress);
    	
    	smooth();
    	background(BGCOLOR);
    }

    @Override
    public void draw() {
    	background(BGCOLOR);
    	
    	image(gameLogo, LOGO_X, LOGO_Y);
    	image(studioLogo, SLOGO_X, SLOGO_Y);
    	
    	try {
			progressView.draw(this);
		} catch (UnboundViewException e) {
			e.printStackTrace();
		}
    	
    	if(progress.hasNextTask()){
    		try {
				progress.doNext();
			} catch (NoTasksInProgressQueueException e) {
				e.printStackTrace();
			}
    	}
    	
    	if(mousePressed && !progressView.isVisible()){
    		Intent i = new Intent(this, MainMenuActivity.class);
    		startActivity(i);
    		exit();
    		finish();
    	}
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
}
