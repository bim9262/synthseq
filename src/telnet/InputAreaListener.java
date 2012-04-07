package telnet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import telnet.ScrollingTextPane.TextPane;

public class InputAreaListener extends KeyAdapter {

	protected TextPane inputArea;
	protected TextPane outputArea;
	protected PrintWriter socketInput;
	private int tabCount = 0;
	private UndoManager undoManager = new UndoManager();

	public InputAreaListener(ScrollingTextPane scrollingInputArea,
			TextPane outputArea, PrintWriter socketInput) {
		inputArea = scrollingInputArea.getTextPane();
		this.outputArea = outputArea;
		this.socketInput = socketInput;
		inputArea.getDocument().addUndoableEditListener(new UndoableEditListener(){

			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
					}
				});
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		// l key
		case 76:
			// if control is on
			if (e.getModifiersEx() == 128) {
				outputArea.setText("");
			}
			break;
		// e key
		case 69:
			e.consume();
			String text = inputArea.getSelectedText();
			// if control is on
			if (e.getModifiersEx() == 128 && text != null) {
				outputArea.append(text);
				socketInput.println(text.replaceAll("\\n", ""));
			}
			break;
		// z key
		case 90:
			if (e.getModifiersEx() == 128 && undoManager.canUndo())
				undoManager.undo();
			break;
		// y key
		case 89:
			if (e.getModifiersEx() == 128 && undoManager.canRedo())
				undoManager.redo();
			break;
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
				String text = inputArea.getText().replaceAll("\\s+$", "");
				outputArea.append(text);
				CommandRecall.getInstance().add(text);
				socketInput.println(text.replaceAll("\\n", ""));
				inputArea.setText("");
			}
			tabCount = 0;
			break;
		default:
			tabCount = 0;
			break;
		}
	}

}
