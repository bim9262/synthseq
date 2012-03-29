package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;

public class WhiteNoise extends ReadableSound {
	private boolean running = false;
	private int x = 123456789, y = 362436069, z = 521288629, w = 88675123;

	@Override
	public double read() {
		if (!running)
			return 0;
		//implementation of the Xorshift pseudorandom number generator
		int t = x ^ (x << 11);
		x = y;
		y = z;
		z = w;
		w = w ^ (w >>> 19) ^ (t ^ (t >> 8));
		return ((double) w) / Integer.MAX_VALUE;
	}

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

}
