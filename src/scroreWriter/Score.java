package scroreWriter;

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
		//setCursor(new Note(""));
	}

	public void setCursor(CustomCursor c) {
		if (c == null) {
			setCursor(Cursor.getDefaultCursor());
		} else {
			setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					c.getImage(), c.getHotspot(), c.getName()));
		}
	}

}
