package common;

import static java.lang.Math.*;

import java.awt.Frame;

import javax.swing.undo.UndoManager;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class TextPane extends JTextPane
		implements
			CaretListener,
			UndoableEditListener {

	private UndoManager undoManager = new UndoManager();
	private Frame frame;

	private Vector<Object> highlights = new Vector<Object>();
	private DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(
			Color.RED);

	public TextPane(Frame frame) {
		super();
		this.frame= frame;
		setBackground(Color.BLACK);
		setForeground(Color.GREEN);
		setCaretColor(Color.RED);
		setBorder(new LineBorder(Color.BLACK));
		setFocusTraversalKeysEnabled(false);
		getDocument().addUndoableEditListener(this);
		addCaretListener(this);
		setSelectedTextColor(Color.RED);
		setSelectionColor(Color.GREEN);
		setFont(new Font("Courier New", Font.PLAIN, 12));
		setText("\n");
		setText(getText().substring(0, getText().length() - 1));
	}

	public synchronized void append(String s) {
		setText(getText() + s + "\n");
	}

	public synchronized void setText(String s) {
		super.setText(s);
		setCaretPosition(getDocument().getLength());
	}

	public void highlight(int p0, int p1) {
		try {
			highlights.add(getHighlighter().addHighlight(p0, p1,
					highlightPainter));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		undoManager.addEdit(e.getEdit());
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (getText() != null && getText().length() != 0) {
			clearHighlights();
			int dot = e.getDot();
			int mark = e.getMark();
			if (abs(dot - mark) == 1 || dot == mark) {
				int pos = max(dot, mark) - 1;
				int count = 0;
				char[][] bracketTypes = {{'(', ')'}, {'[', ']'}, {'{', '}'}};
				String toSearch = getText();
				here : for (char[] c : bracketTypes) {
					if (pos >= 0 && toSearch.charAt(pos) == c[1]) {
						for (int i = pos; i >= 0; i--) {
							if (toSearch.charAt(i) == c[1]) {
								count++;
							} else if (toSearch.charAt(i) == c[0]) {
								count--;
							}
							if (count == 0) {
								highlight(i, i + 1);
								if (dot == mark) {
									highlight(dot - 1, dot);
								}
								break here;
							}
						}
					}
				}
			}
		}
	}

	public void promptFind() {
		Object findObj = JOptionPane.showInputDialog(frame,
				"What would you like to find? ", "Find All",
				JOptionPane.QUESTION_MESSAGE, null, null, getSelectedText());
		if (findObj != null) {
			String find = findObj.toString();
			if (!find.equals("")) {
				if (getSelectedText() != null && getSelectedText().equals(find)) {
					clearHighlights();
				} else {
					getHighlighter().removeAllHighlights();
				}
				String toSearch = getText();
				int i = 0;
				while (i < toSearch.length() - find.length() + 1) {
					int newIndex = toSearch.indexOf(find, i);
					if (newIndex == -1) {
						break;
					} else {
						highlight(newIndex, newIndex + find.length());
						i = newIndex + find.length() - 1;
					}

				}
			}
		}
	}

	private void clearHighlights() {
		for (Object highlight : highlights) {
			getHighlighter().removeHighlight(highlight);
		}
	}

	public void selectBlock(boolean up) {
		int start = getSelectionStart();
		int end = getSelectionEnd();
		String toSearch = getText();
		if (up) {

		} else {

		}
		System.out.println("start: " + start + "\n  end: " + end);
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

}
