package scroreWriter;

public class Note {

	public static enum length {
		DOUBLE(2.0),
		FULL(1.0),
		HALF(1.0 / 2),
		QUARTER(1.0 / 4),
		EIGHTH(1.0 / 8),
		SIXTEENTH(1.0 / 16),
		THIRTYSECOND(1.0 / 32);

		private final double length;

		private length(double length) {
			this.length = length;
		}

		public double getLength(){
			return length;
		}
	}

	private boolean selected;
	private char note;
	private int octave;
	private length duration;
	private Accidental accidental = Accidental.NUTRAL;

	Note(String s) {
	}

}
