package com.karolmajta.stp.models;

/**
 * Basic implementation of {@link ITickable} providing default
 * (and probably the most expected) behavior. {@link Tickable}
 * will start ignoring calls to {@link Tickable#tick(long)} after
 * {@link Tickable#ignoreTicks(boolean)} was called with
 * true, and will stop ignoring them after called with false.
 * 
 * @author Karol Majta
 *
 */
public abstract class Tickable implements ITickable {
	private boolean ignore;
	
	public Tickable() {
		ignore = false;
	}
	
	/**
	 * Overwrite this method to provide specific behavior when calls to
	 * {@link Tickable#tick(long)} are generated.
	 * 
	 * @param dt Time in milliseconds since this object was last ticked.
	 * 		Notice that these intervals are between calls to
	 * 		{@link Tickable#tick(long)}, not {@link Tickable#onTick(long)}.
	 * 		This might be important when calling
	 * 		{@link Tickable#ignoreTicks(boolean)} provided argument can
	 * 		not be used as to calculate the length of ignore-period. 
	 */
	public abstract void onTick(long dt);
	
	/**
	 * Will call {@link Tickable#onTick(long)} if
	 * {@link Tickable#ignoresTicks()} returns false, else will
	 * do nothing.
	 */
	@Override
	public final void tick(long dt) {
		if(!ignoresTicks()){
			onTick(dt);
		}
	}

	/**
	 * Will always affect value returned by
	 * {@link Tickable#ignoresTicks()}
	 */
	@Override
	public final void ignoreTicks(boolean flag) {
		ignore = flag;
	}
	
	/**
	 * Will always return the last value passed to
	 * {@link Tickable#ignoreTicks(boolean)}
	 */
	@Override
	public final boolean ignoresTicks() {
		return ignore;
	}
}
