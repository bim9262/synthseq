package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class HighPass extends ReadableSound {
	private ReadableSound r;
	private Variable alpha;
	private double old, oldx;
	private boolean running = false;
	private int sameCount = 0;

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
		
		if(!running)
			return 0;
		double x = r.read();
		double out = alpha.read() * old + alpha.read() * (x - oldx);
		if(old==out)
			sameCount++;
		else
			sameCount = 0;
		old = out;
		oldx=x;
		if(sameCount > 44100){
			stop();
			sameCount = 0;
		}
		return out;
	}

	@Override
	public void start() {
		running = true;
		r.start();
		alpha.start();
	}

	@Override
	public void stop() {
		running = false;
		r.stop();
		alpha.stop();
	}
}
