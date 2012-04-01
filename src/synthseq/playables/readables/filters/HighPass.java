package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;

public class HighPass extends ReadableSound {
	private ReadableSound r;
	private double alpha;
	private double old, oldx;

	public HighPass(ReadableSound r, double alpha) {
		this.r = r;
		old = r.read();
		oldx = old;
		this.alpha = alpha;
	}

	@Override
	public double read() {
		// see
		// http://en.wikipedia.org/wiki/High-pass_filter#Algorithmic_implementation
		double x = r.read();
		double out = alpha * old + alpha * (x - oldx);
		old = out;
		oldx=x;
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
