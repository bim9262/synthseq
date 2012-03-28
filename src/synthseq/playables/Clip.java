package synthseq.playables;

import java.io.File;

import synthseq.synthesizer.LineOut;

public class Clip implements Playable {
	private javax.sound.sampled.Clip audio;
	public Clip(File f) {
		try {
			audio = LineOut.getClip(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void start() {
		audio.start();
	}

	@Override
	public void stop() {
		audio.stop();
	}

}
