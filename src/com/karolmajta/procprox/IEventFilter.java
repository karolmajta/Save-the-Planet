package com.karolmajta.procprox;

public interface IEventFilter<Event> {
	/**
	 * 
	 * @param e
	 * @return true if given Event e should be ignored, false otherwise
	 */
	public boolean filter(Event e);
}
