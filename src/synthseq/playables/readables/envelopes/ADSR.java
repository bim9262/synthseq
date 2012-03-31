package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;

public class ADSR extends ReadableSound {
	/*
	 * See http://en.wikipedia.org/wiki/Synthesizer#ADSR_envelope This is
	 * arguably one of the most important things that has to happen for the
	 * synthesizer to sound good. This will be used by multiplying the envelope
	 * by a waveform so the range of read values should be from -1 to 1, as the
	 * multiplier scales all multiplying waveforms to 0-1. After start is
	 * called, this class should return a value that grows from -1 to the (peek
	 * value * 2) - 1 (to scale to the -1 to 1 range of ReadableSounds), then
	 * decay to the level value (scaled the same way) over the period specified
	 * by decay. The value should stay at the level value until stop is called,
	 * then decay to -1 over the period specified by release.
	 */
	public ADSR(double attack, double peek, double decay, double level,
			double release) {

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public double read() {
		// TODO Auto-generated method stub
		return 0;
	}

}
