package synthseq.musictheory;

import static synthseq.musictheory.Scales.getScale;

import java.util.Collection;

import java.util.HashMap;

public class Chords {

	// Scale steps
	private static byte[] majorChord = { 4, 3 };
	private static HashMap<String, byte[]> chordMap = new HashMap<String, byte[]>();
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
	static {
		chordMap.put("M", majorChord);
	}

	private int[] getRomanNumeral(int baseNote, int romanNumeral, boolean major) {
		return getRomanNumeral(baseNote, romanNumeral, major, 3);
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

	public static void main(String[] args) {
		boolean major = true;
		int baseNote = 0;
		int length = 5;
		System.out.print("M Scale: ");
		for (int i : getScale("major", baseNote, 2 * length - 1)) {
			System.out.print(i + ", ");
		}
		System.out.print("\nm Scale: ");
		for (int i : getScale("minor", baseNote, 2 * length - 1)) {
			System.out.print(i + ", ");
		}

		System.out.print("\n  Chord: ");
		for (int i : new Chords().getRomanNumeral(baseNote, 3, major, 4)) {
			System.out.print(i + ",    ");
		}

	}

}
