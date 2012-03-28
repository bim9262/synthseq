package synthseq.oscserver;
import java.net.DatagramPacket;
import java.net.DatagramSocket;



/**
 * Opens a UDP socket on the specified port
 * Interprets a subset of the Open Sound Control protocol
 * Accepts a path with zero to two float arguments
 * 
 * Interpreted OSC commands should be acted upon in the ActionMap.
 * 
 * @author john
 */
public class OSCListener {
	public static void start(final ActionMap map, int port) {
		try {
			final DatagramSocket input = new DatagramSocket(port);
			new Thread() {
				public void run() {
					try {
						while (true) {
							float x = 0;
							float y = 0;
							byte[] b = new byte[255];
							DatagramPacket p = new DatagramPacket(b, b.length);
							input.receive(p);
							String label = new String(b);
							for (int i = 0; i < b.length - 7; i++) {
								if (b[i] == 44 && b[i + 1] == 102
										&& b[i + 2] == 0 && b[i + 3] == 0) {
									x = Float.intBitsToFloat(byteArrayToInt(b,
											i + 4));
									break;
								}
								if (b[i] == 44 && b[i + 1] == 102
										&& b[i + 2] == 102 && b[i + 3] == 0) {
									x = Float.intBitsToFloat(byteArrayToInt(b,
											i + 4));
									y = Float.intBitsToFloat(byteArrayToInt(b,
											i + 8));
									break;
								}
							}
							map.interpret(label.substring(0,label.indexOf(0)), x, y);
						}
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static int byteArrayToInt(byte[] b, int start) {
		int value = 0;
		for (int i = start; i < start + 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i] & 0xff) << shift;
		}
		return value;
	}

}
