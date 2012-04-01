package synthseq.playables.readables.operations;

import synthseq.playables.readables.ReadableSound;

public class SmoothClipping extends ReadableSound {
	private ReadableSound r;
	private double max;
	
	public SmoothClipping(ReadableSound r, double max) {
	this.r = r;
	this.max = max;
	}
	
	@Override
	public double read() {
		double val = r.read();
		double det = val/Math.abs(max);
		if (val > Math.abs(max))
			return val/det;
		if (val < -Math.abs(max))
			return val/det;
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

