package telnet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import javax.swing.undo.UndoManager;

import telnet.ScrollingTextPane.TextPane;

public class InputAreaListener extends KeyAdapter {

	protected TextPane inputArea;
	private TextPane outputArea;
	protected PrintWriter socketInput;
	private int tabCount = 0;
	protected UndoManager undoManager;

	public InputAreaListener(ScrollingTextPane scrollingInputArea,
			TextPane outputArea, PrintWriter socketInput) {
		inputArea = scrollingInputArea.getTextPane();
		undoManager = scrollingInputArea.getUndoManager();
		this.outputArea = outputArea;
		this.socketInput = socketInput;
	}

	public void keyPressed(KeyEvent e) {
		// if control is on
		if (e.getModifiersEx() == 128) {
			switch (e.getKeyCode()) {
			// l key
			case 76:
				outputArea.setText("");
				break;
			// e key
			case 69:
				e.consume();
				String text = inputArea.getSelectedText();
				if (text != null) {
					outputArea.append(text);
					socketInput.println(text.replaceAll("\\n", ""));
				}
				break;
			// z key
			case 90:
				if (undoManager.canUndo())
					undoManager.undo();
				break;
			// y key
			case 89:
				if (undoManager.canRedo())
					undoManager.redo();
				break;
			}
		}

	}

	public void keyTyped(KeyEvent e) {
		switch ((int) e.getKeyChar()) {
		// tab key
		case 9:
			tabCount++;
			e.consume();// Does not work
			inputArea.setText(inputArea.getText().replaceAll("\\t", ""));
			if (tabCount == 1) {
				String autoCompleted = Tab.getInstance().autoComplete(
						inputArea.getText(), inputArea.getCaretPosition());
				if (!autoCompleted.equals(inputArea.getText())) {
					inputArea.setText(autoCompleted);

				}
			} else if (tabCount == 2) {
				String results = Tab.getInstance().suggestions(
						inputArea.getText(), inputArea.getCaretPosition());
				if (results.startsWith("doc ", 1)) {
					outputArea.append(results);
					socketInput.println(results);
				} else {
					outputArea.append(results);
				}
			}
			break;
		// enter key
		case 10:
			// if shift is on
			if (e.getModifiersEx() == 64) {
				String text = inputArea.getText().replaceAll("\\n", "");
				outputArea.append(text);
				socketInput.println(text);
			}
			tabCount = 0;
			break;
		default:
			tabCount = 0;
			break;
		}
	}

}
