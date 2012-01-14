package com.karolmajta.procprox.test;

import com.karolmajta.procprox.Drag;
import com.karolmajta.procprox.DragDetector;
import com.karolmajta.procprox.IEventFilter;

import junit.framework.TestCase;

public class TestDragDetector extends TestCase {
	
	public void testIsCreatedWithNoDragsDetected() {
		DragDetector d = new DragDetector();
		assertFalse(d.hasDrags());
		assertEquals(0, d.getDrags().size());
	}
	
	public void testCreatesDragCorrectly() {
		DragDetector d = new DragDetector();
		d.update(0, 0, true, 10);
		d.update(0, 10, true, 10);
		d.update(0, 10, false, 10);
		assertTrue(d.hasDrags());
		assertEquals(1, d.getDrags().size());
		Drag d1 = d.getDrags().get(0);
		assertEquals(0.0f, d1.getStartX());
		assertEquals(0.0f, d1.getStartY());
		assertEquals(0.0f, d1.getStopX());
		assertEquals(10.0f, d1.getStopY());
	}

	public void testHitsAllFiltersThatWereAdded() {
		IEventFilter<Drag> filter1 = new IEventFilter<Drag>() {
			@Override
			public boolean filter(Drag e) {
				if(e.getDuration() > 100) {
					return false;
				}else{
					return true;
				}
			}
		};
		
		IEventFilter<Drag> filter2 = new IEventFilter<Drag>() {
			@Override
			public boolean filter(Drag e) {
				if(e.getStartX() > 0) {
					return false;
				}else{
					return true;
				}
			}
		};
		
		DragDetector d = new DragDetector();
		d.addFilter(filter1);
		d.addFilter(filter2);
		
		d.update(10.0f, 0.0f, true, 1000);
		d.update(10.0f, 0.0f, false, 200);
		assertEquals(1, d.getDrags().size());
		
		d.update(-10.0f, 0.0f, true, 50);
		d.update(15.0f, 0.0f, false, 20);
		assertEquals(1, d.getDrags().size());
		
		d.update(10.0f, 0.0f, true, 50);
		d.update(15.0f, 0.0f, false, 200);
		assertEquals(1, d.getDrags().size());
	}
}
