package com.karolmajta.procprox.test;

import com.karolmajta.procprox.FontManager;
import com.karolmajta.procprox.excepiton.FontNotCreatedException;

import junit.framework.TestCase;

public class TestFontManager extends TestCase {
	
	public void testIsSingleton() {
		FontManager fm1 = FontManager.getFontManager();
		FontManager fm2 = FontManager.getFontManager();
		
		assertEquals(fm1, fm2);
	}
	
	public void testWillThrowIfTryingToGetNonCreatedFont() {
		FontManager fm = FontManager.getFontManager();
		boolean gotError = false;
		try{
			fm.getFont("some font", 20);
		}catch(FontNotCreatedException e){
			gotError = true;
		}
		assertTrue(gotError);
	}
	
	////////////////////////////////////////////////////////
	// THIS CLASS DEFINATELY NEEDS MORE TESTING BUT IT SHOULD
	// BE TESTED WITH ANDROID UNIT TEST USING ACTIVITY
	// INSTRUMENTATION TEST 2 AS AN INSTANCE OF PAPPLET(ACTIVITY
	// NEEDS TO BE PASSED TO createFont
	// FOR NOW THIS MUST SUFFICE
	////////////////////////////////////////////////////////
}
