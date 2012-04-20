package telnet;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.Utilities;

import telnet.ScrollingTextPane.TextPane;

public class Telnet {

	private TextPane outputArea;
	private Socket s;
	private BufferedReader socketOutput;
	private PrintWriter socketInput;
	private JFrame gui = new JFrame("Telnet");
	private ManagedFile file = new ManagedFile();

	public Telnet(final String host, final int port) {
		new Thread() {
			public void run() {
				for (int i = 0; i < 10 && s == null; i++) {
					try {
						Thread.sleep(500);
						s = new Socket(InetAddress.getByName(host), port);
						break;
					} catch (Exception e1) {
					}
				}

				if (s == null) {
					System.out.println("Telnet could not connect");
					System.exit(1);
				} else {
					try {
						s.setKeepAlive(true);
						socketOutput = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						socketInput = new PrintWriter(s.getOutputStream(), true);
						gui.setIconImage(javax.imageio.ImageIO.read(new File(
								"icon.png")));
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (Exception e) {
					}
				}

				gui.setSize(600, 500);
				gui.setBackground(Color.BLACK);

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
						file.promptSaveOnQuit();
						System.exit(0);
					}
				});

				gui.setLayout(new GridBagLayout());

				LeftPanel leftPanel = new LeftPanel();

				RightPanel rightPanel = new RightPanel();

				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridy = 0;
				c.weighty = 1;
				c.weightx = 1;

				c.gridx = 1;
				c.anchor = GridBagConstraints.EAST;
				gui.add(rightPanel, c);

				c.gridx = 0;
				c.anchor = GridBagConstraints.WEST;
				gui.add(leftPanel, c);

				gui.setVisible(true);

			}
		}.start();
	}

	@SuppressWarnings("serial")
	private class RightPanel extends JPanel {
		RightPanel() {
			super(new GridBagLayout());

			setBackground(Color.BLACK);

			ScrollingTextPane rightInputScrollPane = new ScrollingTextPane();

			final JTextField fileInfo = new JTextField();
			fileInfo.setBackground(Color.BLACK);
			fileInfo.setForeground(Color.GREEN);
			fileInfo.setBorder(new LineBorder(Color.RED));
			fileInfo.setCaretColor(Color.RED);
			fileInfo.setEditable(false);
			fileInfo.setFocusable(false);
			fileInfo.setText(file.toString());
			fileInfo.setFont(new Font("Courier New", Font.PLAIN, 12));

			rightInputScrollPane.addKeyListener(new RightInputAreaListener(
					rightInputScrollPane, outputArea, socketInput, file));

			final TextPane textArea = rightInputScrollPane.getTextPane();

			textArea.getDocument().addUndoableEditListener(
					new UndoableEditListener() {
						public void undoableEditHappened(UndoableEditEvent e) {
							file.setSaved(false);

							try {
								int caretPos = textArea.getCaretPosition();
								int line = (caretPos == 0) ? 1 : 0;
								for (int offset = caretPos; offset > 0;) {
									offset = Utilities.getRowStart(textArea, offset) - 1;
									line++;
								}
								int offset = Utilities.getRowStart(textArea, caretPos);
								int col = caretPos - offset;

								fileInfo.setText(file.toString() + " " + line + " : "
										+ col);
							} catch (Exception e1) {
							}

						}
					});


			textArea.addFocusListener(new FocusAdapter() {

				public void focusLost(FocusEvent e) {
					fileInfo.setText(file.toString());
				}

			});

			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.weightx = 1;

			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.PAGE_END;
			c.gridy = 1;
			add(fileInfo, c);

			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.PAGE_START;
			c.weighty = 1;
			c.gridy = 0;
			add(rightInputScrollPane, c);
		}
	}

	@SuppressWarnings("serial")
	private class LeftPanel extends JPanel {
		LeftPanel() {
			super(new GridBagLayout());

			setBackground(Color.BLACK);

			ScrollingTextPane outputScrollPane = new ScrollingTextPane();
			outputArea = outputScrollPane.getTextPane();

			ScrollingTextPane leftInputScrollPane = new ScrollingTextPane();

			leftInputScrollPane.addKeyListener(new LeftInputAreaListener(
					leftInputScrollPane, outputArea, socketInput));

			outputArea.setEditable(false);

			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.weightx = 1;

			c.gridy = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			add(leftInputScrollPane, c);

			c.gridy = 0;
			c.weighty = 4;
			c.anchor = GridBagConstraints.PAGE_START;
			add(outputScrollPane, c);

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

		}
	}

}
