package scroreWriter.model;

import java.util.ArrayList;

import java.awt.Point;



public class Note extends MusicalObject {

	Note(){
		super();
	}

	Note(String s) {
		super(s);
	}

	private char note;
	private int octave;
	private ArrayList<Accidental> accidentals = new ArrayList<Accidental>() ;

	public int getMIDIOffset(){
		int sum = 0;
		for (Accidental accidental: accidentals){
			sum+=accidental.getMIDIOffset();
		}
		return sum;
	}

}
