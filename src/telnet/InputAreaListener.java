package telnet;


import common.TextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import javax.swing.undo.UndoManager;


public class InputAreaListener extends KeyAdapter {

	protected TextPane inputArea;
	private TextPane outputArea = Telnet.getOutputArea();
	protected PrintWriter socketInput = Telnet.getSocketInput();
	private int tabCount = 0;
	protected UndoManager undoManager;

	InputAreaListener(TextPane inputArea){
		this.inputArea = inputArea;
		undoManager = inputArea.getUndoManager();

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
					e.consume();
					eval(inputArea.getSelectedText());
					break;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		switch ((int) e.getKeyChar()) {
		// tab key
			case 9 :
				tabCount++;
				// TODO: Fix tabbing and add auto indentation
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