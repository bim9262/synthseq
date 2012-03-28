package synthseq.synthesizer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualizer {
	private GPanel g = new GPanel();
	private JFrame gui = new JFrame();

	public Visualizer() {
		gui.setSize(400, 400);
		gui.add(g);
	}

	public void write(double d) {
		g.write(d);
	}

	public void show() {
		gui.setVisible(true);
	}

	public void hide() {
		gui.setVisible(false);
	}
}

@SuppressWarnings("serial")
class GPanel extends JPanel {
	int x = 0;
	int[] ys = new int[400];
	BufferedImage b = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);

	public GPanel() {
	}

	public void write(double d) {
		if (x < 400) {
			int y = (int) (d * 200 + 200);
			b.setRGB(x, ys[x], 0x33330000);
			b.setRGB(x, y, 0xFFFF0000);
			ys[x] = y;
		}
		x++;
		if (x >= 400) {
			x = 0;
			repaint();
		}
	}

	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, 400, 400);
		g.drawImage(b, 0, 0, null);
	}
}