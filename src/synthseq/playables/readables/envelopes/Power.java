package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;

/*
 * See the linear filter. Same idea, except decay is a power function.
 */

public class Power extends ReadableSound{
	private double amplitude;
	private double period;
	private double t;
	private boolean running = false;
	
	public Power(double period){
		this.period = period;
		t = Math.sqrt(period);
	}
	
	public void start() {
		amplitude = 1;
		running = true;
		
	}

	
	public void stop() {
		running = false;
	}

	
	public double read() {
		if (!running) {
			return -1;
		} else if (amplitude <= -1) {
			running = false;
		} else{
			t += 2/(period * 44100);
			amplitude = 1 - Math.pow(t,2);
		}
		return amplitude*2-1;
	}

}
