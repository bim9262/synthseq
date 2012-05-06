package scroreWriter;

import java.util.ArrayList;

import java.awt.Point;

public class Note extends MusicalObject {

	private char note;
	private int octave;
	private ArrayList<Accidental> accidentals = new ArrayList<Accidental>() ;

	Note (Duration duration){
		this.duration = duration;
	}

	Note(String s) {
	}

	@Override
	public Point getHotspot() {
		return new Point(image.getWidth()/2, 25);
	}

	public int getMIDIOffset(){
		int sum = 0;
		for (Accidental accidental: accidentals){
			sum+=accidental.getMIDIOffset();
		}
		return sum;
	}

}
