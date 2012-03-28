package synthseq.synthesizer;

import java.util.Vector;

import synthseq.playables.readables.Readable;

public class Synthesizer {
	private LineOut out;
	private Vector<Readable> readables = new Vector<Readable>();
	private Vector<Integer> quietCount = new Vector<Integer>();
	private double threshold = .01;
	private int quietDuration = 44100 * 1;
	private Visualizer vis = new Visualizer();

	public Synthesizer(LineOut out) {
		this.out = out;
		start();
	}

	public void addSource(Readable r) {
		readables.add(r);
		quietCount.set(readables.indexOf(r), 0);
	}

	public void kill() {
		for (Readable r : readables)
			r.stop();
	}

	public void start() {
		new Thread() {
			public void run() {
				while (true) {
					double add = 0;
					for (int i = 0; i < readables.size(); i++) {
						double a = readables.get(i).read();
						add += a / readables.size();
						if (Math.abs(a) < threshold)
							quietCount.set(i, quietCount.get(i) + 1);
						if (quietCount.get(i) > quietDuration) {
							readables.remove(i);
							quietCount.remove(i);
							i--;
						}
					}
					out.writeM(add);
					vis.write(add);
				}
			}
		}.start();
	}
}
