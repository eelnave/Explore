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

		double t = (h * 3600) + (m * 60) + s;
		double p = t / d;
		double sp = (t / d) % 60;
		sp = Math.round(sp * 100) / 100.0;
		int hp = (int)(h / d);
		p = p - (hp * 3600);
		int mp = (int) p / 60;

		String output = hp + ":" + mp + ":" + sp;

		return output;
	}
}
