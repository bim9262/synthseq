package synthseq.musictheory;

import java.util.Collection;
import java.util.HashMap;

public class Scales {
	// Scale steps
		private static byte[] majorScale = { 2, 2, 1, 2, 2, 2, 1 };
		private static byte[] majorPentScale = { 2, 2, 3, 2, 3 };
		private static byte[] minorScale = { 2, 1, 2, 2, 1, 2, 2 };
		private static byte[] minorPentScale = { 3, 2, 2, 3, 2 };
		private static byte[] bluesScale = { 3, 2, 1, 1, 3, 2 };
		private static byte[] chromaticScale = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		private static byte[] wholeToneScale = { 2, 2, 2, 2, 2, 2 };
		private static byte[] phrygianDominantScale = { 1, 3, 1, 2, 1, 2, 2 };
		private static byte[] doubleHarmonicMajorScale = { 1, 3, 1, 2, 1, 3, 1 };
		private static HashMap<String,byte[]> scaleMap = new HashMap<String,byte[]>();

		static{
			scaleMap.put("major",majorScale);
			scaleMap.put("pentatonic major",majorPentScale);
			scaleMap.put("minor",minorScale);
			scaleMap.put("pentatonic minor",minorPentScale);
			scaleMap.put("blues",bluesScale);
			scaleMap.put("chromatic",chromaticScale);
			scaleMap.put("whole tone",wholeToneScale);
			scaleMap.put("phrygian dominant",phrygianDominantScale);
			scaleMap.put("double harmonic major",doubleHarmonicMajorScale);
		}

		public static int[] getScale(String scaleName, int baseNote, int count){
			byte[] scale = scaleMap.get(scaleName);
			int[] notes = new int[count];
			notes[0] = baseNote;
			for(int i = 1; i < count; i++){
				notes[i] = notes[i-1] + scale[(i-1)%scale.length];
			}
			return notes;
		}

		public static Collection<String> getScales(){
			return scaleMap.keySet();
		}
}
