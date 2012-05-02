package telnet;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import javax.swing.undo.UndoManager;

import telnet.ScrollingTextPane.TextPane;

public class InputAreaListener extends KeyAdapter {

	protected TextPane inputArea;
	private TextPane outputArea = Telnet.getOutputArea();
	protected PrintWriter socketInput = Telnet.getSocketInput();
	private int tabCount = 0;
	protected UndoManager undoManager;

	public void setInputArea(ScrollingTextPane scrollingInputArea) {
		inputArea = scrollingInputArea.getTextPane();
		undoManager = scrollingInputArea.getUndoManager();
	}

	public void keyPressed(KeyEvent e) {
		// if control is on
		if (e.getModifiersEx() == 128) {
			switch (e.getKeyCode()) {
			// l key
				case 76 :
					outputArea.setText("");
					break;
				// e key
				case 69 :
					eval(inputArea.getSelectedText());
					break;
				// z key
				case 90 :
					if (undoManager.canUndo())
						undoManager.undo();
					break;
				// y key
				case 89 :
					if (undoManager.canRedo())
						undoManager.redo();
					break;
				// f key
				case 70 :
					inputArea.promptFind();
					break;
			}
		}
		// if alt is on
		if (e.getModifiersEx() == 512) {
			switch (e.getKeyCode()) {
			// up key
				case 38 :
					inputArea.selectBlock(true);
					break;
				// down key
				case 40 :
					inputArea.selectBlock(false);
					break;
				// a key
				case 65 :
					// TODO: Select the entire code block.
					break;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		switch ((int) e.getKeyChar()) {
		// tab key
			case 9 :
				tabCount++;
				if (Telnet.getTab() != null) {
					inputArea
							.setText(inputArea.getText().replaceAll("\\t", ""));
					if (tabCount == 1) {
						String autoCompleted = Telnet.getTab().autoComplete(
								inputArea.getText(),
								inputArea.getCaretPosition());
						if (!autoCompleted.equals(inputArea.getText())) {
							inputArea.setText(autoCompleted);

						}
					} else if (tabCount == 2) {
						String results = Telnet.getTab().suggestions(
								inputArea.getText(),
								inputArea.getCaretPosition());
						outputArea.append(results);
					}
				}
				break;
			// enter key
			case 10 :
				// if shift is on
				if (e.getModifiersEx() == 64) {
					eval(inputArea.getText());
				}
				tabCount = 0;
				break;
			default :
				tabCount = 0;
				break;
		}
	}
	private void eval(String text) {
		if (text != null) {
			outputArea.append(text);
			socketInput.println(text.replaceAll("\\n", ""));
			String[] explode = text.split("[ )\n]");
			for (int i = 0; i < explode.length - 1; i++) {
				if (explode[i].startsWith("def", 1)) {
					Tab.addDefinition(explode[i + 1]);
				}
			}
		}
	}
}