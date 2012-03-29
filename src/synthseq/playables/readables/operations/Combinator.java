package synthseq.playables.readables.operations;

import java.util.Collection;

import synthseq.playables.readables.ReadableSound;


public class Combinator extends ReadableSound {
	private Collection<ReadableSound> elements;

	public Combinator(Collection<ReadableSound> elements) {
		this.elements = elements;
	}

	@Override
	public double read() {
		double a = 0;
		for (ReadableSound p : elements)
			a += p.read() / elements.size();
		return a;
	}

	@Override
	public void start() {
		for (ReadableSound p : elements)
			p.start();
	}

	@Override
	public void stop() {
		for (ReadableSound p : elements)
			p.stop();
	}
}
