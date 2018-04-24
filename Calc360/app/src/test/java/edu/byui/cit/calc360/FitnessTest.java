package edu.byui.cit.calc360;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.byui.cit.model.Fitness;


public final class FitnessTest {
	@Test
	public void testCalories() {
		//calories burn per exercise
		double swimming = .05;
		double weightLift = .024;
		double running = .07;
		double walking = .03;

		//user weight and minutes exercising
		double bodyWeight = 180;
		double minutes = 30;

		//what type of exercise to test
		int ResultsSwimming = (int)Fitness.computeCalories(swimming, bodyWeight, minutes);
		int ResultsWeightLift = (int)Fitness.computeCalories(weightLift, bodyWeight, minutes);
		int ResultsRunning = (int)Fitness.computeCalories(running, bodyWeight, minutes);
		int ResultsWalking = (int)Fitness.computeCalories(walking, bodyWeight, minutes);

		//testing the results
		assertEquals(270, ResultsSwimming);
		assertEquals(129, ResultsWeightLift);
		assertEquals(378, ResultsRunning);
		assertEquals(161, ResultsWalking);
	}

	@Test
	public void testCalcPace() {
		final double delta = 0.005;

		assertArrayEquals(new double[]{0, 6, 40.57}, Fitness.calcPace(5.3, 0, 35, 23), delta);

		// Test values when hours are used and returned
		assertArrayEquals(new double[]{0, 8, 13.24}, Fitness.calcPace(26.2, 3, 35, 23), delta);
		assertArrayEquals(new double[]{1, 47, 41.5}, Fitness.calcPace(2, 3, 35, 23), delta);

		// Test return when large numbers are inserted into seconds or minutes.
		// Verifies that when values in minutes or seconds exceeds 60 that the
		// values exceeding 60 are carried over to the correct unit.
		assertArrayEquals(new double[]{26, 6, 39.67}, Fitness.calcPace(3, 0, 700, 239999), delta);
	}
}
