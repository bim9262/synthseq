package synthseq.playables.readables.operations;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

/*
 * Multiplies Waveforms by waveforms or scalars
 */
public class Multiplier extends ReadableSound {
	private ReadableSound p;
	private ReadableSound mult = null;

	public Multiplier(ReadableSound p, double magnitude) {
		this(p,new Variable(magnitude));
	}

	public Multiplier(ReadableSound p, ReadableSound magnitude) {
		mult = magnitude;
		this.p = p;
	}

	@Override
	public double read() {
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
