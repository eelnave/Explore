package edu.byui.cit.model;

public final class Fitness {
    private Fitness() {
	}

    public static double computeCalories(double ratio, double weight, double minutes) {
        return (ratio * weight) * minutes;
    }

    public static double computeCalWithMet(double met, double weight, double minutes) {
    	return (met * 3.5 * weight / 200 * minutes);
	}

	public static String calcPace(double d, int h, int m, int s) {
		String output;
		double t = (h * 3600) + (m * 60) + s;
		double sp = (t / d) % 60;
		sp = Math.round(sp * 100) / 100.0;
		int mp = (int)(t / 60 / d) % 60;
		int hp = (int)(t / 3600 / d) % 60;

		output = hp + " Hrs: " + mp + " Mins: " + sp + " Secs";

		return output;
	}
}
