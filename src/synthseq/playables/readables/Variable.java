package synthseq.playables.readables;

public class Variable extends ReadableSound {
	// Readable, all it does is return a constant value that can be changed.
	private double value = 0;
	
	public Variable(double val){
		value = val;
	}
	public void setValue(double val){
		value = val;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public double read() {
			return value;
	}
}
