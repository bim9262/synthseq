package synthseq.playables.readables.filters;

import synthseq.playables.readables.Readable;


public class Modulator implements Readable {
	private Readable p;
	private double time = 0;
	private double wobbleFreq = 1;

	public Modulator(Readable p, double wobbleFreq) {
		this.wobbleFreq = wobbleFreq;
		this.p = p;
	}
	
	public void setPlayable(Readable p){
		this.p = p;
	}

	@Override
	public double read() {
		time += 8 * wobbleFreq / 44100;
		if (time >= 2 * Math.PI)
			time = 0;
		return p.read() * (Math.sin(time) * .5 + .5);
	}

	@Override
	public void start() {
		p.start();
	}

	@Override
	public void stop() {
		p.stop();
	}

	public void setWobble(float x) {
		wobbleFreq = x;
	}
}
