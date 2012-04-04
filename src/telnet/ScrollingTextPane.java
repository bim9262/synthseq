package telnet;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class ScrollingTextPane extends JScrollPane {
	
	private TextPane textPane;
	
	public ScrollingTextPane() {
		super();
		textPane = new TextPane();
		setViewportView(textPane);
	}
	
	public TextPane getTextPane(){
		return textPane;
	}
	
	public class TextPane extends JTextPane{	
	
	public TextPane(){
		super();		
		setBackground(Color.BLACK);
		setForeground(Color.GREEN);
		setCaretColor(Color.RED);
	}
	
	public synchronized void append (String s) {
		setText(getText() + s
				+ "\n");		
	}
	
	public synchronized void setText(String s){
		super.setText(s);
		setCaretPosition(getDocument().getLength());
	}
	}
}
