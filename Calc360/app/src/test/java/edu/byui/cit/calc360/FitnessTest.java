package edu.byui.cit.calc360;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.byui.cit.model.Fitness;


public final class FitnessTest {
	@Test
	public void testCalories() throws Exception {
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

		// Test the exact string that will be returned
		assertEquals("0 Hrs: 6 Mins: 40.57 Secs", Fitness.calcPace(5.3, 0, 35, 23));
		// check that the calcPace method doesn't rounding down
		assertFalse(Fitness.calcPace(5.3, 0, 35, 23).contains("40.56"));
		assertTrue(Fitness.calcPace(5.3, 0, 35, 23).contains("40.57"));

		// Test values when hours are used and returned
		assertEquals("0 Hrs: 8 Mins: 13.24 Secs", Fitness.calcPace(26.2, 3, 35, 23));
		assertEquals("1 Hrs: 47 Mins: 41.5 Secs", Fitness.calcPace(2, 3, 35, 23));

		// Test return when large numbers are inserted into seconds or minutes.
		// Verifies that when values in minutes or seconds exceeds 60 that the
		// values exceeding 60 are carried over to the correct unit.
		assertEquals("26 Hrs: 6 Mins: 39.67 Secs", Fitness.calcPace(3, 0, 700, 239999));





	}
}
