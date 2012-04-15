package synthseq.synthesizer;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class LineOut {
	private SourceDataLine soundLine;
	private byte[] buffer;
	private int bytes = 0;
	private static LineOut instance;
	private static TargetDataLine mic;

	// singleton constructor, only one lineout at a time.
	private LineOut() {
		try {
			try {
				Line.Info targetDLInfo = new Line.Info(TargetDataLine.class);
				Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();

				for (int i = 0; i < mixerInfo.length; i++) {

					Mixer currentMixer = AudioSystem.getMixer(mixerInfo[i]);

					if (currentMixer.isLineSupported(targetDLInfo)) {
						mic = (TargetDataLine) currentMixer.getLine(targetDLInfo);
					}
				}
				mic.open();
				mic.start();
				//System.out.println(mic);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int channels = 2;
			AudioFormat audioFormat = new AudioFormat(44100, 16, channels,
					true, false);
			buffer = new byte[4 * 1024 * channels];
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static LineOut getInstance() {
		if (instance == null)
			instance = new LineOut();
		return instance;
	}

	public static Clip getClip(File f) throws Exception {
		AudioInputStream sound = AudioSystem.getAudioInputStream(f);
		DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		Clip clip = (Clip) AudioSystem.getLine(info);
		clip.open(sound);
		return clip;
	}

	public static double readMic() {
		byte[] b = new byte[4];
		mic.read(b, 0, b.length);
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getShort() / ((double) Short.MAX_VALUE);
	}

	private void write(double d) {
		short s = (short) (d * Short.MAX_VALUE);
		buffer[bytes++] = (byte) (s & 0xff);
		buffer[bytes++] = (byte) ((s >> 8) & 0xff);
		if (bytes == buffer.length) {
			soundLine.write(buffer, 0, buffer.length);
			bytes = 0;
		}
	}

	public void writeM(double d) {
		write(d);
		write(d);
	}

	public void writeS(double d1, double d2) {
		write(d1);
		write(d2);
	}
}
