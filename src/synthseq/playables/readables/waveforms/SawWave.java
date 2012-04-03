package synthseq.playables.readables.waveforms;

import synthseq.playables.readables.ReadableSound;



public class SawWave extends ReadableSound{
	private double time = 0;
	private double freq;
	private boolean running = false;

	public SawWave(double freq) {
		this.freq =  44100/freq;
	}

	@Override
	public double read() {
		if (!running)
			return 0;
		time++;
		if(time>=freq)
			time-=freq;
		return (time/freq)*2-1;
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
