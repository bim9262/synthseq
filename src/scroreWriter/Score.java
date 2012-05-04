package scroreWriter;

import java.awt.GridBagLayout;

import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Score extends JPanel {

	Score() {
		setLayout(new GridBagLayout());
		add(new Measure());
		setBackground(Color.YELLOW);
	}

}
