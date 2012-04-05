package synthseq.playables.readables;

import synthseq.synthesizer.LineOut;

public class LineIn extends ReadableSound {
	private boolean running = false;

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		return LineOut.readMic();
	}

}
