package scroreWriter.model;

abstract class MusicalObject {

	protected Duration duration;

	public MusicalObject() {
	}

	public MusicalObject(String s) {
	}

	public final Duration getDuration(){
		return duration;
	}

	public final void setDuration(Duration duration){
		this.duration = duration;
	}

	public final String toString(){
		return duration.toString() + " " + getClass().getName();
	}

}
