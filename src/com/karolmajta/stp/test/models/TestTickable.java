package com.karolmajta.stp.test.models;

import junit.framework.TestCase;

import com.karolmajta.stp.models.ITickable;
import com.karolmajta.stp.models.Tickable;
import com.karolmajta.stp.test.models.TestTickable.StubTickable;


public class TestTickable extends TestCase {
	class StubTickable extends Tickable {
		@Override
		public void onTick(long dt) {}
	}
	class Condition {
		public boolean satisfied;
		public Condition(boolean s){satisfied = s;}
	}
	
	public void testIsCreatedNotIgnoringTicks() {
		Tickable t = new StubTickable();
		assertFalse(t.ignoresTicks());
	}
	
	/**
	 * Tests weather ignoreTicks(boolean) will always affect the
	 * value returned by ignoresTicks()
	 */
	public void testIgnoreTicksAlwaysAffectsIgnoresTicks() {
		Tickable t1 = new StubTickable();
		Tickable t2 = new StubTickable();
		
		t1.ignoreTicks(true);
		t1.ignoreTicks(false);
		t1.ignoreTicks(true);
		t1.ignoreTicks(true);
		
		t2.ignoreTicks(false);
		t2.ignoreTicks(false);
		t2.ignoreTicks(true);
		t2.ignoreTicks(false);
		
		assertTrue(t1.ignoresTicks());
		assertFalse(t2.ignoresTicks());
	}

	public void testIgnoreTicksFalseDoesntAffectOnTick() {
		final Condition onTickCalled = new Condition(false);
		
		Tickable stub = new Tickable() {
			@Override
			public void onTick(long dt) {
				onTickCalled.satisfied = true;
			}
		};
		
		stub.tick(10);
		
		assertTrue(onTickCalled.satisfied);
	}

	public void testIgnoreTicksTrueDoesAffectOnTick() {
		final Condition onTickCalled = new Condition(false);
		
		Tickable stub = new Tickable() {
			@Override
			public void onTick(long dt) {
				onTickCalled.satisfied = true;
			}
		};
		
		stub.ignoreTicks(true);
		stub.tick(10);
		
		assertFalse(onTickCalled.satisfied);
	}

	public void testOnTickIsCalledWithSameArgumentsAsTick() {
		final Condition sameArguments = new Condition(false);
		
		final long expectedDt = 10;
		
		Tickable stub = new Tickable() {
			@Override
			public void onTick(long dt) {
				if(dt == expectedDt)
				sameArguments.satisfied = true;
			}
		};
		
		stub.tick(expectedDt);
		
		assertTrue(sameArguments.satisfied);
	}
}
