package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class HighPass extends ReadableSound {
	private ReadableSound r;
	private Variable alpha;
	private double old, oldx;

	public HighPass(ReadableSound r, Variable v) {
		this.r = r;
		old = r.read();
		oldx = old;
		this.alpha = v;
	}
	
	public HighPass(ReadableSound r, double v){
		this(r,new Variable(v));
	}

	@Override
	public double read() {
		// see
		// http://en.wikipedia.org/wiki/High-pass_filter#Algorithmic_implementation
		double x = r.read();
		double out = alpha.read() * old + alpha.read() * (x - oldx);
		old = out;
		oldx=x;
		return out;
	}

	@Override
	public void start() {
		r.start();
		alpha.start();
	}

	@Override
	public void stop() {
		r.stop();
		alpha.stop();
	}
}
