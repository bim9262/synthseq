package telnet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import telnet.ScrollingTextPane.TextPane;

public class ManagedFile {

	private final String ext;
	private TextPane inputArea;
	private File file;
	private boolean saved = true;
	private File defaultDirectory;
	private final String fileDescription;

	public ManagedFile(String defaultFile, String ext, String fileDescription) {
		this.ext = ext.replace(".", "");
		this.fileDescription = fileDescription;
		defaultDirectory = new File(defaultFile);
		if (!defaultDirectory.isDirectory()) {
			if (isFileType(defaultDirectory)) {
				file = defaultDirectory;
			}
			defaultDirectory = defaultDirectory.getParentFile();
		}
	}

	public void promptNew() {
		promptSave("Would you like to save before starting a new file?");
		file = null;
		saved = true;
		inputArea.setText("");

	}

	public boolean promptOpen() {
		promptSave("Would you like to save before opening a file?");
		if (selectFile("Open")) {
			open();
			return true;
		}
		return false;
	}

	public void promptSave() {
		promptSave(null);
	}

	public void promptSave(String prompt) {
		if (!saved && (prompt == null || prompt(prompt))) {
			if (file == null) {
				saveDialog("Save");
			} else {
				save();
			}
		}
	}

	public void promptSaveAs() {
		saveDialog("SaveAs");
	}

	private void saveDialog(String title) {
		File tempFile = file;
		if (selectFile(title)) {
			if (file.exists()) {
				if (prompt("Would you like to overwrite this file?")) {
					save();
				} else {
					file = tempFile;
				}
			} else {
				save();
			}
		} else {
			save();
		}
	}

	private boolean prompt(String msg) {
		return JOptionPane.showConfirmDialog(Telnet.getFrame(), msg,
				"Are You Sure?", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE) == 0;
	}

	private boolean selectFile(String dialogType) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(dialogType);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(getCurrentDirectory());
		fc.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return isFileType(f);
			}

			@Override
			public String getDescription() {
				return fileDescription + " (*." + ext + ")";
			}
		});

		boolean toReturn = fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION;
		if (toReturn) {
			file = fc.getSelectedFile();
			if (!isFileType(file)) {
				file = new File(file.toString() + "." + ext);
			}
		}
		return toReturn;
	}

	private File getCurrentDirectory() {
		return (file != null ? file.getParentFile() : defaultDirectory);
	}

	private boolean isFileType(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return (ext == null ? false : ext.equals(this.ext));
	}

	private void save() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(inputArea.getText());
			out.close();
			saved = true;
		} catch (Exception e1) {
		}
	}

	private void open() {
		inputArea.setText("");
		try {
			Scanner f = new Scanner(new FileReader(file));
			while (f.hasNextLine()) {
				inputArea.append(f.nextLine());
			}
			inputArea.setText(inputArea.getText().substring(0,
					inputArea.getText().length() - 1));
			saved = true;
		} catch (Exception e1) {
		}
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void setInputSource(TextPane inputArea) {
		this.inputArea = inputArea;
		if (file != null) {
			open();
		}
	}

	public String toString() {
		return (file == null ? "File not saved" : file.getPath()) + " ";
	}

}
