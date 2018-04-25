package edu.byui.cit.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;


public final class Academic {
	private Academic() {
	}

	public static final class Course {
		private static final HashMap<String, Float> values = new HashMap<>();
		private static final NumberFormat fmtrDec;

		static {
			values.put("A", 4.0F);
			values.put("A-", 3.7F);
			values.put("B+", 3.4F);
			values.put("B", 3.0F);
			values.put("B-", 2.7F);
			values.put("C+", 2.4F);
			values.put("C", 2.0F);
			values.put("C-", 1.7F);
			values.put("D+", 1.4F);
			values.put("D", 1.0F);
			values.put("D-", 0.7F);
			values.put("F", 0.0F);
			values.put("UW", 0.0F);
			values.put("W", 0.0F);
			fmtrDec = NumberFormat.getInstance();
			fmtrDec.setMinimumFractionDigits(1);
			fmtrDec.setMaximumFractionDigits(1);
		}

		public static float getValue(String grade) {
			return values.get(grade);
		}

		public static double computeGPA(
				double gpa, double credits, ArrayList<Course> courses) {
			double points = gpa * credits;
			for (Course course : courses) {
				points += course.value * course.credits;
				credits += course.credits;
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
			this.value = values.get(grade);
			this.credits = credits;
		}

		@Override
		public String toString() {
			return grade + " : " + fmtrDec.format(credits);
		}
	}
}
