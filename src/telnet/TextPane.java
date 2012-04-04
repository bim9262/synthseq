package telnet;

import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class TextPane extends JTextPane {
	public synchronized void append (String s) {
		setText(getText() + s
				+ "\n");
		
	}
	
	public synchronized void setText(String s){
		super.setText(s);
		setCaretPosition(getDocument().getLength());
	}
}
