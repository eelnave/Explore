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

    public static double repMax(int reps, double weight) {
        final double[] ratio = {
                1.0, 0.95, 0.93, 0.90, 0.87, 0.85, 0.83, 0.80, 0.77, 0.75, 0.73, 0.70
        };
        return weight * ratio[reps - 1];
    }
}
