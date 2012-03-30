package synthseq.playables.readables.operations;

import synthseq.playables.readables.ReadableSound;

/*
 * Multiplies Waveforms by waveforms or scalars
 */
public class Multiplier extends ReadableSound {
	private ReadableSound p;
	private ReadableSound mult = null;
	private double magnitude = 1;

	public Multiplier(ReadableSound p, double magnitude) {
		this.magnitude = magnitude;
		this.p = p;
	}

	public Multiplier(ReadableSound p, ReadableSound magnitude) {
		mult = magnitude;
		this.p = p;
	}

	@Override
	public double read() {
		if (mult == null)
			return p.read() * magnitude;
		else
			return p.read() * ((mult.read() + 1) / 2);
	}

	@Override
	public void start() {
		p.start();
		if (mult != null)
			mult.start();
	}

	@Override
	public void stop() {
		p.stop();
	}
}
