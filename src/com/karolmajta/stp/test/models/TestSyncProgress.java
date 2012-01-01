package com.karolmajta.stp.test.models;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.karolmajta.stp.exception.NoTasksInProgressQueueException;
import com.karolmajta.stp.models.ITask;
import com.karolmajta.stp.models.SyncProgress;
import com.karolmajta.stp.models.SyncTask;

import junit.framework.TestCase;

public class TestSyncProgress extends TestCase {
	public void testSyncProgressGetsCreatedEmpty() {
		SyncProgress p = new SyncProgress();
		assertFalse(p.hasNextTask());
	}
	
	public void testAddTaskChangesHasNextBehaviour() {
		SyncTask stubTask = new SyncTask() {
			@Override
			public void doIt() {}
			@Override
			public void then() {}
		};
		
		SyncProgress p = new SyncProgress();
		p.addTask(stubTask, 1);
		
		assertTrue(p.hasNextTask());
	}
	
	public void testAddTaskChangesGetTotal() {
		SyncTask stubTask = new SyncTask() {
			@Override
			public void doIt() {}
			@Override
			public void then() {}
		};
		
		SyncProgress p = new SyncProgress();
		p.addTask(stubTask, 2);
		p.addTask(stubTask, 3);
		
		assertEquals(5, p.getTotal());
	}
	
	public void testDoNextHitsITasksLaunch()
			throws NoTasksInProgressQueueException {
		
		Mockery context = new Mockery();
		ITask mockTask = context.mock(ITask.class);
		
		SyncProgress p = new SyncProgress();
		p.addTask(mockTask, 1);
		
		Expectations expect = new Expectations();
		expect.oneOf(mockTask).launch();
		context.checking(expect);
		
		p.doNext();
		
		context.assertIsSatisfied();
	}
	
	public void testDoNextWillRemoveTaskFromProgressQueue()
			throws NoTasksInProgressQueueException {
		
		ITask stubTask = new ITask() {
			@Override
			public void launch() {}
			@Override
			public void doIt() {}
			@Override
			public void then() {}
		};
		
		SyncProgress p = new SyncProgress();
		p.addTask(stubTask, 1);
		p.doNext();
		
		assertFalse(p.hasNextTask());
	}

	public void testDoNextWillThrowIfProgressQueueIsEmpty()
			throws NoTasksInProgressQueueException {
		
		ITask stubTask = new ITask() {
			@Override
			public void launch() {}
			@Override
			public void doIt() {}
			@Override
			public void then() {}
		};
		
		SyncProgress p = new SyncProgress();
		p.addTask(stubTask, 1);
		p.doNext();
		boolean gotException = false;
		try{
			p.doNext();
		}catch(NoTasksInProgressQueueException e){
			gotException = true;
		}
		assertTrue(gotException);
	}

	public void testMarkAsProcessedWillAffectGetProcessed() {
		ITask stubTask = new ITask() {
			@Override
			public void launch() {}
			@Override
			public void doIt() {}
			@Override
			public void then() {}
		};
		
		SyncProgress p = new SyncProgress();
		int id1 = p.addTask(stubTask, 1);
		int id2 = p.addTask(stubTask, 2);
		int id3 = p.addTask(stubTask, 3);
		
		p.markAsProcessed(id1);
		assertEquals(1, p.getProcessed());
		p.markAsProcessed(id2);
		assertEquals(3, p.getProcessed());
		p.markAsProcessed(id3);
		assertEquals(6, p.getProcessed());
	}
}
