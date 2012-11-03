package scroreWriter.model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Measure extends ArrayList<MusicalObject> {

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

	public void setTimeSigHigh(int timeSigHigh) {
		this.timeSigHigh = timeSigHigh;

	}

	public void setTimeSigLow(int timeSigLow) {
		this.timeSigLow = timeSigLow;

	}

	public double length() {
		return timeSigHigh / ((double) timeSigLow);
	}

	public boolean isFull() {
		double time = 0;
		for (MusicalObject m : this) {
			time += m.duration.getLength();
		}
		return time >= length();
	}

	public ArrayList<MusicalObject> getOverflow(){
		ArrayList<MusicalObject> toReturn = new ArrayList<MusicalObject>();
		double time = 0;
		double length = length();
		for (int i = 0; i < size(); i++) {
			time += get(i).duration.getLength();
			if (time > length){
				toReturn.add(this.get(i));
			}
		}
		return toReturn;
	}

	public Clef getClef() {
		return clef;
	}

	public void setClef(Clef clef) {
		this.clef = clef;
	}

}
