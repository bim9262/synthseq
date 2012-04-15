package synthseq.playables.readables;

public class Variable extends ReadableSound {
	// Readable, all it does is return a constant value that can be changed.
	private double value = 0;
	private boolean running = false;
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
		running = true;
		if (sound != null)
			sound.start();
	}

	@Override
	public void stop() {
		running = false;
		if (sound != null)
			sound.stop();
	}

	@Override
	public double read() {
		//if(!running)
		//	return 0;
		if (sound != null)
			return (sound.read() + 1) / 2;
		return value;
	}
}
