package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;

public class SineWave extends ReadableSound{
	private double time = 0;
	private Variable freq;
	private boolean running = false;
	
	public SineWave(double freq){
		this(new Variable(freq));
	}
	public SineWave(Variable freq) {
		this.freq = freq;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		time += 2*Math.PI/(44100/freq.read());
		if(time >= Math.PI*2)
			time -= Math.PI*2;
		return Math.sin(time);
	}

	@Override
	public void start() {
		running = true;
		freq.start();
	}

	@Override
	public void stop() {
		running = false;
		freq.stop();
	}

}
