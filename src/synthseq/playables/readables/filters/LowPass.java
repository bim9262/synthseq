package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;
import synthseq.playables.readables.waveforms.PulseWave;

public class LowPass extends ReadableSound {
	private ReadableSound r;
	private Variable alpha;
	private double old = 0;
	private double offset = 0;
	private boolean running = false;

	public LowPass(ReadableSound r, Variable v) {
		this.r = r;
		old = r.read();
		this.alpha = v;
		if(r instanceof PulseWave)
			offset = 0.5;
	}
	
	public LowPass(ReadableSound r, double v){
		this(r,new Variable(v));
	}

	@Override
	public double read() {
		// see
		// http://en.wikipedia.org/wiki/Low-pass_filter#Algorithmic_implementation
		if(!running)
			return 0;
		double out = alpha.read() * r.read() + (1 - alpha.read()) * old;
		old = out;
		return out+offset;
	}

	@Override
	public void start() {
		running  = true;
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
