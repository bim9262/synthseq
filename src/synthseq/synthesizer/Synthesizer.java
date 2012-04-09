package synthseq.synthesizer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import synthseq.playables.readables.ReadableSound;
import synthseq.signalanalysis.FrequencyDomain;
import synthseq.signalanalysis.Visualizer;

public class Synthesizer {
	private LineOut out;
	private Map<ReadableSound, Integer> readables = new HashMap<ReadableSound, Integer>();
	private double threshold = .01;
	private final int quietSampling = 10000;
	private int quietSamplingCount = 0;
	private final int quietDuration = (44100 * 1) / quietSampling;
	private Visualizer vis = new Visualizer();
	private FrequencyDomain fdVis = new FrequencyDomain(8);
	private static Synthesizer instance = null;

	private Synthesizer() {
		this.out = LineOut.getInstance();
		start();
	}

	public int size() {
		return readables.size();
	}

	public static Synthesizer getInstance() {
		if (instance == null)
			instance = new Synthesizer();
		return instance;
	}

	public synchronized void addSource(ReadableSound r) {
		synchronized (readables) {
			if (!readables.containsKey(r)) {
				readables.put(r, 0);
			}
		}
	}

	public void showVisualizer() {
		vis.show();
	}

	public void hideVisualizer() {
		vis.hide();
	}

	public void showFreqVis() {
		fdVis.show();
	}

	public void hideFreqVis() {
		fdVis.hide();
	}

	public void kill() {
		synchronized (readables) {
			for (ReadableSound r : readables.keySet())
				r.stop();
		}
	}

	public void start() {
		new Thread() {
			public void run() {
				while (true) {
					double add = 0;
					synchronized (readables) {
						for (Iterator<ReadableSound> it = readables.keySet()
								.iterator(); it.hasNext();) {
							ReadableSound r = it.next();
							double a = r.read();
							add += a / 10;
							if (quietSamplingCount++ >= quietSampling) {
								quietSamplingCount = 0;
								if (Math.abs(a) < threshold)
									readables.put(r, readables.get(r) + 1);
								else
									readables.put(r, 0);
								if (readables.get(r) > quietDuration) {
									it.remove();
								}
							}
						}
						add = (add > 1) ? 1 : (add < -1) ? -1 : add;
						out.writeM(add);
						if (vis.isVisible())
							vis.write(add);
						if (fdVis.isVisible())
							fdVis.write(add);
					}
				}
			}
		}.start();
	}
}
