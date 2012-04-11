package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;
import synthseq.playables.readables.waveforms.PulseWave;

public class HighPass extends ReadableSound {
	private ReadableSound r;
	private Variable alpha;
	private double old, oldx, offset = 0;
	private boolean running = false;

	public HighPass(ReadableSound r, Variable v) {
		this.r = r;
		old = r.read();
		oldx = old;
		this.alpha = v;
		if(r instanceof PulseWave)
			offset = 0.5;
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
		old = out;
		oldx=x;
		return out+offset;
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
