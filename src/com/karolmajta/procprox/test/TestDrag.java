package com.karolmajta.procprox.test;

import com.karolmajta.procprox.Drag;

import junit.framework.TestCase;

public class TestDrag extends TestCase {
	
	public void testGetVelocityX() {
		Drag d = new Drag
				(
					111.2f,
					128.1f,
					182.6f,
					-26.8f,
					101
				);
		float expected = (d.getStopX()-d.getStartX())/d.getDuration();
		float actual = d.getVelocityX();
		assertEquals(expected, actual);
	}
	
	public void testGetVelocityY() {
		Drag d = new Drag
				(
					111.2f,
					128.1f,
					182.6f,
					-26.8f,
					101
				);
		float expected = (d.getStopY()-d.getStartY())/d.getDuration();
		float actual = d.getVelocityY();
		assertEquals(expected, actual);
	}
}
