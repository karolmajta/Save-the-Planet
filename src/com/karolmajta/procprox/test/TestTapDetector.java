package com.karolmajta.procprox.test;

import com.karolmajta.procprox.Tap;
import com.karolmajta.procprox.TapDetector;
import com.karolmajta.procprox.IEventFilter;

import junit.framework.TestCase;

public class TestTapDetector extends TestCase {
	
	public void testIsCreatedWithNoDragsDetected() {
		TapDetector d = new TapDetector();
		assertFalse(d.hasTaps());
		assertEquals(0, d.getTaps().size());
	}
	
	public void testCreatesDragCorrectly() {
		TapDetector d = new TapDetector();
		d.update(0, 0, true, 10);
		d.update(0, 10, true, 10);
		d.update(0, 10, false, 10);
		assertTrue(d.hasTaps());
		assertEquals(1, d.getTaps().size());
		Tap t1 = d.getTaps().get(0);
		assertEquals(0.0f, t1.getStartX());
		assertEquals(0.0f, t1.getStartY());
		assertEquals(0.0f, t1.getStopX());
		assertEquals(10.0f, t1.getStopY());
	}

	public void testHitsAllFiltersThatWereAdded() {
		IEventFilter<Tap> filter1 = new IEventFilter<Tap>() {
			@Override
			public boolean filter(Tap e) {
				if(e.getDuration() < 200) {
					return false;
				}else{
					return true;
				}
			}
		};
		
		TapDetector d = new TapDetector();
		d.addFilter(filter1);
		
		d.update(10.0f, 0.0f, true, 20);
		d.update(10.0f, 0.0f, false, 50);
		assertEquals(1, d.getTaps().size());
		
		d.update(-10.0f, 0.0f, true, 500);
		d.update(15.0f, 0.0f, false, 200);
		assertEquals(1, d.getTaps().size());
	}
}
