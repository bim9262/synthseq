package scroreWriter;

import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.File;

public enum Clef {

	Treble("G4", "Images/Clefs/Treble.png"),
	Alto("C4", "Images/Clefs/Alto.png"),
	Bass("D3", "Images/Clefs/Bass.png");

	private Note center;
	private BufferedImage image;

	Clef(String center, String imageLocation){
		this.center = new Note(center);
		try {
			image = ImageIO.read(new File (imageLocation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
