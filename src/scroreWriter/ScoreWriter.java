package scroreWriter;

import common.TextPane;

import java.awt.Dimension;

import javax.swing.event.UndoableEditEvent;

import javax.swing.event.UndoableEditListener;

import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;

import common.ScrollPane;

import abc.ui.swing.JScoreComponent;

import abc.parser.TuneParser;

import abc.notation.Tune;

import common.ManagedFile;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class ScoreWriter {

	private static ManagedFile file = new ManagedFile("test.abc", "abc",
			"ABC Notaion File");

	public static void main (String[] args){
		new ScoreWriter();
	}

	public ScoreWriter(){
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}

		JFrame j = new JFrame();
		j.setSize(new Dimension(400, 500));
		final TextPane textPane = new TextPane(j);

		final JScoreComponent scoreUI =new JScoreComponent();

		textPane.getDocument().addUndoableEditListener(new UndoableEditListener(){
			public void undoableEditHappened(UndoableEditEvent arg0) {
				Tune tune = new TuneParser().parse(textPane.getText());
		        scoreUI.setTune(tune);
			}
        });

        file.setFrame(j);
        file.setInputSource(textPane);
        j.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;

		c.gridy = 1;
		c.anchor = GridBagConstraints.PAGE_END;
		j.add(new ScrollPane(scoreUI), c);

		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
        j.add(new ScrollPane(textPane), c);


        j.setVisible(true);
	}




}
