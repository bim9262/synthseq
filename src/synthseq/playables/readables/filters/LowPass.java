package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;

public class LowPass extends ReadableSound {
	private ReadableSound r;
	private double alpha;
	private double old = 0;

	public LowPass(ReadableSound r, double alpha) {
		this.r = r;
		old = r.read();
		this.alpha = alpha;
	}

	@Override
	public double read() {
		// see
		// http://en.wikipedia.org/wiki/Low-pass_filter#Algorithmic_implementation
		double out = alpha * r.read() + (1 - alpha) * old;
		old = out;
		return out;
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
