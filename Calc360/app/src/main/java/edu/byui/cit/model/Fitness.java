package edu.byui.cit.model;

import java.util.HashMap;
import java.util.Map;

import edu.byui.cit.text.SpinInteger;

public final class Fitness {
    private Fitness() {
	}

    public static double computeCalories(double ratio, double weight, double minutes) {
        return (ratio * weight) * minutes;
    }

    public static double computeCalWithMet(double met, double weight, double minutes) {
    	return (met * 3.5 * weight / 200 * minutes);
	}

    public static double repMax(int rep, double weight) {

        // Create data for calculation
        Double[] weightRatio = {1.0, .95, .93, .90, .87, .85, .83, .80, .77, .75, .73, .70};
        int[] key = {1,2,3,4,5,6,7,8,9,10,11,12};

        // store as a map for easy extraction
        Map<Integer, Double> hashMap = new HashMap<>();

        for (int i = 0;  i < key.length;  ++i) {
            hashMap.put(key[i], weightRatio[i]);
        }
        return (weight/hashMap.get(rep - 1) );
    }
}
