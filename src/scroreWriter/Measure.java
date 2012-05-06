package scroreWriter;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Measure extends JPanel implements MouseListener {

	final int startDrawLine = 5;
	final int numberOfLines = 13;

	private Clef clef = Clef.BASS;
	private Note center = clef.getCenter();
	private ArrayList<ArrayList<MusicalObject>> musicalObjects = new ArrayList<ArrayList<MusicalObject>>();

	Measure() {
		setBackground(Color.gray);
		setPreferredSize(new Dimension(400, 100));
		this.addMouseListener(this);

	}

	public boolean isFull() {
		double time = 0;
		for (ArrayList<MusicalObject> m : musicalObjects) {
			time += m.get(0).duration.getLength();
		}
		return time == 1.0;
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
		double clefWidth = clefHeight / clef.getImage().getHeight()
				* clef.getImage().getWidth();
		g.drawImage(clef.getImage(), 5, (int) top, (int) clefWidth,
				(int) clefHeight, null);
	}

	private Point getStaffPossition(MouseEvent e) {
		double noteHeight = getHeight() / numberOfLines;
		double center = noteHeight * (startDrawLine + 2);
		int noteYPos = (int) (2* (center - e.getY()) / noteHeight);
		return new Point(0, noteYPos);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point staffPos = getStaffPossition(e);
		// musicalObjects.get((int)staffPos.getX()).add(/*new MusicalObject()*/)
		System.out.println(staffPos.getX() + " , " + staffPos.getY() + ", "
				+ getCursor().getName());

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
