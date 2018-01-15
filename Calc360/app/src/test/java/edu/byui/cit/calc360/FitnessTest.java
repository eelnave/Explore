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
}
