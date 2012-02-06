package com.karolmajta.stp;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.karolmajta.procprox.Drag;
import com.karolmajta.procprox.DragDetector;
import com.karolmajta.procprox.FontManager;
import com.karolmajta.procprox.IEventFilter;
import com.karolmajta.procprox.Tap;
import com.karolmajta.procprox.TapDetector;
import com.karolmajta.procprox.excepiton.FontNotCreatedException;
import com.karolmajta.stp.exception.NoDeferredException;
import com.karolmajta.stp.exception.UnboundViewException;
import com.karolmajta.stp.models.MainMenuItemBall;
import com.karolmajta.stp.models.MainMenuObstacleBall;
import com.karolmajta.stp.models.ObstacleManager;
import com.karolmajta.stp.views.FancyTextView;
import com.karolmajta.stp.views.MainMenuItemBallView;
import com.karolmajta.stp.views.ObstacleManagerView;

public class MainMenuActivity extends PApplet {
	
	private static final int QUIT_DIALOG = 0;
	
	private static final float BG = 0xff000000;
	
	private static float BALL_BASE_X;
	private static float BALL_BASE_Y;
	private static float BALL_RADIUS;
	private static final float BALL_SPRING = 0.015f;
	private static final float BALL_DAMPING = 3.5f;
	private static final float BALL_MASS = 500;
	
	private Integer lastTick = null;
	private Integer deltaTime = null;
	
	private DragDetector leftDetector;
	private DragDetector rightDetector;
	private TapDetector tapDetector;
	
	private ArrayList<MainMenuItemBall> menuBalls;
	private ArrayList<MainMenuItemBallView> ballViews;
	
	private ObstacleManager om;
	private ObstacleManagerView ov;
	
	private FancyTextView swipeToScrollTextView;
	
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
	public Dialog onCreateDialog(int id) {
		switch(id){
			/*
			case QUIT_DIALOG:
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to exit?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                MainMenuActivity.this.finish();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				return alert;
			*/
			case QUIT_DIALOG:
				LayoutInflater inflater=LayoutInflater.from(this);
				int quitDialogLayoutId = com.karolmajta.stp.R.layout.quit_dialog_layout;
				int tvId = com.karolmajta.stp.R.id.text;
			    View addView=inflater.inflate(quitDialogLayoutId, null);
			    TextView tv = (TextView)addView.findViewById(tvId);
			    Typeface face = Typeface.createFromAsset(getAssets(), "Sansation_Bold.ttf");
			    tv.setTypeface(face);
			    
			    new AlertDialog.Builder(this)
			    	.setView(addView)
			    	.show();
			    
			default:
				return null;
		}
	}
	
    @Override
    public void setup() {
    	BALL_BASE_X = width/2;
    	BALL_BASE_Y = height/2;
    	BALL_RADIUS = 0.5f*width*0.65f;
    	
    	leftDetector = new DragDetector();
    	leftDetector.addFilter
    			(
    					new IEventFilter<Drag>() {
							@Override
							public boolean filter(Drag e) {
								// only left
								if(e.getVelocityX() > 0){
									return true;
								}
								// only fast enough
								if(Math.abs(e.getVelocityX()) < 0.5) {
									return true;
								}
								// only horizontal
								if(Math.abs(e.getVelocityX()) < Math.abs(e.getVelocityY())){
									return true;
								}
								// only long distance (more than 1/10 screen width)
								if(Math.abs(e.getStartX()-e.getStopX()) < width/10){
									return true;
								}
								return false;
							}
    					}
    			);
    	rightDetector = new DragDetector();
    	rightDetector.addFilter
    			(
    					new IEventFilter<Drag>() {
							@Override
							public boolean filter(Drag e) {
								// only right
								if(e.getVelocityX() < 0){
									return true;
								}
								// only fast enough
								if(Math.abs(e.getVelocityX()) < 0.5) {
									return true;
								}
								// only horizontal
								if(Math.abs(e.getVelocityX()) < Math.abs(e.getVelocityY())){
									return true;
								}
								// only long distance (more than 1/10 screen width)
								if(Math.abs(e.getStartX()-e.getStopX()) < width/10){
									return true;
								}
								return false;
							}
    					}
    			);
    	tapDetector = new TapDetector();
    	tapDetector.addFilter
    			(
    					new IEventFilter<Tap>() {
							@Override
							public boolean filter(Tap e) {
								// only short distance X (less than 1/10 screen width)
								if(Math.abs(e.getStartX()-e.getStopX()) > width/10){
									return true;
								}
								// only short distance Y (less than 1/10 screen width)
								if(Math.abs(e.getStartY()-e.getStopY()) > width/10){
									return true;
								}
								// only short duration (less than 500 milliseconds)
								if(e.getDuration() > 500){
									return true;
								}
								return false;
							}
    						
    					}
    			);
    	
    	menuBalls = new ArrayList<MainMenuItemBall>();
    	menuBalls.add
    			(
		    			new MainMenuItemBall
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
		    					)
    			);
    	menuBalls.add
    			(
    					new MainMenuItemBall
				    			(
				    					"Instructions",
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
				    			)
    			);
    	menuBalls.add
		(
				new MainMenuItemBall
		    			(
		    					"Options",
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
		    			)
		);
    	menuBalls.add
		(
				new MainMenuItemBall
		    			(
		    					"Donate",
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
		    			)
		);
    	menuBalls.add
		(
				new MainMenuItemBall
		    			(
		    					"Credits",
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
		    			)
		);
    	menuBalls.add
		(
				new MainMenuItemBall
		    			(
		    					"Quit",
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
		    			)
		);
    	menuBalls.get(5).provideDeferred
    			(
    					new MainMenuItemBall.Deferred() {
							@Override
							public void call() {
								runOnUiThread
										(
												new Runnable() {
													@Override
													public void run() {
														showDialog(QUIT_DIALOG);
													}
												}
										);
							}
    					}
    			);
    	for(int i = 0; i < menuBalls.size(); i++){
    		MainMenuItemBall b = menuBalls.get(i);
    		b.setCurrentX(b.getCurrentX()+i*width);
    		b.setX0(b.getX0()+i*width);
    	}
    	
