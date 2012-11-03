package scroreWriter.viewer;

import javax.swing.JPanel;
import javax.swing.JTextField;

import scroreWriter.model.Measure;

@SuppressWarnings("serial")
public class ScoreViewer extends JPanel {

	public ScoreViewer(){
		add(new JTextField("This is a test title"));
		add(new JTextField("This is a test composer"));
		add(new JTextField("This is a test date"));

		add(new MeasureViewer(new Measure()));
	}
	
}
