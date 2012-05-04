package scroreWriter;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Note implements CustomCursor {

	private boolean selected;
	private char note;
	private int octave;
	private Duration duration;
	private Accidental accidental = Accidental.NUTRAL;
	private BufferedImage image;

	Note(String s) {
	}

	@Override
	public BufferedImage getImage() {
		try {
			image = ImageIO.read(new File("Images/Notes/" + duration.toString()
					+ ".gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public String getName() {
		return duration.toString() + " Note";
	}

	@Override
	public Point getHotspot() {
		return new Point(0, 0);
	}

}
