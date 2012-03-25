package synthseq;
public abstract class Action {
	private int beat;
	public abstract void act();
	public void setBeat(int beat){
		this.beat = beat;
	}
	public int getBeat(){
		return beat;
	}
}
