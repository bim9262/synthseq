package scroreWriter;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum Clef {

	TREBLE("G4"),
	ALTO("C4"),
	BASS("D3");

	private final Note center;
	private BufferedImage image;

	private Clef(String center){
		this.center = new Note(center);
		try {
			image = ImageIO.read(new File("Images/Clefs/" + toString() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Note getCenter(){
		return center;
	}

	public BufferedImage getImage(){
		return image;
	}

}
