package scroreWriter.model;

import java.util.HashMap;

public class KeySignature {

	private HashMap<String, Accidental> accidentalMap = new HashMap<String, Accidental>();

	public KeySignature() {

		accidentalMap.put("A", Accidental.NUTRAL);
		accidentalMap.put("B", Accidental.NUTRAL);
		accidentalMap.put("C", Accidental.NUTRAL);
		accidentalMap.put("D", Accidental.NUTRAL);
		accidentalMap.put("E", Accidental.NUTRAL);
		accidentalMap.put("F", Accidental.NUTRAL);
		accidentalMap.put("G", Accidental.NUTRAL);

	}

	public void setAccidental(String note, Accidental accidental) {
		accidentalMap.remove(note);
		accidentalMap.put(note, accidental);
	}

	public Accidental getAccidental(String note) {
		return accidentalMap.get(note);
	}

}
