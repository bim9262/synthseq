package telnet;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import telnet.ScrollingTextPane.TextPane;

public class RightInputAreaListener extends InputAreaListener {

	ManagedFile file;

	public RightInputAreaListener(ScrollingTextPane scrollingInputArea,
			TextPane outputArea, PrintWriter socketInput, ManagedFile file) {
		super(scrollingInputArea, outputArea, socketInput);
		file.setInputSource(inputArea);
		this.file = file;
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		// ctrl key
		if (e.getModifiersEx() == 128) {
			switch (e.getKeyCode()) {
			// s key
				case 83 :
					file.promptSave();
					break;
				// o key
				case 79 :
					if (file.promptOpen()) {
						undoManager.discardAllEdits();
					}
					break;
				// n key
				case 78 :
					file.promptNew();
					break;
			}

		}
		// shift + ctrl
		if (e.getModifiersEx() == 192) {
			switch (e.getKeyCode()) {
			// s key
				case 83 :
					file.promptSaveAs();
					break;
			}

		}
	}
}
