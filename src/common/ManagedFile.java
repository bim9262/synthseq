package common;

import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class ManagedFile {

	private final String ext;
	private TextPane inputArea;
	private File file;
	private boolean saved = true;
	private File defaultDirectory;
	private final String fileDescription;
	private Frame frame = new Frame();
	private Thread fileWatcher = new Thread() {
		public void run() {
			while (true) {
				String prevContents = null;
				String newContents = null;
				File oldFile = null;
				boolean saveStatus = saved;
				if (file != null) {
					prevContents = getFileContents();
					oldFile = file;
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				if (file != null && (newContents = getFileContents()) != null
						&& prevContents != null && saveStatus == saved
						&& !newContents.equals(prevContents)
						&& oldFile.equals(file)) {
					if (prompt("This file has been modified by another program."
							+ "\nWould you like to use the modified file?")) {
						open();
					} else {
						saved = false;
					}
				}
			}
		}
	};

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
		fileWatcher.start();
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
		saveDialog("Save As");
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

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public String toString() {
		return (file == null ? "File not saved" : file.getPath()) + " ";
	}

	public void close() {
		fileWatcher.interrupt();
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
		return JOptionPane.showConfirmDialog(frame, msg, "Warning!",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
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

	private String getFileContents() {
		String toReturn = "";
		try {
			Scanner in = new Scanner(new FileReader(file));
			while (in.hasNextLine()) {
				toReturn += in.nextLine() + (in.hasNextLine()?"\n":"");
			}
			in.close();
		} catch (Exception e1) {
		}
		return toReturn;
	}

	private void open() {
		inputArea.setText(getFileContents());
		inputArea.setCaretPosition(inputArea.getText().length());
		saved = true;
	}
}
