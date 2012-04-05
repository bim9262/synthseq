package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;
import synthseq.playables.readables.Variable;



public class SawWave extends ReadableSound{
	private double time = 0;
	private Variable freq;
	private boolean running = false;

	public SawWave(double freq){
		this(new Variable(freq));
	}
	public SawWave(Variable freq) {
		this.freq =  freq;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		time++;
		if(time>=44100/freq.read())
			time-=44100/freq.read();
		return (time/(44100/freq.read()))*2-1;
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
