package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;

public class PulseWave extends ReadableSound {
	private double time = 0, amplitude = -1, ratio;
	private double freq;
	private boolean running = false;

	public PulseWave(double freq) {
		this(freq, 0.5);
	}

	public PulseWave(double freq, double ratio) {
		this.freq = freq;
		this.ratio = ratio;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		time += 1.00 / (44100 / freq);
		if (amplitude > 0) {
			if (time >= ratio) {
				time = 0;
				amplitude = -amplitude;
			}
		} else {
			if (time >= (1 - ratio)) {
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
