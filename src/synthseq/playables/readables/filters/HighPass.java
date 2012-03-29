package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;

public class HighPass extends ReadableSound {
	private ReadableSound r;
	private double alpha;

	public HighPass(ReadableSound r, double alpha) {
		this.alpha = alpha;
	}

	@Override
	public double read() {
		//TODO
		//see http://en.wikipedia.org/wiki/High-pass_filter#Algorithmic_implementation
		return 0;
	}

	@Override
	public void start() {
		r.start();
	}

	@Override
	public void stop() {
		r.stop();
	}
}
