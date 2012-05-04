package scroreWriter;

import java.awt.Dimension;

import java.awt.Color;

import java.awt.Graphics;

import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Measure extends JPanel {

	final int noteHeight = 9;
	final int startDrawLine = 4;
	final int numberOfLines = 12;

	private Clef clef = Clef.Bass;
	private ArrayList<Note> notes = new ArrayList<Note>();

	Measure() {
		setBackground(Color.gray);
		this.setPreferredSize(new Dimension(400, 300));
	}

	public void paint(Graphics g) {
		super.paint(g);
		int j = getHeight() / numberOfLines;
		int h = j * startDrawLine;
		int top = h;
		for (int i = startDrawLine; i < startDrawLine + 5; i++) {
			g.drawLine(0, h, getWidth(), h);
			h += j;
		}
		int clefHeight = h - top - j;
		int clefWidth = (int)((double)clefHeight / clef.getImage().getHeight()
				* clef.getImage().getWidth());
		System.out.println(clefHeight + ", " + clefWidth);
		g.drawImage(clef.getImage(), 5, top, clefWidth, clefHeight, null);
	}
}
