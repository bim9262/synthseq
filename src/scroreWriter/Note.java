package scroreWriter;

import java.awt.Graphics2D;

import java.awt.Graphics;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Note extends MusicalObject {

	private char note;
	private int octave;
	private Accidental accidental = Accidental.NUTRAL;
	private BufferedImage image;

	Note(Duration duration) {
		this.duration = duration;
	}

	Note(String s) {

	}

	@Override
	public BufferedImage getImage() {
		if (image == null) {
			try {
				image = ImageIO.read(new File("Images/Notes/"
						+ duration.toString() + ".gif"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	@Override
	public String getName() {
		return duration.toString() + " Note";
	}

	@Override
	public Point getHotspot() {
		return new Point(3, 25);
	}

}
