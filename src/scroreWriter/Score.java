package scroreWriter;

import java.awt.Point;

import java.awt.Graphics;

import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionAdapter;

import java.awt.event.MouseMotionAdapter;

import java.awt.image.BufferedImage;

import scroreWriter.MusicalObject.Duration;

import java.awt.Cursor;

import java.awt.Toolkit;

import java.awt.GridBagLayout;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Score extends JPanel {


	Score() {
		setLayout(new GridBagLayout());
		add(new Measure());
		setBackground(Color.YELLOW);

		setCursor(new Note(Duration.THIRTYSECOND));
	}

	public void setCursor(MusicalObject m) {
		if (m == null) {
			setCursor(Cursor.getDefaultCursor());
		} else {
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					m.getImage(),
					m.getHotspot(), m.getName()));

		}
	}
}
