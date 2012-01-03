package com.karolmajta.procprox;

import java.util.HashMap;

import com.karolmajta.procprox.excepiton.FontNotCreatedException;

import processing.core.PApplet;
import processing.core.PFont;

public class FontManager {
	private static FontManager fontManager = null;
	
	private HashMap<String, HashMap<Integer, PFont>> fonts =
			new HashMap<String, HashMap<Integer, PFont>>();
	
	public static FontManager getFontManager() {
		if(fontManager == null){
			fontManager = new FontManager();
		}
		return fontManager;
	}
	
	private FontManager() {}
	
	public void createFont(PApplet p, String name, int size) { 
		if(fonts.get(name) == null){
			fonts.put(name, new HashMap<Integer, PFont>());
		}
		if(fonts.get(name).get(size) == null){
			PFont f = p.createFont(name, size);
			fonts.get(name).put(size, f);
		}
	}

	public PFont getFont(String name, int size)
			throws FontNotCreatedException {
		
		if(fonts.get(name) == null){
			throw new FontNotCreatedException(name, size);
		}
		if(fonts.get(name).get(size) == null){
			throw new FontNotCreatedException(name, size);
		}
		
		return fonts.get(name).get(size);
	}
}
