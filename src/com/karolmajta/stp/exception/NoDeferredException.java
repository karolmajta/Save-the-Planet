package com.karolmajta.stp.exception;

import com.karolmajta.stp.exception.STPException;

public class NoDeferredException extends STPException {
	@Override
	public String toString() {
		return "No deferred was provided.";
	}
}
