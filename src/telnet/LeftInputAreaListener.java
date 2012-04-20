package telnet;

import java.awt.event.KeyEvent;

public class LeftInputAreaListener extends InputAreaListener {

	CommandRecall commandRecall = new CommandRecall();

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		// if shift is on
		if (e.getModifiersEx() == 64) {
			switch (e.getKeyCode()) {
			// up key
			case 38:
				inputArea.setText(commandRecall.prev());
				undoManager.discardAllEdits();
				break;

			// down key
			case 40:
				inputArea.setText(commandRecall.next());
				undoManager.discardAllEdits();
				break;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		super.keyTyped(e);
		if (e.getModifiersEx() == 64 && e.getKeyChar() == '\n') {
			commandRecall.add(inputArea.getText());
			inputArea.setText("");
			undoManager.discardAllEdits();
		}
	}
}
