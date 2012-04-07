package telnet;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import telnet.ScrollingTextPane.TextPane;

public class Telnet {

	private TextPane leftInputArea;
	private TextPane rightInputArea;
	private TextPane outputArea;
	private Socket s;
	private BufferedReader socketOutput;
	private PrintWriter socketInput;
	private JFrame gui = new JFrame("Telnet");

	public Telnet(String host, int port) {
		try {
			gui.setIconImage(javax.imageio.ImageIO.read(new File("icon.png")));
			s = new Socket(InetAddress.getByName(host), port);
			s.setKeepAlive(true);
			socketOutput = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			socketInput = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				try {
					while (true) {
						String line = socketOutput.readLine();
						outputArea.append(line);
					}
				} catch (IOException e) {
					System.out.println("Connection Closed.");
				}
			}
		}.start();

		gui.setSize(600, 500);

		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				socketInput.close();
				try {
					socketOutput.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					s.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		gui.add(new GPanel());

		gui.setVisible(true);
		
		leftInputArea.requestFocusInWindow();
	}

	@SuppressWarnings("serial")
	private class GPanel extends JPanel {

		GPanel() {
			super(new GridBagLayout());

			ScrollingTextPane outputScrollPane = new ScrollingTextPane();
			outputArea = outputScrollPane.getTextPane();

			ScrollingTextPane leftInputScrollPane = new ScrollingTextPane();
			leftInputArea = leftInputScrollPane.getTextPane();

			ScrollingTextPane rightInputScrollPane = new ScrollingTextPane();
			rightInputArea = rightInputScrollPane.getTextPane();

			leftInputArea.addKeyListener(new MainInputAreaListener(
					leftInputArea, outputArea, socketInput));

			leftInputArea.setFocusTraversalKeysEnabled(false);

			outputArea.setKeymap(null);

			// Add Components to this panel.
			GridBagConstraints c = new GridBagConstraints();

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 1;
			c.weighty = 1;
			c.weightx = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			this.add(leftInputScrollPane, c);

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.weighty = 4;
			c.weightx = 1;
			this.add(outputScrollPane, c);
			
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 1;
			c.gridy = 0;
			c.weighty = 5;
			c.weightx = 1;
			c.anchor = GridBagConstraints.EAST;
			this.add(rightInputScrollPane, c);

			setBackground(Color.BLACK);

		}
	}

}
