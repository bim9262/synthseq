package synthseq.playables.readables;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Clip extends ReadableSound {
	private double[] audio;
	private boolean playing = false;
	private byte frameSize = 0;
	private int location = 0;

	public static void main(String[] args) throws Exception {
		Clip clip = new Clip("/home/john/Desktop/Beats/misc/bass.wav");
		clip.play();
		Thread.sleep(2000);
	}

	public Clip(final String f) {
		new Thread() {
			public void run() {
				try {
					File file = new File(f);
					AudioInputStream audioStream = AudioSystem
							.getAudioInputStream(file);
					AudioFileFormat format = AudioSystem
							.getAudioFileFormat(file);
					frameSize = ((byte) (format.getFormat().getFrameSize()));
					byte[] frame = new byte[frameSize];
					audio = new double[AudioSystem.getAudioFileFormat(file)
							.getByteLength() / (frameSize)];
					for (int i = 0; audioStream.available() > frameSize + 1
							&& i < audio.length; i++) {
						audioStream.read(frame);
						byte[] used = new byte[4];
						int max = format.getFormat().getSampleSizeInBits() / 8 / format.getFormat().getChannels();
						for (int j = 0; j < max && j < 4; j++)
							used[3-j] = frame[frame.length-j-1];
						ByteBuffer bb = ByteBuffer.wrap(used);
						bb.order(ByteOrder.LITTLE_ENDIAN);
						int out = bb.getInt();
						audio[i] = out / (double) (Integer.MAX_VALUE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public void start() {
		location = 0;
		playing = true;
	}

	@Override
	public void stop() {
		playing = false;
		location = 0;
	}

	@Override
	public double read() {
		if (playing) {
			try {
				if (location++ >= audio.length - 1){
					stop();
					location = 0;
				}
				return audio[location];
			} catch (Exception e) {
				return 0;
			}
		} else
			return 0;
	}

}
