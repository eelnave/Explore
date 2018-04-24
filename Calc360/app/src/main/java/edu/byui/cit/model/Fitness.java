package edu.byui.cit.model;

public final class Fitness {
	private Fitness() {
	}

	public static double computeCalories(
			double ratio, double weight, double minutes) {
		return (ratio * weight) * minutes;
	}

	public static double computeCalWithMet(
			double met, double weight, double minutes) {
		return (met * 3.5 * weight / 200 * minutes);
	}

	public static double[] calcPace(double dist, int h, int m, int s) {
		double time = (h * 3600) + (m * 60) + s;  // Total seconds
		double ps = time / dist;  // seconds per km or mile
		double pm = Math.floor(ps / 60.0);
		ps -= pm * 60.0;
		double ph = Math.floor(pm / 60.0);
		pm -= ph * 60.0;
		return new double[]{ ph, pm, ps };
	}
}
