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

public class Telnet {

	private JTextField textField;
	private JTextArea textArea;
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
						textArea.append(in.readLine() + "\n");
				} catch (IOException e) {
					System.out.println("Connection Closed.");
				}
			}
		}.start();

		gui.setSize(600, 400);
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
			}
		});

		gui.setVisible(true);

		textField.requestFocusInWindow();

	}

	@SuppressWarnings("serial")
	private class GPanel extends JPanel implements ActionListener {
		private int tabCount = 0;
		GPanel() {
			super(new GridBagLayout());
			textField = new JTextField();

			textField.addActionListener(this);
			textField.addKeyListener(new KeyAdapter() {
				
				
				public void keyPressed(KeyEvent e) {
					switch (e.getKeyCode()) {
					// up key
					case 38:
						textField.setText(cmds.prev());
						break;

					// down key
					case 40:
						textField.setText(cmds.next());
						break;
					}

				}

				public void keyTyped(KeyEvent e) {
					switch (e.getKeyCode()) {
					// tab key
					case 9:
						tabCount++;
						//TODO: add implementation for tabbing
						break;
					}
					
				}
			});

			textArea = new JTextArea();

			textField.setFocusTraversalKeysEnabled(false);

			textArea.setKeymap(null);

			JScrollPane scrollPane = new JScrollPane(textArea);

			// Add Components to this panel.
			GridBagConstraints c = new GridBagConstraints();

			c.gridwidth = getWidth();
			c.weightx = 1;

			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			this.add(textField, c);

			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0;
			c.weighty = 1;
			this.add(scrollPane, c);
			
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);		
			
			textField.setBackground(Color.BLACK);
			textArea.setBackground(Color.BLACK);
			textField.setForeground(Color.GREEN);
			textArea.setForeground(Color.GREEN);
		}

		public void actionPerformed(ActionEvent evt) {
			String text = textField.getText();
			textArea.append(text + "\n");
			cmds.add(text);

			out.println(text);

			textField.setText("");
			tabCount = 0;
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

}
