package scroreWriter.model.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import scroreWriter.model.*;

public class MeasureTest {

	Measure measure;

	@Before
	public void setUp() throws Exception {
		measure = new Measure();
	}

	@Test
	public void testLength() {
		measure.setTimeSigHigh(2);
		assertEquals(measure.length(), .5, .1);
		measure.setTimeSigHigh(3);
		assertEquals(measure.length(), .75, .1);
		measure.setTimeSigHigh(4);
		assertEquals(measure.length(), 1, .1);

		measure.setTimeSigLow(2);
		measure.setTimeSigHigh(2);
		assertEquals(measure.length(), 1, .1);
		measure.setTimeSigHigh(3);
		assertEquals(measure.length(), 1.5, .1);

		measure.setTimeSigLow(8);
		measure.setTimeSigHigh(3);
		assertEquals(measure.length(), 1.5/4, .1);
		measure.setTimeSigHigh(6);
		assertEquals(measure.length(), .75, .1);
		measure.setTimeSigHigh(9);
		assertEquals(measure.length(), 4.5/4, .1);
		measure.setTimeSigHigh(12);
		assertEquals(measure.length(), 1.5, .1);
	}
	
	@Test
	public void testIsFull() {
		Note note = new Note("G4");
		note.setDuration(Duration.QUARTER);
		assertFalse(measure.isFull());
		measure.add(note);
		measure.add(note);
		measure.add(note);
		assertFalse(measure.isFull());
		measure.add(note);
		assertTrue(measure.isFull());
		measure.add(note);
		assertTrue(measure.isFull());
	}
	
	@Test
	public void testGetOverflow() {
		Note note0 = new Note("A4");
		note0.setDuration(Duration.QUARTER);
		Note note1 = new Note("B4");
		note1.setDuration(Duration.QUARTER);
		Note note2 = new Note("C4");
		note2.setDuration(Duration.QUARTER);
		Note note3 = new Note("D4");
		note3.setDuration(Duration.QUARTER);
		Note note4 = new Note("E4");
		note4.setDuration(Duration.QUARTER);
		Note note5 = new Note("F4");
		note5.setDuration(Duration.QUARTER);
		assertFalse(measure.isFull());
		measure.add(note0);
		measure.add(note1);
		measure.add(note2);
		measure.add(note3);
		measure.add(note4);
		measure.add(note5);
		
		ArrayList<MusicalObject> overFlow = measure.getOverflow();
		assertEquals(overFlow.size(), 2);
		assertEquals(overFlow.get(0).getCenter(), "E4");
		assertEquals(overFlow.get(1).getCenter(), "F4");

	}

}
