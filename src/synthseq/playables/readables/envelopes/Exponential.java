package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;


public class Exponential extends ReadableSound{
	private double amplitude;
	private double period;
	private boolean running = false;
	

	/*
	 * See the linear filter. Same idea, except decay is exponential.
	 */

	public Exponential(double period) {
		this.period = period;
	}

	@Override
	public void start() {
		amplitude = 1;
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public double read() {
		if (!running) {
			return -1;
		} else if (amplitude <= 0) {
			running = false;
		} else
			amplitude = 0;//TODO:Change to some equation
		return amplitude;
	}
}