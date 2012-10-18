package scroreWriter;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Point;
import java.awt.image.BufferedImage;

abstract class MusicalObject {

	protected enum Duration {
		WHOLE(1.0), HALF(1.0 / 2), QUARTER(1.0 / 4), EIGHTH(1.0 / 8),
		SIXTEENTH(1.0 / 16), THIRTYSECOND(1.0 / 32);

		private final double length;

		private Duration(double length) {
			this.length = length;
		}

		public double getLength() {
			return length;
		}

	}

	protected boolean selected;
	protected Duration duration;
	protected BufferedImage image;

	MusicalObject() {
	}

	MusicalObject(String s) {
	}

	public final Duration getDuration(){
		return duration;
	}

	public final void setDuration(Duration duration){
		this.duration = duration;
	}

	public final String getName() {
		return duration.toString() + " " + getClass().getName();
	}

	public final String toString(){
		return getName();
	}

	public final BufferedImage getImage() {
		if (image == null) {
			try {
				image = ImageIO.read(new File("Images/"
						+ getClass().getSimpleName() + "s/"
						+ duration.toString() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	public abstract Point getHotspot();

}
