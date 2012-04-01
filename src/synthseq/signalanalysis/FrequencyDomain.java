package synthseq.signalanalysis;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import synthseq.playables.readables.ReadableSound;

public class FrequencyDomain {
	private GPanel g = new GPanel();
	private JFrame gui = new JFrame();
	private double[][] frequencyDomain;
	private double xMax, yMax;
	private double[] buffer;
	private int bufferLoc = 0;

	public FrequencyDomain(ReadableSound r, double pow2) {
		buffer = new double[(int) (Math.pow(2, pow2))];
		if (r != null) {
			r.start();
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = r.read();
			}
			r.stop();
		}
		write();
		gui.setSize(400, 400);
		gui.add(g);
	}

	public FrequencyDomain(double pow2) {
		this(null, pow2);
	}

	public void show() {
		gui.setVisible(true);
	}

	public void hide() {
		gui.setVisible(false);
	}

	public boolean isVisible() {
		return gui.isVisible();
	}

	public void write(double d) {
		buffer[bufferLoc++] = d;
		if (bufferLoc >= buffer.length - 1) {
			write();
			bufferLoc = 0;
		}
	}

	public void write() {
		frequencyDomain = FFT.FrequencyDomain(buffer);
		xMax = frequencyDomain[0][frequencyDomain[0].length - 1];
		yMax = frequencyDomain[1][0];
		for (int i = 1; i < frequencyDomain[1].length; i++) {
			if (frequencyDomain[1][i] > yMax) {
				yMax = frequencyDomain[1][i];
			}
		}
		if(yMax <= 0.0000000001)
			yMax = 1;
		g.repaint();
	}

	@SuppressWarnings("serial")
	private class GPanel extends JPanel {
		public GPanel() {
			super(true);
		}

		public void paintComponent(Graphics g) {
			g.setColor(new Color(0x01000000));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0xffff0000));
			int[] xLocs = new int[frequencyDomain[0].length+1];
			int[] yLocs = new int[frequencyDomain[0].length+1];
			xLocs[0]=0;
			yLocs[0]=getHeight();
			for (int i = 0; i < frequencyDomain[0].length; i++) {
				xLocs[i+1] = (int) (getWidth() * (frequencyDomain[0][i] / xMax));
				yLocs[i+1] = (int) (getHeight() - getHeight()
						* (frequencyDomain[1][i] / yMax));
			}

			g.fillPolygon(xLocs, yLocs, xLocs.length);
		}
	}
}
