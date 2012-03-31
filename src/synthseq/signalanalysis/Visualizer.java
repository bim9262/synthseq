package synthseq.signalanalysis;

import java.awt.Graphics;

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
	public boolean isVisible(){
		return gui.isVisible();
	}

	public void show() {
		gui.setVisible(true);
	}

	public void hide() {
		gui.setVisible(false);
	}
	@SuppressWarnings("serial") 
	private class GPanel extends JPanel {
		int x = 0;
		int[] ys = new int[400];
		public GPanel() {
			super(true);
		}

		public void write(double d) {
			ys[x]=(int) (400 - (d*200+200));
			x++;
			if(x>=400){
				x = 0;
				repaint();
			}
		}

		public void paintComponent(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			for(int i = 0; i< ys.length-1;i++)
				g.drawLine(i,ys[i], i+1, ys[i+1]);
		}
	}
}