package scroreWriter;

import java.awt.Point;

import java.awt.image.BufferedImage;

public interface CustomCursor {

	public String getName();

	public BufferedImage getImage();

	public Point getHotspot();

}
