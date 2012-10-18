package scroreWriter.model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Measure extends ArrayList<MusicalObject>{

	private int timeSigHigh, timeSigLow;
	private Clef clef;

	public Measure() {
		timeSigHigh = 4;
		timeSigLow = 4;
		setClef(Clef.TREBLE);
	}

	public Measure(int timeSigHigh, int timeSigLow, Clef clef) {
		this.timeSigHigh = timeSigHigh;
		this.timeSigLow = timeSigLow;
		this.setClef(clef);
	}
	
	public void setTimeSigHigh(int timeSigHigh){
		this.timeSigHigh = timeSigHigh;

	}
	
	public void setTimeSigLow(int timeSigLow){
		this.timeSigLow = timeSigLow;

	}

	public double length() {
		return timeSigHigh / (timeSigLow / 4.0);
	}

	public boolean isFull() {
		double time = 0;
		for (MusicalObject m : this) {
			time += m.duration.getLength();
		}
		return time >= length();
	}

	public Clef getClef() {
		return clef;
	}

	public void setClef(Clef clef) {
		this.clef = clef;
	}

}
