package scroreWriter.model;

public abstract class MusicalObject {

	protected int posistion;
	protected Duration duration;
	private String center;

	public MusicalObject() {
	}
	
	public MusicalObject(String center) {
		this.setCenter(center);
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

	public final String getCenter() {
		return center;
	}

	public final void setCenter(String center) {
		this.center = center;
	}

}
