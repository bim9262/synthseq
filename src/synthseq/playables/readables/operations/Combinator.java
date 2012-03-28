package synthseq.playables.readables.operations;

import synthseq.playables.readables.Readable;


public class Combinator extends Readable {
	private Readable[] elements;

	public Combinator(Readable... element) {
		elements = element;
	}

	@Override
	public double read() {
		double a = 0;
		for (Readable p : elements)
			a += p.read() / elements.length;
		return a;
	}

	@Override
	public void start() {
		for (Readable p : elements)
			p.start();
	}

	@Override
	public void stop() {
		for (Readable p : elements)
			p.stop();
	}
}
