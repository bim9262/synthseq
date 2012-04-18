package synthseq.musictheory;

import java.util.Collection;
import java.util.HashMap;

public class Chords {

	// Scale steps
	private static byte[] major = {4, 3};
	private static byte[] minor = {3, 4};
	private static byte[] diminished = {3, 3};
	private static byte[] augmented = {4, 4};
	private static byte[] suspended2 = {2, 5};
	private static byte[] suspended4 = {5, 2};
	private static byte[] flatFifth = {4, 2};
	private static byte[] sixth = {4, 3, 2};
	private static byte[] minorSixth = {3, 4, 2};
	private static byte[] seventh = {4, 3, 3};
	private static byte[] minorSeventh = {3, 4, 3};
	private static byte[] diminishedSeventh = {3, 3, 3};
	private static byte[] majorSeventh = {4, 3, 4};
	private static byte[] minorMajorSeventh = {3, 4, 4};
	private static byte[] sevenSix = {4, 3, 2, 1};
	private static byte[] ninth = { 4, 3, 3, 4 };
	private static byte[] minorNinth = { 3, 4, 3, 4 };
	private static byte[] flatNinth = { 4, 3, 3, 3 };
	private static byte[] minorFlatNinth = { 3, 4, 3, 3 };
	private static byte[] augmentedNinth = { 4, 3, 3, 5 };
	private static byte[] nineSix = { 4, 3, 2, 5 };
	private static byte[] minorNineSix = { 3, 4, 2, 5 };
	private static byte[] eleventh = { 4, 3, 3, 4, 3 };
	private static byte[] minorEleventh = { 3, 4, 3, 4, 3 };
	private static byte[] augmentedEleventh = { 4, 3, 3, 4, 4 };
	private static byte[] minorAugmentedEleventh = { 3, 4, 3, 4, 4 };
	private static byte[] thirteenth = { 4, 3, 3, 4, 3, 4 };
	private static byte[] minorThirteenth = { 3, 4, 3, 4, 3, 4 };
	private static byte[] thirteenthAugmentedEleventh = { 4, 3, 3, 4, 4, 3 };
	private static byte[] minorThirteenthAugmentedEleventh = { 3, 4, 3, 4, 4, 3 };
	private static HashMap<String, byte[]> chordMap = new HashMap<String, byte[]>();

	static {
		chordMap.put("M", major);
		chordMap.put("m", minor);
		chordMap.put("*", diminished);
		chordMap.put("+", augmented);
		chordMap.put("sus2", suspended2);
		chordMap.put("sus4", suspended4);
		chordMap.put("-5", flatFifth);
		chordMap.put("6th", sixth);
		chordMap.put("m6", minorSixth);
		chordMap.put("7th", seventh);
		chordMap.put("m7", minorSeventh);
		chordMap.put("*7", diminishedSeventh);
		chordMap.put("M7", majorSeventh);
		chordMap.put("mM7", minorMajorSeventh);
		chordMap.put("7/6", sevenSix);
		chordMap.put("9th",ninth);
		chordMap.put("m9",minorNinth);
		chordMap.put("-9",flatNinth);
		chordMap.put("m-9",minorFlatNinth);
		chordMap.put("9+",augmentedNinth);
		chordMap.put("9/6",nineSix);
		chordMap.put("m9/6",minorNineSix);
		chordMap.put("11th",eleventh);
		chordMap.put("m11",minorEleventh);
		chordMap.put("11+",augmentedEleventh);
		chordMap.put("m11+",minorAugmentedEleventh);
		chordMap.put("13th",thirteenth);
		chordMap.put("m13",minorThirteenth);
		chordMap.put("13+11",thirteenthAugmentedEleventh);
		chordMap.put("m13+11",minorThirteenthAugmentedEleventh);
	}

	private int[] getRomanNumeral(int baseNote, int romanNumeral, boolean major) {
		return getRomanNumeral(baseNote, romanNumeral, major, 3);
	}
	
	public static int[] getChord(int baseNote, String chord){
			byte[] chordNotes = chordMap.get(chord);
			int[] notes = new int[chordNotes.length+1];
			notes[0] = baseNote;
			for(int i = 1; i <= chordNotes.length; i++){
				notes[i] = notes[i-1] + chordNotes[(i-1)%chordNotes.length];
			}
			return notes;
	}

	private int[] getRomanNumeral(int baseNote, int romanNumeral,
			boolean major, int length) {
		// the next line just get the note that we need to start from
		// this note becomes the base not for the new chord
		// getScale("major", baseNote, romanNumeral)[romanNumeral - 1]

		return null;
	}

	public static Collection<String> getChords() {
		return chordMap.keySet();
	}

}
