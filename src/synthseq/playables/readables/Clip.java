package synthseq.playables.readables;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import synthseq.synthesizer.Synthesizer;

public class Clip extends ReadableSound {
	private double[] audio;
	private boolean playing = false;
	private byte frameSize = 0;
	private int location = 0;

	public static void main(String[] args) throws Exception {
		Clip clip = new Clip("/home/john/Desktop/Beats/39452__the-bizniss__gotcha.wav");
		clip.play();
		Synthesizer.getInstance().showFreqVis();
		Synthesizer.getInstance().showVisualizer();
		Thread.sleep(2000);
	}

	public Clip(String f) {
		try {
			File file = new File(f);
			AudioInputStream audioStream = AudioSystem
					.getAudioInputStream(file);
			AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
			frameSize =  ((byte) (format
					.getFormat().getFrameSize()));
			byte[] frame = new byte[frameSize];
			audio = new double[AudioSystem.getAudioFileFormat(file)
					.getByteLength() / frameSize];
			for (int i = 0; audioStream.available() > frameSize + 1
					&& i < audio.length; i++) {
				audioStream.read(frame);
				byte[] used = new byte[2];
				used[0] = frame[0];
				used[1] = frame[1];
				ByteBuffer bb = ByteBuffer.wrap(frame);
				bb.order(ByteOrder.LITTLE_ENDIAN);
				short out = bb.getShort();
				audio[i] = (out / (double) Short.MAX_VALUE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		location = 0;
		playing = true;
	}

	@Override
	public void stop() {
		playing = false;
	}

	@Override
	public double read() {
		if (playing) {
			try {
				if (location++ >= audio.length - 1)
					location = 0;
				return audio[location];
			} catch (Exception e) {
				return 0;
			}
		} else
			return 0;
	}

}
