package com.karolmajta.stp.test.models;

import com.karolmajta.stp.models.MainMenuItemBall;
import com.karolmajta.stp.models.MainMenuObstacleBall;

import junit.framework.TestCase;

public class TestMainMenuObstacleBall extends TestCase {
	public static final float FLOAT_EPSILON = 1e-6f;
	public static final float X0 = 231.2f;
	public static final float Y0 = -28.1f;
	public static final float R = 50.0f;
	public static final float VX0 = 1.28f;
	public static final float VY0 = 0.02f;
	public static final long DT = 21;
	
	public void testIntegrationOnTick() {
		MainMenuObstacleBall ball = new MainMenuObstacleBall(X0, Y0, R, VX0, VY0);
		
		float x1 = X0+VX0*DT;
		float y1 = Y0+VY0*DT;
		
		ball.tick(DT);
		
		assertEquals(x1, ball.getX());
		assertEquals(y1, ball.getY());
	}
	
	public void testVelocityWontChangeOnIntegration() {
		MainMenuObstacleBall ball = new MainMenuObstacleBall(X0, Y0, R, VX0, VY0);
		
		ball.tick(DT);
		
		assertEquals(VX0, ball.getVX());
		assertEquals(VY0, ball.getVY());
	}
	
	public void testAffectedByWithMainMenuItemBallFalseIfDontOverlap() {
		float x0 = 0.0f;
		float y0 = 0.0f;
		float r0 = 10.0f;
		
		float x1 = 0.0f;
		float y1 = 20.0f;
		float r1 = 8.0f;
		
		MainMenuObstacleBall b0 = new MainMenuObstacleBall(x0, y0, r0, 0, 0);
		MainMenuItemBall b1 = new MainMenuItemBall
				(
						x1, y1, r1, 0, 0, 0
				);
		
		assertFalse(b0.affectedBy(b1));
	}
	
	public void testAffectedByWithMainMenuItemBallTrueIfOverlap() {
		float x0 = 0.0f;
		float y0 = 0.0f;
		float r0 = 10.0f;
		
		float x1 = 0.0f;
		float y1 = 20.0f;
		float r1 = 12.0f;
		
		MainMenuObstacleBall b0 = new MainMenuObstacleBall(x0, y0, r0, 0, 0);
		MainMenuItemBall b1 = new MainMenuItemBall
				(
						x1, y1, r1, 0, 0, 0
				);
		
		assertTrue(b0.affectedBy(b1));
	}
	
	/*
	 * This test is not very pretty which means there is better way to
	 * implement this.
	 */
	public void testWontBeAffectedAgainUntilCollisionAreaIsLeft() {
		float x0 = 0.0f;
		float y0 = 0.0f;
		float r0 = 10.0f;
		
		float xc = 0.0f;
		float yc = 20.0f;
		float rc = 12.0f;
		
		float xnc = 0.0f;
		float ync = 20.0f;
		float rnc = 8.0f;
		
		MainMenuItemBall item = new MainMenuItemBall(xc, yc, rc, 0, 0, 0);
		
		MainMenuObstacleBall b0 = new MainMenuObstacleBall(x0, y0, r0, 0, 0);
		
		assertTrue(b0.affectedBy(item));
		assertFalse(b0.affectedBy(item));
		assertFalse(b0.affectedBy(item));
		
		item.setCurrentX(xnc);
		item.setCurrentY(ync);
		item.setRadius(rnc);
		
		assertFalse(b0.affectedBy(item));
		assertFalse(b0.affectedBy(item));
		
		item.setCurrentX(xc);
		item.setCurrentY(yc);
		item.setRadius(rc);
		
		assertTrue(b0.affectedBy(item));
		assertFalse(b0.affectedBy(item));
	}
}
