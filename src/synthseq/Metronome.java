package synthseq;
import java.util.Vector;

public class Metronome {
	private static int count = 0;
	private static Vector<Action> actions = new Vector<Action>();

	public static void start(final int bpm) {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(60000 / bpm);
						synchronized (actions) {
							for (int i = 0; i < actions.size(); i++) {
								if (actions.get(i).getBeat() == count) {
									actions.get(i).act();
									actions.remove(i);
								}
							}
						}
						count++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public static void addAction(Action a, int beats) {
		synchronized (actions) {
			a.setBeat(count + beats);
			actions.add(a);
		}
	}
}
