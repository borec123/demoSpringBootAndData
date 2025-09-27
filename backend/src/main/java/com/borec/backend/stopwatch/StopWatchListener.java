package com.borec.backend.stopwatch;

/**
 * All listeners of {@link StopWatch} class should implement 
 * StopWatchListener interface and register via {@link StopWatch#addStopWatchListener(StopWatchListener)} method.
 */
public interface StopWatchListener {
	
	void onTimeChangedIn100MillisecondsInterval(long time);

}
