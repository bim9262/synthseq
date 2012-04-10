package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class PulseWave extends ReadableSound {
	private double time = 0, amplitude = -1;
	private Variable ratio;
	private Variable freq;
	private boolean running = false;

	public PulseWave(Variable freq) {
		this(freq, 0.5);
	}
	public PulseWave(Variable freq, double ratio){
		this(freq,new Variable(ratio));
	}
	public PulseWave(double freq, double ratio){
		this(new Variable(freq),new Variable(ratio));
	}

	public PulseWave(Variable freq, Variable ratio) {
		this.freq = freq;
		this.ratio = ratio;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		time++;
		double val = freq.read();
		double ratVal = ratio.read();
		if(time>=44100/val)
			time-=44100/val;
		if(time>val*ratVal){
			amplitude=-1;
		}else{
			amplitude = 1;
		}
		return amplitude;
	}

	@Override
	public void start() {
		running = true;
		ratio.start();
		freq.start();
	}

	@Override
	public void stop() {
		running = false;
		ratio.stop();
		freq.stop();
	}

}
