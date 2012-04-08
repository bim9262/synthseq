package telnet;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;

import telnet.ScrollingTextPane.TextPane;

public class LeftInputAreaListener extends InputAreaListener {

	public LeftInputAreaListener(ScrollingTextPane scrollingInputArea,
			TextPane outputArea, PrintWriter socketInput) {
		super(scrollingInputArea, outputArea, socketInput);
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		// if shift is on
		if (e.getModifiersEx() == 64) {
			switch (e.getKeyCode()) {
			// up key
			case 38:
				inputArea.setText(CommandRecall.getInstance().prev());
				break;

			// down key
			case 40:
				String s = inputArea.getText();
				inputArea.setText(CommandRecall.getInstance().next());
				CommandRecall.getInstance().add(s);
				break;
			}
		}
	}
}