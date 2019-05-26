package com.jy.common.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleUtils {
	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
	
	public static void executeTimer(Runnable runnable, long initialDelay, long period) {
		scheduler.scheduleAtFixedRate(runnable, initialDelay, period, TimeUnit.SECONDS);
	}
}
