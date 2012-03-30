package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;

public class HighPass extends ReadableSound {
	private ReadableSound r;
	private double alpha;

	public HighPass(ReadableSound r, double alpha) {
		this.r = r;
		this.alpha = alpha;
	}
	
	@Override
	public double read() {
		//see http://en.wikipedia.org/wiki/High-pass_filter#Algorithmic_implementation
		   double y;
		   y = r.read();
		   return  alpha * y + alpha * (- r.read() + r.read());
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
