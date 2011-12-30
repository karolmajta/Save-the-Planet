package com.karolmajta.stp.test.views;

import processing.core.PApplet;

import com.karolmajta.stp.exception.UnboundViewException;
import com.karolmajta.stp.views.View;

import junit.framework.TestCase;

public class TestView extends TestCase {
	class Condition {
		public boolean satisfied;
		public Condition(boolean s) {satisfied = s;}
	}
	
	public void testIsCreatedVisible() {
		View stubView = new View() {
			@Override
			protected void onDraw(PApplet p) {}
		};
		
		assertTrue(stubView.isVisible());
	}
	
	public void testViewWillRaiseExceptionIfDrawCalledWithNoBoundModel() {
		View stubView = new View() {
			@Override
			protected void onDraw(PApplet p) {}
		};
		
		boolean gotException = false;
		try{
			stubView.draw(null);
		}catch(UnboundViewException e){
			gotException = true;
		}
		
		assertTrue(gotException);
	}
	
	public void testSettingVisibleTrueDoesntAffectOnDraw()
			throws UnboundViewException {
		
		final Condition called = new Condition(false);
		
		View stubView = new View() {
			@Override
			protected void onDraw(PApplet p) {called.satisfied = true;}
		};
		
		stubView.bindModel(new Object());
		stubView.setVisible(true);
		stubView.draw(null);
		
		assertTrue(called.satisfied);
	}
	
	public void testSettingVisibleFalseBlocksCallsToOnDraw()
			throws UnboundViewException {
		
		final Condition notCalled = new Condition(true);
		
		View stubView = new View() {
			@Override
			protected void onDraw(PApplet p) {notCalled.satisfied = false;}
		};
		
		stubView.bindModel(new Object());
		stubView.setVisible(false);
		stubView.draw(null);
		
		assertTrue(notCalled.satisfied);
	}
	
	public void testModelIsAccessibleViaProtectedModelField() {
		final Object o = new Object();
		View stubView = new View() {
			@Override
			protected void onDraw(PApplet p) {
				assertEquals(o, model);	
			}
		};
	}
	
	// there should be one more test here. It should test if onDraw is called
	// with same PApplet instance as draw, but I have no idea how to mock out
	// PApplet as no interface for it is provided (just concrete class), and
	// extending it is not an option (can't instantiate it in jUnit without
	// android libraries)
}