    	ballViews = new ArrayList<MainMenuItemBallView>();
    	ballViews.add(new MainMenuItemBallView(0xffcc0099, 0xffffffff));
    	ballViews.get(0).bindModel(menuBalls.get(0));
    	ballViews.add(new MainMenuItemBallView(0xff9966ff, 0xffffffff));
    	ballViews.get(1).bindModel(menuBalls.get(1));
    	ballViews.add(new MainMenuItemBallView(0xff009933, 0xffffffff));
    	ballViews.get(2).bindModel(menuBalls.get(2));
    	ballViews.add(new MainMenuItemBallView(0xffcc3300, 0xffffffff));
    	ballViews.get(3).bindModel(menuBalls.get(3));
    	ballViews.add(new MainMenuItemBallView(0xff3366cc, 0xffffffff));
    	ballViews.get(4).bindModel(menuBalls.get(4));
    	ballViews.add(new MainMenuItemBallView(0xffcc9900, 0xffffffff));
    	ballViews.get(5).bindModel(menuBalls.get(5));
    	
    	om = new ObstacleManager(0, 0, width, height);
    	ov = new ObstacleManagerView();
    	ov.bindModel(om);
    	
    	PFont font = null;
    	try {
			font = FontManager.getFontManager()
					.getFont("Sansation_Bold.ttf", (int)LoadingScreenActivity.FONT_SMALL);
		} catch (FontNotCreatedException e) {
			font = g.textFont;
		}
		swipeToScrollTextView = new FancyTextView
				(
						width/2,
						height-height/10,
						font,
						0xffffffff,
						FancyTextView.SHOW
				);
	
		swipeToScrollTextView.bindModel("Swipe to navigate\nTap to select");
    	
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
    	leftDetector.update(mouseX, mouseY, mousePressed, deltaTime);
    	rightDetector.update(mouseX, mouseY, mousePressed, deltaTime);
    	tapDetector.update(mouseX,  mouseY, mousePressed, deltaTime);
    	
    	if(tapDetector.hasTaps()){
    		Tap t = tapDetector.getTaps().get(0);
    		tapDetector.flush();
	    	for(MainMenuItemBall b : menuBalls){
	    			try {
						b.consumeTap(t);
					} catch (NoDeferredException e) {
						Log.d("karolmajta.stp", "No deferred for " + b.getLabel());
					}
	    	}
    	}
    	
    	if(leftDetector.hasDrags()){
    		leftDetector.flush();
    		if(menuBalls.get(menuBalls.size()-1).getX0() == width/2){
    			menuBalls.get(menuBalls.size()-1).setCurrentVX(-0.5f);
    		}else{
	    		for(MainMenuItemBall b : menuBalls){
	    			b.setX0(b.getX0()-width);
	    		}
    		}
    	}
    	
    	if(rightDetector.hasDrags()){
    		rightDetector.flush();
    		if(menuBalls.get(0).getX0() == width/2){
    			menuBalls.get(0).setCurrentVX(0.5f);
    		}else{
	    		for(MainMenuItemBall b : menuBalls){
	    			b.setX0(b.getX0()+width);
	    		}
    		}
    	}
    	
    	for(MainMenuItemBall b : menuBalls){
    		for(MainMenuObstacleBall ob : om.getObstacles()){
	    		if(ob.affectedBy(b)){
	        		ob.collide(b);
	        	}
	        	if(b.affectedBy(ob)){
	        		b.collide(ob);
	        	}
    		}	
    	}
    	
    	for(MainMenuItemBall b : menuBalls){
    		b.tick(deltaTime);
    	}
    	for(MainMenuObstacleBall ob : om.getObstacles()){
    		ob.tick(deltaTime);
    	}
    	om.tick(deltaTime);
    	om.cleanup();
    	
    	// view part
    	background(BG);
    	
    	try {
    		for(MainMenuItemBallView bv : ballViews){
    			bv.draw(this);
    		}
    		ov.draw(this);
    		swipeToScrollTextView.draw(this);
		} catch (UnboundViewException e) {
			e.printStackTrace();
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