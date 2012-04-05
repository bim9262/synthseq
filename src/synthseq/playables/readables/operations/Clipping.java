package synthseq.playables.readables.operations;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class Clipping extends ReadableSound {
	private ReadableSound r;
	private Variable max;

	public Clipping(ReadableSound r, Variable max) {
		this.r = r;
		this.max = max;
	}
	public Clipping(ReadableSound r, double max){
		this(r,new Variable(max));
	}

	@Override
	public double read() {
		double val = r.read();
		if (val > Math.abs(max.read()))
			return max.read();
		if (val < -Math.abs(max.read()))
			return -max.read();
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
