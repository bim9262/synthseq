package telnet;

import java.awt.event.KeyAdapter;
import java.io.PrintWriter;

import telnet.ScrollingTextPane.TextPane;

public class InputAreaListener extends KeyAdapter {

	protected TextPane inputArea;
	protected TextPane outputArea;
	protected PrintWriter socketInput;

	public InputAreaListener(TextPane inputArea, TextPane outputArea,
			PrintWriter socketInput) {
		this.inputArea = inputArea;
		this.outputArea = outputArea;
		this.socketInput = socketInput;
	}

	}
