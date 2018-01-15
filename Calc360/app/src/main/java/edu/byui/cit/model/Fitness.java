package edu.byui.cit.model;

public final class Fitness {
    private Fitness() {
	}

    public static double computeCalories(double ratio, double weight, double minutes) {
        return (ratio * weight) * minutes;
    }
}
