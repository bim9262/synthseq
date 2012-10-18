package scroreWriter.model;

import java.util.ArrayList;

public class Measure {

	ArrayList<MusicalObject> meausure = new ArrayList<MusicalObject>();

	public boolean isFull() {
		double time = 0;
		for (MusicalObject m : meausure) {
			time += m.duration.getLength();
		}
		return time == 1.0;
	}
	
}
