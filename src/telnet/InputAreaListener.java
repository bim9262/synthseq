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
	private Tab tab = Tab.getInstance();

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
	}

	public void keyTyped(KeyEvent e) {
		switch ((int) e.getKeyChar()) {
		// tab key
			case 9 :
				tabCount++;
				e.consume();// Does not work
				inputArea.setText(inputArea.getText().replaceAll("\\t", ""));
				if (tabCount == 1) {
					String autoCompleted = tab .autoComplete(
							inputArea.getText(), inputArea.getCaretPosition());
					if (!autoCompleted.equals(inputArea.getText())) {
						inputArea.setText(autoCompleted);

					}
				} else if (tabCount == 2) {
					String results = tab.suggestions(
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
					tab.addDefinition(explode[i + 1]);
				}
			}
		}
	}
}