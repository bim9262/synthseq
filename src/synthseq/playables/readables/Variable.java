package synthseq.playables.readables;

public class Variable extends ReadableSound {
	// Readable, all it does is return a constant value that can be changed.
	private double value = 0;
	private ReadableSound sound;

	public Variable(){
		this(0);
	}
	public Variable(double val) {
		value = val;
	}

	public Variable(ReadableSound val) {
		sound = val;
	}

	public void setValue(double val) {
		value = val;
	}

	@Override
	public void start() {
		if (sound != null)
			sound.start();
	}

	@Override
	public void stop() {
		if (sound != null)
			sound.stop();
	}

	@Override
	public double read() {
		if (sound != null)
			return (sound.read() + 1) / 2;
		return value;
	}
}
