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
		time += 1.00 / (44100 / freq.read());
		if (amplitude > 0) {
			if (time >= ratio.read()) {
				time = 0;
				amplitude = -amplitude;
			}
		} else {
			if (time >= (1 - ratio.read())) {
				time = 0;
				amplitude = -amplitude;
			}
		}
		return amplitude;
	}

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

}
