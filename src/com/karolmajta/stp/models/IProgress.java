package com.karolmajta.stp.models;

import com.karolmajta.stp.exception.NoTasksInProgressQueueException;

/**
 * Model for various types of progress bars. Collects task, can process
 * them one-by-one and provide information on how many of theme were
 * processed.
 * 
 * @author Karol
 *
 */
public interface IProgress {
	/**
	 * Adds task binding a weight to it.
	 * 
	 * @param task ITask to be processed
	 * @param weight Weight associated with ITask. If two tasks are added with weights
	 * 		2 and 3, the first one is responsible for 40% of progress and second for 60%.
	 * @return unique id of added task (unique in context of this IProgress Instance).
	 */
	public int addTask(ITask task, int weight);
	
	/**
	 * Returns the sum of weights of all tasks (processed and not) that were added
	 * to this IProgress 
	 * 
	 * @return sum of all weights ever added to this IProgress.
	 */
	public int getTotal();
	
	/**
	 * Returns sum of events that were processed.
	 * 
	 * @return
	 */
	public int getProcessed();
	
	/**
	 * Mark ITask with given id as processed.
	 * 
	 * @param id uniqie (in this instance context) id of ITask
	 */
	public void markAsProcessed(int id);
	
	/**
	 * 
	 * @return true if there are unprocessed tasks in the queue, false otheriws.e
	 */
	public boolean hasNextTask();
	
	/**
	 * Process current task in queue.
	 * 
	 * @return true if after processing current task there are others in queue.
	 * @throws when called on IProgress that has no more tasks to process.
	 */
	public boolean doNext() throws NoTasksInProgressQueueException;
}