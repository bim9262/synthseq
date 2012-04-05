package synthseq.playables.readables.operations;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class SmoothClipping extends ReadableSound {
	private ReadableSound r;
	private Variable max;
	
	public SmoothClipping(ReadableSound r, Variable max) {
	this.r = r;
	this.max = max;
	}
	
	@Override
	public double read() {
		double val = r.read();
		double det = val/Math.abs(max.read());
		if (val > Math.abs(max.read()))
			return val/det;
		if (val < -Math.abs(max.read()))
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

