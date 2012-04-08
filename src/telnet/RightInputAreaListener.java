package telnet;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import telnet.ScrollingTextPane.TextPane;

public class RightInputAreaListener extends InputAreaListener {

	private File file;

	public RightInputAreaListener(ScrollingTextPane scrollingInputArea,
			TextPane outputArea, PrintWriter socketInput) {
		super(scrollingInputArea, outputArea, socketInput);
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (e.getModifiersEx() == 128) {
			switch (e.getKeyCode()) {
			// s key
			case 83:
				promptSave();
				break;
			// o key
			case 79:

				break;
			}
		}
	}

	private void promptSave() {
		if (file == null) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

			int rval = fc.showSaveDialog(fc);

			if (rval == JFileChooser.APPROVE_OPTION) {

				file = fc.getSelectedFile();
				if (file.exists()) {
					if(JOptionPane.showConfirmDialog(new Frame(),
						    "Would you like to overwrite this file?",
						    "Are You Sure?",
						    JOptionPane.YES_NO_OPTION)==0){
						save();
					}
					else {
						file=null;
					}
				}
				else {
				save();
				}
			}

		} else {
			save();
		}
	}

	private void save() {
		try {
			// Create file
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(inputArea.getText());
			// Close the output stream
			out.close();
		} catch (Exception e1) {// Catch exception if any
			System.err.println("Error: " + e1.getMessage());
		}
	}
}
