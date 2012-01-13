package com.karolmajta.stp;

import java.util.ArrayList;

import com.karolmajta.stp.exception.UnboundViewException;
import com.karolmajta.stp.models.MainMenuItemBall;
import com.karolmajta.stp.views.MainMenuItemBallView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import processing.core.PApplet;

public class MainMenuActivity extends PApplet {
	private static final float BG = 0xff000000;
	
	private static float BALL_BASE_X;
	private static float BALL_BASE_Y;
	private static float BALL_RADIUS;
	private static final float BALL_SPRING = 0.015f;
	private static final float BALL_DAMPING = 3.5f;
	private static final float BALL_MASS = 500;
	
	private Integer lastTick = null;
	private Integer deltaTime = null;
	
	private MainMenuItemBall ball1;
	private MainMenuItemBall ball2;
	
	private MainMenuItemBallView ballView1;
	private MainMenuItemBallView ballView2;
	
	private boolean move = true;
	private boolean left = true;
	private boolean right = false;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		lastTick = null;
		deltaTime = null;
		loop();
	}
	
	@Override
	public void onPause() {
		noLoop();
		super.onPause();
	}
	
    @Override
    public void setup() {
    	BALL_BASE_X = width/2;
    	BALL_BASE_Y = height/2;
    	BALL_RADIUS = 0.5f*width*0.65f;
    	
    	ball1 = new MainMenuItemBall
    			(
    					"Play!",
    					BALL_BASE_X,
    					BALL_BASE_Y,
    					BALL_RADIUS,
    					BALL_BASE_X,
    					BALL_BASE_Y,
    					0,
    					0,
    					BALL_SPRING,
    					BALL_DAMPING,
    					BALL_MASS
    			);
    	ball2 = new MainMenuItemBall
    			(
    					"Quit",
    					BALL_BASE_X+width,
    					BALL_BASE_Y,
    					BALL_RADIUS,
    					BALL_BASE_X+width,
    					BALL_BASE_Y,
    					0,
    					0,
    					BALL_SPRING,
    					BALL_DAMPING,
    					BALL_MASS
    			);
    	
    	ballView1 = new MainMenuItemBallView(0xffcc0099, 0xffffffff);
    	ballView1.bindModel(ball1);
    	ballView2 = new MainMenuItemBallView(0xff9966ff, 0xffffffff);
    	ballView2.bindModel(ball2);
    	
    	smooth();
    	background(BG);
    }

    @Override
    public void draw() {
    	// time tracking part
    	
    	if(lastTick == null){
    		lastTick = millis();
    	}
    	int newTick = millis();
    	deltaTime = newTick - lastTick;
    	lastTick = newTick;
    	
    	// model part
    	
    	ball1.tick(deltaTime);
    	ball2.tick(deltaTime);
    	
    	// view part
    	background(BG);
    	
    	try {
			ballView1.draw(this);
			ballView2.draw(this);
		} catch (UnboundViewException e) {
			e.printStackTrace();
		}
    	
    	if(mousePressed){
    		if(left && move){
	    		ball2.setX0(width/2);
	    		ball2.setY0(height/2);
	    		ball1.setX0(ball1.getX0()-width);
	    		move = false;
	    		right = true;
	    		left = false;
    		}
    		if(right && move){
    			ball1.setX0(width/2);
    			ball1.setY0(height/2);
    			ball2.setX0(ball2.getX0()+width);
    			move = false;
    			left = true;
    			right = false;
    		}
    		
    	}else{
    		move = true;
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
    @Override
    public void mousePressed() {}
    @Override
    public void mouseReleased() {}
}