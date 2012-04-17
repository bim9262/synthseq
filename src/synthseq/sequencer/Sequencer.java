package synthseq.sequencer;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Sequencer {
	private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
			Runtime.getRuntime().availableProcessors() + 2);

	public static void evalAtTime(final long time, final Runnable code) {
		executor.schedule(code, time, TimeUnit.MILLISECONDS);
	}

	public static void evalPeriodic(final long startTime, final long period,
			final Runnable code) {
		executor.scheduleAtFixedRate(code, startTime, period,
				TimeUnit.MILLISECONDS);
	}

	public static void stopTasks() {
		executor.shutdownNow();
		executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime()
				.availableProcessors() + 2);
	}
}
