package synthseq.signalanalysis;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import synthseq.playables.readables.ReadableSound;

public class FrequencyDomain {
	private GPanel g = new GPanel();
	private JFrame gui = new JFrame();
	private double[][] frequencyDomain;
	private double xMax, yMax;

	public FrequencyDomain(ReadableSound r, double pow2) {
		double[] buffer = new double[(int) (Math.pow(2,pow2))];
		frequencyDomain = new double[2][buffer.length / 2];
		r.start();
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = r.read();
		}
		r.stop();
		frequencyDomain = FFT.FrequencyDomain(buffer);
		xMax = frequencyDomain[0][frequencyDomain[0].length-1];
		yMax = frequencyDomain[1][0];
		int index = 0;
		for (int i = 1; i < frequencyDomain[1].length; i++) {
			if (frequencyDomain[1][i] > yMax) {
				yMax = frequencyDomain[1][i];
				index = i;
			}
		}
		gui.setSize(400, 400);
		gui.add(g);
		gui.setVisible(true);
	}

	@SuppressWarnings("serial")
	private class GPanel extends JPanel {
		public GPanel() {
			super(true);
		}

		public void paintComponent(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			for (int i = 0; i < frequencyDomain[0].length - 1; i++)
				g.drawLine((int) (getWidth() * (frequencyDomain[0][i] / xMax)),
						(int) (getHeight() - getHeight()
								* (frequencyDomain[1][i] / yMax)),
						(int) (getWidth() * (frequencyDomain[0][i+1] / xMax)),
						(int) (getHeight() - getHeight()
								* (frequencyDomain[1][i+1] / yMax)));
		}
	}
}
