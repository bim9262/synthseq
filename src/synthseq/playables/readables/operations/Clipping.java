package synthseq.playables.readables.operations;

import synthseq.playables.readables.ReadableSound;

public class Clipping extends ReadableSound {
	private ReadableSound r;
	private double max;

	public Clipping(ReadableSound r, double max) {
		this.r = r;
		this.max = max;
	}

	@Override
	public double read() {
		double val = r.read();
		if (val > Math.abs(max))
			return max;
		if (val < -Math.abs(max))
			return -max;
		return val;
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
