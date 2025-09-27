package com.borec.backend.stopwatch;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.borec.backend.entity.Action;
import com.borec.backend.entity.ActionType;
import com.borec.backend.service.ActionService;

import cz.borec.stopwatch.util.TimeUtils;

/**
 * The StopWatch class implements a stopwatch. It measures time with millisecond accuracy.
 * 
 * Třída StopWatch realizuje stopky. Měří čas s přesností na milisekundy.
 */
@Component("StopWatchBean")
public class StopWatch {

	private static final Logger logger = LogManager.getLogger(StopWatch.class);
	
	private Timer timer;

	private long time = 0;
	private long timeInIntervals = 0;
	private long starttime;
	private long endtime;

	private StopWatchNotifier stopWatchNotifier = new StopWatchNotifier();
	private static final int INTERVAL = 10;
	private ActionService actionService;

	private StopWatch() { }
	
	@org.springframework.beans.factory.annotation.Autowired
	public void setActionService(ActionService greetingService) {
		this.actionService = greetingService;
	}
	
	public static StopWatch createInstance() {
		return new StopWatch();
	}

	//TODO: remove
	public static void main(String[] args) {

		StopWatch stopWatch = createInstance();

		stopWatch.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stopWatch.stop();
	}

	public void start() {
	       
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				refresh(timeInIntervals);
				timeInIntervals += INTERVAL;
			}
		};
		timer = new Timer();
		int delay = 0;
		if (time > 0) {
			delay = INTERVAL - (int) (time % INTERVAL);
		} 
		timer.scheduleAtFixedRate(timerTask, delay, INTERVAL);
		starttime = System.currentTimeMillis(); 
	       logger.info("Start: " + starttime);
	       logger.debug("Start: " + starttime);
			actionService.insert(new Action(ActionType.START, starttime));

	}

	public void reset() {
		timeInIntervals = 0L;
		time = 0L;
		refresh(time);
	}

	public void stop() {
		timer.cancel();
		endtime = System.currentTimeMillis();
		time += endtime - starttime;
		refresh(time);
		System.out.println(TimeUtils.displayTime(time));
		System.out.println(TimeUtils.displayTime(timeInIntervals - INTERVAL));
	}


	public void refresh(long timeParameter) {
		stopWatchNotifier.notifyListeners(timeParameter);
	}

	public void addStopWatchListener(StopWatchListener listener) {
		stopWatchNotifier.addStopWatchListener(listener);
	}
}
