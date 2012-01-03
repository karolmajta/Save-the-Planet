package com.karolmajta.stp.test.models;

import java.util.List;

import com.karolmajta.stp.models.MainMenuItemBall;
import com.karolmajta.stp.models.MenuBallManager;

import junit.framework.TestCase;

public class TestMenuBallManager extends TestCase {
	private static final int NUM_BALLS = 10;
	private MainMenuItemBall[] stubBalls;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		stubBalls = new MainMenuItemBall[NUM_BALLS];
		for(int i = 0; i < NUM_BALLS; i++){
			stubBalls[i] = new MainMenuItemBall(0,0,0,0,0,0);
		}
	}
	
	public void testContainsListOfMenuItemBalls() {
		MenuBallManager man = new MenuBallManager();
		List<MainMenuItemBall> l = man.getItems();
		
		assertNotNull(l);
	}
	
	public void testAllowsAddingItems() {
		MenuBallManager man = new MenuBallManager();
		for(MainMenuItemBall b : stubBalls){
			man.add(b);
		}
	}
	
	public void testBehavesAsCyclicRegisterForPositiveOffsets() {
		MenuBallManager man = new MenuBallManager();
		for(int i = 0; i < 5; i++){
			man.add(stubBalls[i]);
		}
		assertEquals(stubBalls[0], man.get(0));
		assertEquals(stubBalls[2], man.get(2));
		assertEquals(stubBalls[0], man.get(5));
		assertEquals(stubBalls[4], man.get(14));
	}
	
	public void testBehavesAsCyclicRegisterForNegativeOffsets() {
		MenuBallManager man = new MenuBallManager();
		for(int i = 0; i < 5; i++){
			man.add(stubBalls[i]);
		}
		assertEquals(stubBalls[0], man.get(0));
		assertEquals(stubBalls[4], man.get(-1));
		assertEquals(stubBalls[3], man.get(-2));
		assertEquals(stubBalls[2], man.get(-3));
		assertEquals(stubBalls[1], man.get(-4));
	}
	
	public void testBehavesAsCyclicRegisterForNegativeOffsets2() {
		MenuBallManager man = new MenuBallManager();
		for(int i = 0; i < 5; i++){
			man.add(stubBalls[i]);
		}
		assertEquals(stubBalls[0], man.get(-5));
		assertEquals(stubBalls[4], man.get(-6));
		assertEquals(stubBalls[3], man.get(-7));
		assertEquals(stubBalls[2], man.get(-8));
		assertEquals(stubBalls[1], man.get(-9));
		assertEquals(stubBalls[0], man.get(-15));
		assertEquals(stubBalls[4], man.get(-16));
		assertEquals(stubBalls[3], man.get(-17));
		assertEquals(stubBalls[2], man.get(-18));
		assertEquals(stubBalls[1], man.get(-19));
	}
	
	public void testBehavesAsCyclicRegisterWith1Element() {
		MenuBallManager man = new MenuBallManager();
		man.add(stubBalls[0]);
		assertEquals(stubBalls[0], man.get(0));
		assertEquals(stubBalls[0], man.get(1));
		assertEquals(stubBalls[0], man.get(5));
		assertEquals(stubBalls[0], man.get(-1));
		assertEquals(stubBalls[0], man.get(-17));
	}

	public void testShiftRight() {
		MenuBallManager man = new MenuBallManager();
		for(int i = 0; i < 5; i++){
			man.add(stubBalls[i]);
		}
		man.shiftRight();
		assertEquals(stubBalls[4], man.get(0));
		assertEquals(stubBalls[0], man.get(1));
		assertEquals(stubBalls[1], man.get(2));
		assertEquals(stubBalls[2], man.get(3));
		assertEquals(stubBalls[3], man.get(4));
	}
	
	public void testShiftLeft() {
		MenuBallManager man = new MenuBallManager();
		for(int i = 0; i < 5; i++){
			man.add(stubBalls[i]);
		}
		man.shiftLeft();
		assertEquals(stubBalls[1], man.get(0));
		assertEquals(stubBalls[2], man.get(1));
		assertEquals(stubBalls[3], man.get(2));
		assertEquals(stubBalls[4], man.get(3));
		assertEquals(stubBalls[0], man.get(4));
	}
}
