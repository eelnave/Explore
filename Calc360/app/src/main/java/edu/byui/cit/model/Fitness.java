package edu.byui.cit.model;

public final class Fitness {
    private Fitness() {
	}

    public static double computeCalories(double ratio, double weight, double minutes) {
        return (ratio * weight) * minutes;
    }

    public static double computeCalWithMet(double met, double weight, double minutes) {
    	return (.0175 * met * (weight/2.2) * minutes);
	}
}
