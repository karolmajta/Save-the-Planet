package com.karolmajta.stp.exception;

/**
 * Raised when a draw is called on View before binding a model. 
 * 
 * @author Karol
 *
 */
public class UnboundViewException extends STPException {
	@Override
	public String toString() {
		return "No model is bound to this view. Call bindModel first.";
	}
}
