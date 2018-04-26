package edu.byui.cit.model;

import java.text.NumberFormat;
import java.util.ArrayList;


public final class Academic {
	private Academic() {
	}

	public static final class Course {
		private static final String[] noCredit = { "I", "W" };
		private static final String[] letterGrades = {
				"A", "A-", "B", "B+", "B-", "C", "C+", "C-",
				"D", "D+", "D-", "F", "I", "UW", "W",
		};
		private static final float[] gradeValues = {
				4.0F, 3.7F, 3.0F, 3.4F, 2.7F, 2.0F, 2.4F, 1.7F,
				1.0F, 1.4F, 0.7F, 0.0F, 0.0F, 0.0F, 0.0F
		};
		private static final NumberFormat fmtrDec = NumberFormat.getInstance();

		static {
			fmtrDec.setMinimumFractionDigits(1);
			fmtrDec.setMaximumFractionDigits(1);
		}

		public static double computeGPA(
				double gpa, double credits, ArrayList<Course> courses) {
			double points = gpa * credits;
			for (Course course : courses) {
				points += course.getPoints();
				credits += course.getCredits();
			}
			if (credits > 0) {
				gpa = points / credits;
			}
			return gpa;
		}

		public static String toGradeString(ArrayList<Course> courses) {
			StringBuilder sb = new StringBuilder();
			String sep = "";
			for (Course c : courses) {
				sb.append(sep).append(c.toString());
				sep = "; ";
			}
			return sb.toString();
		}


		private String grade;
		private float value;
		private float credits;

		public Course(String grade, float credits) {
			this.grade = grade;
			this.value = gradeValues[Search.linearSearch(letterGrades, grade)];
			this.credits = credits;
		}

		public float getValue() {
			return value;
		}

		public float getPoints() {
			return value * credits;
		}

		public float getCredits() {
			return Search.contains(noCredit, grade) ? 0 : credits;
		}

		@Override
		public String toString() {
			return grade + " : " + fmtrDec.format(credits);
		}
	}
}
