package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;



public class TriangleWave extends ReadableSound{
	private double amplitude = 0;
	private double direction = 1;
	private double freq;
	private boolean running = false;

	public TriangleWave(double freq) {
		this.freq = freq;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		amplitude += (2.0*direction)/(44100/freq);
		if(amplitude <= -1 || amplitude >=1)
			direction = -1*direction;
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
