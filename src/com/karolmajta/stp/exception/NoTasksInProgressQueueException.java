package com.karolmajta.stp.exception;

public class NoTasksInProgressQueueException extends STPException {
	@Override
	public String toString() {
		return "IProgress has no more ITask's to process.";
	}
}
