package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;

public class Linear extends ReadableSound {
	private double amplitude = 1;
	private double period;
	private boolean running = false;

	/*
	 * Linear filter should be modeled on the ADSR envelope. This envelope is
	 * the simplest, just start at 1, and reduce read value linearly over the
	 * time specified by the period. Remember, sampling frequency is 44.1kHz, so
	 * a period of 1 should decay linearly from 1 to -1 over 44.1k samples/calls
	 * to read().
	 */
	public Linear(double period) {
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
		} else if (amplitude <= -1) {
			running = false;
		} else
			amplitude -= 1 / (44100 / period);
		return amplitude;

	}

}
