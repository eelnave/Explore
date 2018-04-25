package edu.byui.cit.calc360;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.byui.cit.model.Academic.Course;


public final class AcademicTest {
	private static final double delta = 1e-9;

	@Test
	public void testGPA() {
		final String[] grades = {
				"A", "A-", "B+", "B", "B-", "C+", "C", "C-",
				"D+", "D", "D-", "F", "UW", "W"
		};
		final float[] values = {
				4.0F, 3.7F, 3.4F, 3.0F, 2.7F, 2.4F, 2.0F, 1.7F,
				1.4F, 1.0F, 0.7F, 0.0F, 0.0F, 0.0F
		};
		assertEquals(grades.length, values.length);
		for (int i = 0;  i < grades.length;  ++i) {
			assertEquals(values[i], Course.getValue(grades[i]), delta);
		}
	}
}
