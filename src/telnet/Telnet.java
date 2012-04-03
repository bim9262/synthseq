package telnet;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Telnet {

	private JTextArea inputArea;
	private JTextArea outputArea;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	private GPanel g = new GPanel();
	private JFrame gui = new JFrame("Telnet");
	private CommandList cmds = new CommandList();

	public Telnet(String host, int port) {
		try {
			s = new Socket(InetAddress.getByName(host), port);
			s.setKeepAlive(true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread() {
			public void run() {
				try {
					while (true)
						outputArea.append(in.readLine() + "\n");
				} catch (IOException e) {
					System.out.println("Connection Closed.");
				}
			}
		}.start();

		gui.setSize(600, 500);
		gui.add(g);

		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				out.close();
				try {
					in.close();
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

		gui.setVisible(true);

		inputArea.requestFocusInWindow();

	}

	@SuppressWarnings("serial")
	private class GPanel extends JPanel {
		private int tabCount = 0;

		GPanel() {
			super(new GridBagLayout());
			inputArea = new JTextArea();

			inputArea.addKeyListener(new KeyAdapter() {

				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					// up key
					case 38:
						inputArea.setText(cmds.prev());
						break;

					// down key
					case 40:
						inputArea.setText(cmds.next());
						break;
					// l key
					case 76:
						if(e.getModifiersEx()==128);
						inputArea.setText("");
						outputArea.setText("");
						break;
					}

				}

				public void keyTyped(KeyEvent e) {
					switch ((int) e.getKeyChar()) {
					// tab key
					case 9:
						tabCount++;
						// TODO: add implementation for tabbing
						break;
					// enter key
					case 10:
						if (e.getModifiersEx() == 64) {
							String text = inputArea.getText().replaceAll(
									"\\s+$", "");
							outputArea.append(text + "\n");
							cmds.add(text);
							out.println(text);
							inputArea.setText("");
							tabCount = 0;
							outputArea.setCaretPosition(outputArea
									.getDocument().getLength());
						}
					}

				}
			});

			outputArea = new JTextArea();

			inputArea.setFocusTraversalKeysEnabled(false);

			outputArea.setKeymap(null);

			JScrollPane outputScrollPane = new JScrollPane(outputArea);
			JScrollPane inputScrollPane = new JScrollPane(inputArea);

			// Add Components to this panel.
			GridBagConstraints c = new GridBagConstraints();

			c.gridwidth = getWidth();
			c.weightx = 1;

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			this.add(inputScrollPane, c);

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.weighty = 4;
			this.add(outputScrollPane, c);

			inputArea.setLineWrap(true);
			inputArea.setWrapStyleWord(true);
			outputArea.setLineWrap(true);
			outputArea.setWrapStyleWord(true);

			setBackground(Color.BLACK);

			outputScrollPane.setBorder(new LineBorder(Color.BLACK));
			inputScrollPane.setBorder(new LineBorder(Color.BLACK));
			outputArea.setBorder(new LineBorder(Color.BLACK));
			inputArea.setBorder(new LineBorder(Color.RED));
			inputArea.setBackground(Color.BLACK);
			outputArea.setBackground(Color.BLACK);
			inputArea.setForeground(Color.GREEN);
			outputArea.setForeground(Color.GREEN);
			inputArea.setCaretColor(Color.RED);

		}
	}

}
