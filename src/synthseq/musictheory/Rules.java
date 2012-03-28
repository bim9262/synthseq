package synthseq.musictheory;

import java.util.Scanner;
import java.util.Vector;

import synthseq.playables.Playable;

public class Rules {
	
	private static byte[] noteSeq = { 0, 2, 3, 5, 7, 8, 10, 12 };
	
	public static Playable[] stringToPlayable(Class<?> pClass, String str) {
		return freqToPlayable(pClass, noteToFreq(stringToNote(str)));
	}

	public static int[] stringToNote(String... str) {
		Vector<Integer> v = new Vector<Integer>();
		for (String s : str) {
			Scanner scn = new Scanner(s);
			while (scn.hasNext()) {
				int i = noteParser(scn.next());
				if (i != -1)
					v.add(i);
			}
		}
		int[] ret = new int[v.size()];
		for (int i = 0; i < v.size(); i++)
			ret[i] = v.get(i);
		return ret;
	}

	private static int noteParser(String str) {
		if (!str.matches("[a-gA-G][s#b]?-?[0-9]?"))
			return -1;
		int baseNote = noteSeq[(Character.toUpperCase(str.charAt(0)) - 65)] + 69;
		str = str.substring(1);
		if (str.matches("[s#b]-?[0-9]?")) {
			if (str.charAt(0) == 's' || str.charAt(0) == '#')
				baseNote++;
			else if (str.charAt(0) == 'b')
				baseNote--;
			str = str.substring(1);
		}
		if (str.matches("[0-9]"))
			baseNote += 12 * (Integer.parseInt(str) - 4);
		return baseNote;
	}

	public static Playable[] noteToPlayable(Class<?> pClass, int... notes) {
		return freqToPlayable(pClass, noteToFreq(notes));
	}

	public static double[] noteToFreq(int... notes) {
		double[] freqs = new double[notes.length];
		for (int i = 0; i < notes.length; i++)
			freqs[i] = 440 * Math.pow(2, (notes[i] - 69) / 12.0);
		return freqs;
	}

	public static Playable[] freqToPlayable(Class<?> pClass, double... notes) {
		Playable[] p = new Playable[notes.length];
		try {
			for (int i = 0; i < p.length; i++) {
				p[i] = (Playable) pClass.getConstructor(
						new Class[] { double.class }).newInstance(notes[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
}
