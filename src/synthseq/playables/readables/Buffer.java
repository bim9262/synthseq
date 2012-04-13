package synthseq.playables.readables;

public class Buffer extends ReadableSound{
	//Like a clip, just buffers sound, except a waveform instead of a file
	private boolean running = false;
	private double[] data;
	private int location = 0;
	public Buffer(ReadableSound r, double period){
		data = new double[(int) (period*44100)];
		r.start();
		for(int i = 0; i < data.length; i++)
			data[i]=r.read();
		r.stop();
	}
	
	@Override
	public void start() {
		location = 0;
		running = true;
		
	}

	@Override
	public void stop() {
		running = false;
		
	}
	@Override
	public double read() {
		if (running) {
			try {
				if (location++ >= data.length - 1){
					stop();
				}
				return data[location];
			} catch (Exception e) {
				return 0;
			}
		} else
			return 0;
	}

}
