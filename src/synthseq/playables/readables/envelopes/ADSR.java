package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;

public class ADSR extends ReadableSound {
	double attack;
	double peek;
	double decay;
	double level;
	double release;
	private boolean running = false;
	private double amplitude = -1;
	private double time;
	private ReadableSound sound;

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
	public ADSR(ReadableSound sound, double attack, double peek, double decay, double level,
			double release) {
		this.sound = sound;
		this.attack = attack;
		this.peek = peek;
		this.decay = decay;
		this.level = level;
		this.release = release;

	}

	@Override
	public void start() {
		amplitude = 0;
		time = 0;
		running = true;
		sound.start();
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public double read() {
		time += 1.0 / 44100;
		if (!running) {
			amplitude = (amplitude>0 ?(amplitude - (level) / (release * 44100)) : 0);
		} else if (time <= attack) {
			amplitude += peek / (attack * 44100);
		} else if (time > attack && time <= attack + decay) {
			amplitude += (level - peek) / (decay * 44100);
		} else if (time > attack + decay) {
			amplitude = level;
		}
		return amplitude * sound.read();
	}


}
