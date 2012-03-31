package synthseq.playables.readables.envelopes;

import synthseq.playables.readables.ReadableSound;

public class Linear extends ReadableSound{
	/*
	 * Linear filter should be modeled on the ADSR envelope.
	 * This envelope is the simplest, just start at 1, and 
	 * reduce read value linearly
	 * over the time specified by the period.
	 * Remember, sampling frequency is 44.1kHz, so a period of 1
	 * should decay linearly from 1 to -1 over 
	 * 44.1k samples/calls to read().
	 */
	public Linear(double period){
		
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double read() {
		// TODO Auto-generated method stub
		return 0;
	}

}
