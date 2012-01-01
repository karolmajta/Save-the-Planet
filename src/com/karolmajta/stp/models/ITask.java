package com.karolmajta.stp.models;

public interface ITask {
	/**
	 * Method used by callers to start task processing.
	 */
	public void launch();
	
	/**
	 * Body of the task end user will override.
	 */
	public void doIt();
	
	/**
	 * Callback that will get called after doIt finishes. With synchronous
	 * task it means it gets called just after doIt(). With asynchronous
	 * task it means that it gets called after the thread wrapping doIt
	 * finishes, so calling launch is non blocking.
	 */
	public void then();
}
