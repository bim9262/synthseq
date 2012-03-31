package telnet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.*;

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s.setKeepAlive(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread(){
			public void run(){
				try {
					while(true)
					textArea.append(in.readLine() + "\n");
				} catch (IOException e) {
					System.out.println("Connection Closed.");
				}
			}
		}.start();

		gui.setSize(400, 400);
		gui.add(g);

		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				out.close();
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		gui.setVisible(true);

		textField.requestFocusInWindow();

	}

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
			textArea.setEditable(false);
			textArea.setFocusable(false);

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
