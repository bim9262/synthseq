package telnet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.View;
import javax.swing.undo.UndoManager;
import static java.lang.Math.*;

@SuppressWarnings("serial")
public class ScrollingTextPane extends JScrollPane {

	private TextPane textPane;
	private UndoManager undoManager = new UndoManager();

	public ScrollingTextPane() {
		super();
		textPane = new TextPane();
		setBackground(Color.BLACK);
		setBorder(new LineBorder(Color.RED));
		getVerticalScrollBar().setUI(new CoolScrollBarUI());
		getHorizontalScrollBar().setUI(new CoolScrollBarUI());
		makeCorner();
		setViewportView(textPane);
	}

	public void addKeyListener(KeyListener keyListener) {
		textPane.addKeyListener(keyListener);
	}

	private void makeCorner() {
		JTextField corner = new JTextField();
		corner.setBackground(Color.BLACK);
		corner.setForeground(Color.GREEN);
		corner.setBorder(new LineBorder(Color.BLACK));
		corner.setText(" X");
		corner.setFocusable(false);
		corner.setEditable(false);
		setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, corner);
	}

	public TextPane getTextPane() {
		return textPane;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public class TextPane extends JTextPane implements CaretListener,
			UndoableEditListener {

		private Object highlight;

		public TextPane() {
			super();
			setBackground(Color.BLACK);
			setForeground(Color.GREEN);
			setCaretColor(Color.RED);
			setBorder(new LineBorder(Color.BLACK));
			setFocusTraversalKeysEnabled(false);
			getDocument().addUndoableEditListener(this);
			addCaretListener(this);
			// setHighlighter(new CoolHighlighter());
		}

		public synchronized void append(String s) {
			setText(getText() + s + "\n");
		}

		public synchronized void setText(String s) {
			super.setText(s);
			setCaretPosition(getDocument().getLength());
		}

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			undoManager.addEdit(e.getEdit());
		}

		@Override
		public void caretUpdate(CaretEvent e) {
			if (getText() != null && getText().length() != 0) {
				Highlighter highlighter = getHighlighter();
				if (highlight!=null) {
					highlighter.removeHighlight(highlight);
				}
				int dot = e.getDot();
				int mark = e.getMark();
				if (abs(dot - mark) == 1 || dot == mark) {
					int pos = max(dot, mark) - 1;
					int parCount = 0;
					if (pos >= 0 && getText().charAt(pos) == ')') {
						for (int i = pos; i >= 0; i--) {
							if (getText().charAt(i) == ')') {
								parCount++;
							} else if (getText().charAt(i) == '(') {
								parCount--;
							}
							if (parCount == 0) {
								try {
									highlight = highlighter.addHighlight(i, i + 1,
											new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
								break;
							}
						}
					}
				}
			}
		}
	}

	

	private class CoolScrollBarUI extends BasicScrollBarUI {

		private boolean horizontal = false;
		private Color thumbCoreColor;

		protected void configureScrollBarColors() {
			thumbColor = Color.BLACK;
			trackColor = thumbColor;
			thumbHighlightColor = Color.RED;
			thumbCoreColor = Color.GREEN;
		}

		protected void paintThumb(Graphics g, JComponent c,
				Rectangle thumbBounds) {
			super.paintThumb(g, c, thumbBounds);
			if (horizontal) {
				if (thumbBounds.width > 20) {
					g.setColor(thumbCoreColor);
					g.drawLine(thumbBounds.x + 10, thumbBounds.height / 2,
							thumbBounds.width + thumbBounds.x - 10,
							thumbBounds.height / 2);
				}
			} else {
				if (thumbBounds.height > 20) {
					g.setColor(thumbCoreColor);
					g.drawLine(thumbBounds.width / 2, thumbBounds.y + 10,
							thumbBounds.width / 2, thumbBounds.height
									+ thumbBounds.y - 10);
				}
			}

		}

		protected JButton createDecreaseButton(int orientation) {
			return new ArrowButton(orientation);

		}

		protected JButton createIncreaseButton(int orientation) {
			return new ArrowButton(orientation);
		}

		private class ArrowButton extends JButton {

			private int orientation;

			ArrowButton(int orientation) {
				setPreferredSize(new Dimension(18, 18));
				this.orientation = orientation;
				horizontal = (orientation == 3 || orientation == 7);
			}

			public void paint(Graphics g) {
				g.setColor(thumbColor);
				g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
				g.setColor(thumbHighlightColor);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
				g.setColor(thumbCoreColor);
				switch (orientation) {
				case 1:
					new Triangle(getWidth() / 2, 3, 2, getHeight() - 4,
							getWidth() - 3, getHeight() - 4).draw(g);
					break;
				case 5:
					new Triangle(getWidth() / 2, getHeight() - 4, 2, 3,
							getWidth() - 3, 3).draw(g);
					break;
				case 7:
					new Triangle(2, getHeight() / 2, getWidth() - 4, 3,
							getWidth() - 4, getHeight() - 4).draw(g);
					break;
				case 3:
					new Triangle(getWidth() - 4, getHeight() / 2, 2, 3, 2,
							getHeight() - 4).draw(g);
					break;
				}
			}

			private class Triangle {
				private int[] x = new int[3];
				private int[] y = new int[3];

				public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
					x[0] = x1;
					x[1] = x2;
					x[2] = x3;
					y[0] = y1;
					y[1] = y2;
					y[2] = y3;
				}

				public void draw(Graphics g) {
					g.drawPolygon(x, y, 3);
				}
			}
		}
	}
}
