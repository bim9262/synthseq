package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;



public class TriangleWave extends ReadableSound{
	private double amplitude = 0;
	private double direction = 1;
	private Variable freq;
	private boolean running = false;

	public TriangleWave(Variable freq){
		this.freq=freq;
	}
	public TriangleWave(double freq) {
		this(new Variable(freq));
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		amplitude += (2.0*direction)/(44100/freq.read());
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
