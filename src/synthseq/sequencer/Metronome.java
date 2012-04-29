package synthseq.sequencer;

public class Metronome {
	private double bpms;
	private long startTime;
	public Metronome(double bpm){
		bpms = bpm/60000.0;
		startTime = System.currentTimeMillis();
	}
	public long getTime(double beat){
		return (long) (startTime+beat/bpms);
	}
	public double getBeat(){
		return (int)((System.currentTimeMillis()-startTime)*bpms);
	}
	public double getBeatAt(long time){
		return (int)((time-startTime)*bpms);
	}
	public static void main(String[]args)throws Exception{
		Metronome m = new Metronome(120);
		System.out.println(System.currentTimeMillis());
		Thread.sleep(500);
		System.out.println(m.getBeat());
		System.out.println(m.getBeatAt(System.currentTimeMillis()+1000));
	}
}
