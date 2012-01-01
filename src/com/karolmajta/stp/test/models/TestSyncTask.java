package com.karolmajta.stp.test.models;

import com.karolmajta.stp.models.SyncTask;

import junit.framework.TestCase;

public class TestSyncTask extends TestCase {
	public void testDoItAndThenAreLaunchedOneAfterAnotherSynchronously() {
		class Condition {
			private int flag = 1;
			public void doIt() {flag += 2;}
			public void then() {flag *= 0;}
			public boolean satisfied() {
				return flag == 0 ? true : false;
			}
		}
		
		final Condition c = new Condition();
		
		SyncTask t = new SyncTask() {
			@Override
			public void doIt() {
				c.doIt();
			}
			@Override
			public void then() {
				c.then();
			}
		};
		t.launch();
		
		assertTrue(c.satisfied());
	}
}
