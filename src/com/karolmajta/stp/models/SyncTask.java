package com.karolmajta.stp.models;

public abstract class SyncTask implements ITask {
	/**
	 * Synchronously launches doIt() and then(), both are blocking.
	 */
	@Override
	public void launch() {
		doIt();
		then();
	}
}
