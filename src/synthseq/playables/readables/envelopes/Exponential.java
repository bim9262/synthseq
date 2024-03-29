package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;


public class Exponential extends ReadableSound{
	private double amplitude;
	private double period;
	private double t;
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
		t = -Math.log(period);
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
		} else if (amplitude <= -1) {
			running = false;
		} else{
			t += 2/(period * 44100);
			amplitude = Math.pow(Math.E, -t);
		}
		return amplitude*2-1;
		
	}
}
