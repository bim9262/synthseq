package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;

public class LowPass extends ReadableSound {
	private ReadableSound r;
	private double alpha;
	private double[] buffer = new double[2];

	public LowPass(ReadableSound r, double RC) {
		this.r = r;
		buffer[0] = r.read();
		buffer[1] = r.read();
		this.alpha = (1 / 44100.0) / (RC + 1 / 44100.0);
		this.alpha = 0.5;
	}

	@Override
	public double read() {
		// see
		// http://en.wikipedia.org/wiki/Low-pass_filter#Algorithmic_implementation
		double out = alpha * buffer[1] + (1 - alpha) * buffer[0];
		buffer[0] = buffer[1];
		buffer[1] = r.read();
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
