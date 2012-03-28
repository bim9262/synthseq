package synthseq.playables.readables;

import synthseq.playables.Playable;
import synthseq.synthesizer.Synthesizer;

public abstract class Readable implements Playable{
	public void register(){
		Synthesizer.getInstance().addSource(this);
	}
	public abstract double read();
}
