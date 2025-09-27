package com.borec.backend.stopwatch;

import java.util.ArrayList;
import java.util.List;

public class StopWatchNotifier {
	
	List<StopWatchListener> list = new ArrayList<>();
	
	public void addStopWatchListener(StopWatchListener listener) {
		list.add(listener);
	}
	
	public void notifyListeners(long time) {
		list.forEach(listener -> listener.onTimeChangedIn100MillisecondsInterval(time));
	}
}
