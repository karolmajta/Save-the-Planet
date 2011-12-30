package com.karolmajta.stp.exception;

public class UnboundViewException extends STPException {
	@Override
	public String toString() {
		return "No model is bound to this view. Call bindModel first.";
	}
}
