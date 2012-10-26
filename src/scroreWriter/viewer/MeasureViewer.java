package scroreWriter.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import scroreWriter.model.Clef;
import scroreWriter.model.Measure;
import scroreWriter.model.Note;

@SuppressWarnings("serial")
public class MeasureViewer extends JPanel {

	final int startDrawLine = 5;
	final int numberOfLines = 13;

	private Clef clef = Clef.BASS;
	private Note center = clef.getCenter();
	private Measure measure;

	MeasureViewer(Measure measure) {
		setBackground(Color.gray);
		setPreferredSize(new Dimension(400, 100));
		this.measure = measure;

	}

	public void paint(Graphics g) {
		super.paint(g);
		double noteHeight = getHeight() / numberOfLines;
		double h = noteHeight * startDrawLine;
		double top = h;
		for (int i = startDrawLine; i < startDrawLine + 5; i++) {
			g.drawLine(0, (int) h, getWidth(), (int) h);
			h += noteHeight;
		}
		double clefHeight = h - top - noteHeight;
		/*double clefWidth = clefHeight / clef.getImage().getHeight()
				* clef.getImage().getWidth();
		g.drawImage(clef.getImage(), 5, (int) top, (int) clefWidth,
				(int) clefHeight, null);*/
	}

	/*private Point getStaffPossition(MouseEvent e) {
		double noteHeight = getHeight() / numberOfLines;
		double center = noteHeight * (startDrawLine + 2);
		int yPos = (int) (2 * (center - e.getY()) / noteHeight);
		int xPos = 0;
		int pixelThreshold = 5;
		return new Point(xPos, yPos);
	}*/

}
