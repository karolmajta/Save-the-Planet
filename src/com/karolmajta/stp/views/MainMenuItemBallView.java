package com.karolmajta.stp.views;

import java.util.Date;

import android.util.Log;

import com.karolmajta.procprox.FontManager;
import com.karolmajta.procprox.excepiton.FontNotCreatedException;
import com.karolmajta.stp.LoadingScreenActivity;
import com.karolmajta.stp.models.MainMenuItemBall;

import processing.core.PApplet;
import processing.core.PFont;

public class MainMenuItemBallView extends View<MainMenuItemBall> {
	public static final int STROKE = 0xffff0000;
	
	private int color;
	private int textColor;
	
	public MainMenuItemBallView(int color, int textColor) {
		this.color = color;
		this.textColor = textColor;
	}
	
	@Override
	protected void onDraw(PApplet p) {
		int prevFill = p.g.fillColor;
		int prevStroke = p.g.strokeColor;
		int prevTextAlign = p.g.textAlign;
		PFont prevPFont = p.g.textFont;

		p.noStroke();
		
		p.fill(color);
		// ball
		p.ellipse(
				model.getCurrentX(),
				model.getCurrentY(),
				model.getRadius()*2,
				model.getRadius()*2
		);
		p.fill(prevFill);
		
		// text
		p.fill(textColor);
		PFont pFont = null;
		try {
			pFont = FontManager.getFontManager().getFont
					(
							LoadingScreenActivity.AVAILABLE_FONTS[0],
							(int) LoadingScreenActivity.FONT_MEDIUM
					);
			p.textFont(pFont);
		} catch (FontNotCreatedException e) {
			// just use default font
		}
	
		p.textFont(pFont);
			
	
		p.textAlign(p.CENTER);
		p.text
			(
					model.getLabel(),
					model.getCurrentX(),
					model.getCurrentY()+LoadingScreenActivity.FONT_MEDIUM/2
			);
		
		p.stroke(prevStroke);
		p.fill(prevFill);
		p.textAlign(prevTextAlign);
		if(prevPFont != null){
			p.textFont(prevPFont);
		}
	}
}
