package com.karolmajta.stp.models;

/**
 * This interface describes any object that can be notified about the time
 * elapsed between calls. It is used in game loop to provide time data for
 * physics and graphics, so that they're based on "real world's" values,
 * not the frame rate that can be not consistent between devices. 
 * 
 * @author Karol Majta
 */
public interface ITickable {
	
	/**
	 * Called to notify object on how many time elapsed
	 * since last call to {@link ITickable#tick(long)}.
	 * 
	 * @param dt Time in milliseconds since last call to
	 * 		{@link ITickable#tick(long)} 
	 */
	public void tick(long dt);
	
	/**
	 * Called to mark this object as ignoring calls to {@link ITickable#tick(long)}.
	 * Implementation of this method is free. There is no guarantee that after calling
	 * {@link ITickable#ignoreTicks(boolean)} {@link ITickable#ignoresTicks()} will
	 * change it's returned value. The only requirement is to keep
	 * {@link ITickable#ignoresTicks()} with sync with the state of the object. So
	 * if you plan to ignore calls to {@link ITickable#tick(long)} let the user know
	 * by changing the behavior of {@link ITickable#ignoresTicks()}.
	 * 
	 * @param flag boolean indicator of whether calls to {@link ITickable#tick(long)}
	 * 		should be ignored or not.
	 */
	public void ignoreTicks(boolean flag);
	
	/**
	 * Returns a value whether this object ignores calls to {@link ITickable#tick(long)}
	 * or not. Keep in mind that the value is just an indicator, and it is possible to
	 * implement this interface in such way, that calls to {@link ITickable#tick(long)}
	 * will affect state of the object.
	 * 
	 * @return boolean indicator of whether this object is expected to ignore calls
	 * 		to {@link ITickable#tick()} or not
	 */
	public boolean ignoresTicks();
}
