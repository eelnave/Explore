package edu.byui.cit.model;

public final class Academic {

	private Academic() {

	}

	public static final class GPA {
		private GPA() {

		}
		public static double calculateGPA(String grade) {
			double gpa = 0;

			String[] letters = {
					"A+","A", "A-",
					"B+", "B", "B-",
					"C+", "C", "C-",
					"D+", "D", "D-",
					"F+", "F", "F-"
			};

			double[] points = {
					4.0, 4.0, 3.7,
					3.4, 3.0, 2.7,
					2.4, 2.0, 1.7,
					1.4, 1.0, 0.7,
					0.0, 0.0, 0.0
			};

			for (int i = 0; i < letters.length; i++) {
				if (grade == letters[i]) {
					gpa = points[i];
				}
			}

			return Math.floor(gpa * 100) / 100;
		}

		public static double calculateGPA(String[] grades) {
			double gpa = 0;
			double totalPoints = 0;
			int count = grades.length;

			for (String grade : grades) {
				totalPoints += calculateGPA(grade);
			}

			gpa = totalPoints / count;

			return Math.floor(gpa * 100) / 100;
		}
	}
}
