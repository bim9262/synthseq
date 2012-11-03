package scroreWriter.model;

import java.util.ArrayList;

import java.awt.Point;



public class Note extends MusicalObject {

	private char note;
	private int octave;
	private ArrayList<Accidental> accidentals = new ArrayList<Accidental>() ;

	public Note(String center) {
		super(center);
	}

	public int getMIDIOffset(){
		int sum = 0;
		for (Accidental accidental: accidentals){
			sum+=accidental.getMIDIOffset();
		}
		return sum;
	}

}