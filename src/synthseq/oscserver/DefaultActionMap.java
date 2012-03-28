package synthseq.oscserver;

import java.util.Scanner;


public class DefaultActionMap implements ActionMap {
	public void interpret(String label, float x, float y) {
		/*if (label.matches("/1/push[1-9]"))
			if (x == 1)
				Sequencer.play(label.charAt(7) - 49);
			else
				Sequencer.damp(label.charAt(7) - 49);
		if (label.matches("/4/toggle[1-5]")) {
			if (x == 1) {
				Sequencer.startWub(label.charAt(9) - 49);
			} else {
				Sequencer.stopWub(label.charAt(9) - 49);
			}
		}
		if (label.matches("/2/multitoggle/[0-9]{1,2}/[0-9]{1,2}")) {
			Scanner s = new Scanner(label);
			s.useDelimiter("/");
			s.next();
			s.next();
			if (x == 0) {
				Sequencer.setOff(s.nextInt(), s.nextInt());
			} else
				Sequencer.setOn(s.nextInt(), s.nextInt());
		}
		if (label.matches("/4/xy")) {
			if (oldY == -1)
				oldY = y;
			float dY = y - oldY;
			oldY = y;
			Sequencer.setWub(x, dY);
		}*/
	}
}
