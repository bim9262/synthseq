package common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

@SuppressWarnings("serial")
public class ScrollPane extends JScrollPane {


	public ScrollPane(JComponent component) {
		super();
		setBackground(Color.BLACK);
		setBorder(new LineBorder(Color.RED));
		getVerticalScrollBar().setUI(new CoolScrollBarUI());
		getHorizontalScrollBar().setUI(new CoolScrollBarUI());
		makeCorner();
		setViewportView(component);
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
					case 1 :
						new Triangle(getWidth() / 2, 3, 2, getHeight() - 4,
								getWidth() - 3, getHeight() - 4).draw(g);
						break;
					case 5 :
						new Triangle(getWidth() / 2, getHeight() - 4, 2, 3,
								getWidth() - 3, 3).draw(g);
						break;
					case 7 :
						new Triangle(2, getHeight() / 2, getWidth() - 4, 3,
								getWidth() - 4, getHeight() - 4).draw(g);
						break;
					case 3 :
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
