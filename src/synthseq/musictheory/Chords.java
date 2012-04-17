package synthseq.musictheory;

import static synthseq.musictheory.Scales.getScale;

import java.util.HashMap;

public class Chords {

	private HashMap<String,Integer> chordTypes = new HashMap<String,Integer>();

	Chords(){
		chordTypes.put("augmented",1);
		chordTypes.put("natural",0);
		chordTypes.put("diminished",-1);
		}

	public int[] getChord(String chordName) {
		return new int[]{};
	}

	private int[] getOddChord(int baseNote, boolean major, int length){
		return getOddChord(baseNote, major,length, "natural");
	}

	private int[] getOddChord(int baseNote, boolean major, int length, String augNatDim){
		int[] toReturn = new int[length];
		int[] scale = getScale(major?"major":"minor", baseNote, 2*length-1);
		for (int i = 0; i < length; i++){
			toReturn[i] = scale[2*i] + (i==0?0:chordTypes.get(augNatDim));
		}
		return toReturn;
	}
	private int[] getSixthChord(int baseNote, boolean major) {
		return new int[]{};
	}

	private int[] getXSixthesChord(int baseNote, int x) {
		return new int[]{};
	}

	private int[] getSuspensionChord(int baseNote, boolean major, int suspension) {
		return new int[]{};
	}

	public static void main(String[] args){
		boolean major = true;
		int baseNote = 0;
		int length = 5;
		System.out.print("Scale: ");
		for (int i: getScale(major?"major":"minor", baseNote, 2*length-1)){
			System.out.print(i + ", ");
		}
		System.out.print("\nChord: ");
		for (int i: new Chords().getOddChord(baseNote, major, length, "augmented")){
			System.out.print(i + ",    ");
		}



	}

}
