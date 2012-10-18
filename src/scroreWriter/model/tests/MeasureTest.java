package scroreWriter.model.tests;

import static org.junit.Assert.*;
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
		assertEquals(measure.length(), 2, .1);
		measure.setTimeSigHigh(3);
		assertEquals(measure.length(), 3, .1);
		measure.setTimeSigHigh(4);
		assertEquals(measure.length(), 4, .1);

		measure.setTimeSigLow(2);
		measure.setTimeSigHigh(2);
		assertEquals(measure.length(), 4, .1);
		measure.setTimeSigHigh(3);
		assertEquals(measure.length(), 6, .1);

		measure.setTimeSigLow(8);
		measure.setTimeSigHigh(3);
		assertEquals(measure.length(), 1.5, .1);
		measure.setTimeSigHigh(6);
		assertEquals(measure.length(), 3, .1);
		measure.setTimeSigHigh(9);
		assertEquals(measure.length(), 4.5, .1);
		measure.setTimeSigHigh(12);
		assertEquals(measure.length(), 6, .1);
	}
	
	@Test
	public void testIsFull() {
		Note note = new Note();
		note.setDuration(Duration.WHOLE);
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

}
