package edu.byui.cit.calc360;

import org.junit.Test;

import static edu.byui.cit.model.Statistics.*;
import static org.junit.Assert.*;

public final class StatisticsTest {
	private static final double delta = 1e-9;

	@Test
	public void testBinDistProb() {
		int n = 5;
		int x = 2;
		double p = 0.05;

		assertEquals(0.021434375, binDistProb(n, x, p), delta);
	}
}