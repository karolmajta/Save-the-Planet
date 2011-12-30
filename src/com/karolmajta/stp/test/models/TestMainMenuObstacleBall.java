package com.karolmajta.stp.test.models;

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
}
