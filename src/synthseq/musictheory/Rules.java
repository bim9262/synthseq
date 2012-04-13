package synthseq.musictheory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Rules {

	private static byte[] noteSeq = { 0, 2, 3, 5, 7, 8, 10, 12 };

	public static Collection<Double> stringToFreqs(String string) {
		ArrayList<Double> v = new ArrayList<Double>();
		Scanner scn = new Scanner(string);
		while (scn.hasNext()) {
			int i = parseNote(scn.next());
			if (i != -1)
				v.add(noteToFreq(i));
		}
		return v;
	}

	public static int parseNote(String str) {
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

	public static double noteToFreq(int note) {
		return  440 * Math.pow(2, (note - 69) / 12.0);
	}

}
