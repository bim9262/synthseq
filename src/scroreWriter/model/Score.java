package scroreWriter.model;

import java.util.ArrayList;

public class Score {
	
	private String title;
	private String composer;
	private String compositionDate;
	private ArrayList<InstrumentsParts> parts = new ArrayList<InstrumentsParts>();


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getCompositionDate() {
		return compositionDate;
	}

	public void setCompositionDate(String compositionDate) {
		this.compositionDate = compositionDate;
	}

	
}
