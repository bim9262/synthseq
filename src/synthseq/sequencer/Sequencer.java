package synthseq.sequencer;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Sequencer {
	private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
			20);

	public static void evalAtTime(final long time, final Runnable code) {
		executor.schedule(new Thread() {
			public void run() {
				code.run();
				executor.remove(this);
			}
		}, time, TimeUnit.MILLISECONDS);
	}
}
