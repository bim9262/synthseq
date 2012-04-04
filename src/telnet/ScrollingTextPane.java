package telnet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ScrollBarUI;
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
						 thumbBounds.width / 2, thumbBounds.height + thumbBounds.y- 10);
			}

		}
	}
}
