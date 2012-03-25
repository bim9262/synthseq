package playables;
public class SawWave implements Readable{
	private double amplitude = 0;
	private double freq;
	private boolean running = false;

	public SawWave(double freq) {
		this.freq = freq;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		amplitude += 2.0/(44100/freq);
		return amplitude;
	}

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}
	
	public void shiftFreq(float f) {
		freq+=f;
		if(freq<0)
			freq = 0;
	}

}
