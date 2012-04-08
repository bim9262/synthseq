package telnet;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

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
				if (selectFile("Open")) {
					inputArea.setText("");
					try {
						Scanner f = new Scanner(new FileReader(file));
						while (f.hasNextLine()) {
							inputArea.append(f.nextLine());
						}
						inputArea.setText(inputArea.getText().substring(0,
								inputArea.getText().length() - 1));
					} catch (Exception e1) {
					}
				}
				break;
			}
		}
	}

	private void promptSave() {
		if (file == null) {

			if (selectFile("Save")) {

				if (file.exists()) {
					if (JOptionPane.showConfirmDialog(new Frame(),
							"Would you like to overwrite this file?",
							"Are You Sure?", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == 0) {
						save();
					} else {
						file = null;
					}
				} else {
					save();
				}
			}

		} else {
			save();
		}
	}

	private boolean selectFile(String dialogType) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(dialogType);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return isClojureFile(f);
			}

			@Override
			public String getDescription() {
				return "Clojure Files";
			}
		});

		boolean toReturn = fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION;
		if (toReturn) {
			file = fc.getSelectedFile();
			if (!isClojureFile(file)) {
				file = new File(file.toString() + ".clj");
			}
		}
		return toReturn;
	}

	private boolean isClojureFile(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return (ext == null ? false : ext.equals("clj"));
	}

	private void save() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(inputArea.getText());
			out.close();
		} catch (Exception e1) {
		}
	}
}
