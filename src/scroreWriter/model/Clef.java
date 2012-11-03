package scroreWriter.model;

public enum Clef {

	TREBLE("G4"), ALTO("C4"), BASS("D3");

	private final Note center;

	private Clef(String center) {
		this.center = new Note(center);
	}

	public Note getCenter() {
		return center;
	}

}
