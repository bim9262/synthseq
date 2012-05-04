package scroreWriter;

import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Measure extends JPanel {

	final int startDrawLine = 4;
	final int numberOfLines = 12;

	private Clef clef = Clef.Bass;
	private Note center = clef.getCenter();
	private ArrayList<Note> notes = new ArrayList<Note>();

	Measure() {
		setBackground(Color.gray);
		setPreferredSize(new Dimension(400, 300));
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				double noteHeight = getHeight() / numberOfLines;
				double center = noteHeight * (startDrawLine + 2);
				double noteYPos = (center - e.getY())/noteHeight;
				System.out.println(noteYPos);
			}
		});
	}



	public void paint(Graphics g) {
		super.paint(g);
		double noteHeight = getHeight() / numberOfLines;
		double h = noteHeight * startDrawLine;
		double top = h;
		for (int i = startDrawLine; i < startDrawLine + 5; i++) {
			g.drawLine(0, (int)h, getWidth(), (int)h);
			h += noteHeight;
		}
		double clefHeight = h - top - noteHeight;
		double clefWidth = clefHeight / clef.getImage().getHeight()
				* clef.getImage().getWidth();
		g.drawImage(clef.getImage(), 5, (int)top, (int)clefWidth, (int)clefHeight, null);
	}
}
