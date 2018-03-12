package edu.byui.cit.calc360;

import org.junit.Test;
import static org.junit.Assert.*;
import static edu.byui.cit.model.Academic.GPA.*;


public final class AcademicTest {
	private static final double delta = 1e-9;

	@Test
	public void testGPA() {
		String[] grades = {"A", "A-", "B"};
		String grade = "A";

		assertEquals(4.0, calculateGPA(grade), delta);
		assertEquals(3.56, calculateGPA(grades), delta);
	}
}
