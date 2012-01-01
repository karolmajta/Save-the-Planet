package com.karolmajta.stp.models;

import java.util.ArrayList;

import com.karolmajta.stp.exception.NoTasksInProgressQueueException;

public class SyncProgress implements IProgress {
	private class TaskWeightPair {
		public ITask task;
		public int weight;
		public TaskWeightPair(ITask task, int weight) {
			this.task = task;
			this.weight = weight;
		}
	}
	
	private ArrayList<TaskWeightPair> tasks;
	private int addPointer;
	private int processPointer;
	private int weightsTotal;
	private int weightsProcessed;
	
	public SyncProgress() {
		tasks = new ArrayList<TaskWeightPair>();
		addPointer = 0;
		processPointer = 0;
		weightsTotal = 0;
		weightsProcessed = 0;
	}
	
	@Override
	public int addTask(ITask task, int weight) {
		tasks.add(addPointer, new TaskWeightPair(task, weight));
		addPointer++;
		weightsTotal+=weight;
		return addPointer-1;
	}

	@Override
	public int getTotal() {
		return weightsTotal;
	}

	@Override
	public int getProcessed() {
		return weightsProcessed;
	}

	@Override
	public void markAsProcessed(int id) {
		weightsProcessed += tasks.get(id).weight;
	}

	@Override
	public boolean hasNextTask() {
		return processPointer <= tasks.size()-1 ? true : false;
	}

	@Override
	public boolean doNext() throws NoTasksInProgressQueueException {
		if(!hasNextTask()){
			throw new NoTasksInProgressQueueException();
		}
		ITask t = tasks.get(processPointer).task;
		t.launch();
		markAsProcessed(processPointer);
		processPointer++;
		return hasNextTask();
	}

}
