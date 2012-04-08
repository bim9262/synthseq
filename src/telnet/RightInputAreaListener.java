package telnet;

import java.awt.event.KeyEvent;
import java.io.PrintWriter;

import telnet.ScrollingTextPane.TextPane;

public class RightInputAreaListener extends InputAreaListener {

	public RightInputAreaListener(ScrollingTextPane scrollingInputArea,
			TextPane outputArea, PrintWriter socketInput) {
		super(scrollingInputArea, outputArea, socketInput);
	}

	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (e.getModifiersEx() == 128) {
			switch (e.getKeyCode()) {
			// s key
			case 83:

				break;
			// o key
			case 79:

				break;
			}
		}
	}
}
