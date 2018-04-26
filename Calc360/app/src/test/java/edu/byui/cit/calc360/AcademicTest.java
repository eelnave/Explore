package edu.byui.cit.calc360;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import edu.byui.cit.model.Academic.Course;


public final class AcademicTest {
	private static final double delta = 1e-6;

	@Test
	public void testGPA() {
		final String[] grades = {
				"A", "A-", "B+", "B", "B-", "C+", "C", "C-",
				"D+", "D", "D-", "F", "UW", "W", "I"
		};
		final float[] values = {
				4.0F, 3.7F, 3.4F, 3.0F, 2.7F, 2.4F, 2.0F, 1.7F,
				1.4F, 1.0F, 0.7F, 0.0F, 0.0F, 0.0F, 0.0F
		};
		final float[] credits = {
				3, 3, 3, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3, 0, 0
		};
		assertEquals(grades.length, values.length);
		assertEquals(grades.length, credits.length);

		ArrayList<Course> courses = new ArrayList<>(grades.length);
		double totalPoints = 0;
		double totalCredits = 0;
		for (int i = 0;  i < grades.length;  ++i) {
			Course course = new Course(grades[i], 3);
			courses.add(course);
			float v = values[i];
			float c = credits[i];
			float p = v * c;
			totalPoints += p;
			totalCredits += c;
			assertEquals(v, course.getValue(), delta);
			assertEquals(c, course.getCredits(), delta);
			assertEquals(p, course.getPoints(), delta);
		}
		double gpa = totalPoints / totalCredits;
		assertEquals(2.0, gpa, delta);
		assertEquals(2.0, Course.computeGPA(0, 0, courses), delta);
		assertEquals(2.27, Course.computeGPA(4, 6, courses), 0.005);
	}
}
