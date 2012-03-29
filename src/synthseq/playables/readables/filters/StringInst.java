package synthseq.playables.readables.filters;

import synthseq.playables.readables.ReadableSound;

public class StringInst extends ReadableSound {
	private double[] buffer;
	private int first = 0;
	private int last = 0;
	private int tension = 0;
	private double damper = .999;
	private double defDamper = .999;

	public StringInst(double freq) {
		int len = (int) (44100 / freq);
		buffer = new double[len - tension];
		last = len;
	}

	public void start() {
		damper = defDamper;
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = (Math.random()) * 2 - 1;
		}
	}

	public void stop() {
		damper = .99;
	}

	public double read() {
		double a = .05;
		double tmp = damper * (a * pop() + (1 - a) * peek());
		push(tmp);
		return tmp;
	}

	private void push(double d) {
		if (last++ >= buffer.length - 1)
			last = 0;
		buffer[last] = d;
	}

	private double pop() {
		double tmp = buffer[first];
		if (first++ >= buffer.length - 1)
			first = 0;
		return tmp;
	}

	private double peek() {
		return buffer[first];
	}
}