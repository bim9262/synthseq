package synthseq.playables.readables.operations;

import java.util.ArrayList;
import java.util.Collection;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

/*
 * Adds multiple waveforms together
 */
public class Adder extends Variable {
	private Collection<ReadableSound> elements;

	public Adder(Collection<ReadableSound> elements) {
		this.elements = elements;
	}
	
	public Adder(ReadableSound element) {
		this.elements = new ArrayList<ReadableSound>();
		elements.add(element);
	}

	@Override
	public double read() {
		double a = 0;
		for (ReadableSound p : elements)
			a += p.read();
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
