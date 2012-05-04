package scroreWriter;

public enum Accidental {

	SHARP(1),
	NUTRAL(0),
	FLAT(-1);

	private int inc;

	private Accidental(int inc){
		this.inc=inc;
	}

	public int getMIDIIncrement(){
		return inc;
	}

}
