package playables;

import java.io.File;

import javax.sound.sampled.Clip;

import synthseq.LOut;

public class Sample implements Playable {
	private Clip audio;
	public Sample(File f) {
		try {
			audio = LOut.getClip(f);
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
