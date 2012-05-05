package scroreWriter;

public enum Accidental {

	SHARP(1),
	NUTRAL(0),
	FLAT(-1);

	private int offset;

	private Accidental(int offset){
		this.offset=offset;
	}

	public int getMIDIOffset(){
		return offset;
	}

}
