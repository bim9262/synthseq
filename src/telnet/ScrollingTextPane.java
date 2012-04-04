package telnet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

@SuppressWarnings("serial")
public class ScrollingTextPane extends JScrollPane {

	private TextPane textPane;

	public ScrollingTextPane() {
		super();
		textPane = new TextPane();
		setBorder(new LineBorder(Color.BLACK));
		getVerticalScrollBar().setUI(new CoolScrollBarUI());
		setViewportView(textPane);
	}

	public TextPane getTextPane() {
		return textPane;
	}

	public class TextPane extends JTextPane {

		public TextPane() {
			super();
			setBackground(Color.BLACK);
			setForeground(Color.GREEN);
			setCaretColor(Color.RED);
		}

		public synchronized void append(String s) {
			setText(getText() + s + "\n");
		}

		public synchronized void setText(String s) {
			super.setText(s);
			setCaretPosition(getDocument().getLength());
		}
	}

	public class CoolScrollBarUI extends BasicScrollBarUI {

		private Color thumbCoreColor;

		protected void configureScrollBarColors() {
			trackColor = Color.BLACK;
			thumbColor = Color.BLACK;
			thumbHighlightColor = Color.RED;
			thumbCoreColor = Color.GREEN;
		}

		protected void paintThumb(Graphics g, JComponent c,
				Rectangle thumbBounds) {
			super.paintThumb(g, c, thumbBounds);
			if (thumbBounds.height > 20) {
				g.setColor(thumbCoreColor);
				g.drawLine(thumbBounds.width / 2, thumbBounds.y + 10,
						thumbBounds.width / 2, thumbBounds.height
								+ thumbBounds.y - 10);
			}

		}

		protected JButton createDecreaseButton(int orientation) {
			// orientation = 1
			return new ArrowButton(orientation);

		}

		protected JButton createIncreaseButton(int orientation) {
			// orientation = 5
			return new ArrowButton(orientation);
		}

		private class ArrowButton extends JButton {
			Boolean up;

			ArrowButton(int orientation) {
				setPreferredSize(new Dimension(14, 14));
				up = orientation == 1;
			}

			public void paint(Graphics g) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, getWidth()-1, getHeight()-1);
				g.setColor(Color.RED);
				g.drawRect(0, 0, getWidth()-1, getHeight()-1);
				g.setColor(Color.GREEN);
				if(up){
					new Triangle(getWidth()/2, 3, 2, getHeight()-4, getWidth()-3, getHeight()-4).draw(g);
				}
				else{
					new Triangle(getWidth()/2, getHeight()-4, 2, 3, getWidth()-3, 3).draw(g);
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
