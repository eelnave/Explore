package edu.byui.cit.calc360;

import org.junit.Test;


import edu.byui.cit.model.Mathematics;
import edu.byui.cit.model.Mathematics.Quadratic;

import static edu.byui.cit.model.Mathematics.Roman.arabicFromRoman;
import static edu.byui.cit.model.Mathematics.Roman.romanFromArabic;
import static org.junit.Assert.assertEquals;


public final class MathematicsTest {
	private static final double delta = 1e-9;

	@Test
	public void testGCDLCM() {
		long x = 3, y = 11;
		assertEquals(1, Mathematics.gcd(x, y));
		assertEquals(33, Mathematics.lcm(x, y));
		x = -3; y = 9;
		assertEquals(3, Mathematics.gcd(x, y));
		assertEquals(-9, Mathematics.lcm(x, y));
		x = -24; y = 472;
		assertEquals(8, Mathematics.gcd(x, y));
		assertEquals(-1416, Mathematics.lcm(x, y));
		x = -8; y = -28;
		assertEquals(4, Mathematics.gcd(x, y));
		assertEquals(56, Mathematics.lcm(x, y));
	}

	@Test
	public void testQuadratic() {
		assertEquals(-3, Quadratic.discrim(1, 1, 1), delta);
		assertEquals(-12, Quadratic.discrim(2, 2, 2), delta);
		assertEquals(-27, Quadratic.discrim(3, 3, 3), delta);
		assertEquals(0, Quadratic.discrim(0, 0, 0), delta);
		assertEquals(0, Quadratic.discrim(1, 2, 1), delta);
		assertEquals(4, Quadratic.discrim(3, 4, 1), delta);
		assertEquals(9, Quadratic.discrim(1, 5, 4), delta);

		assertEquals(-1, Quadratic.root1(3, 4, 4), delta);
		assertEquals(-4, Quadratic.root1(1, 5, 9), delta);

		assertEquals(-1, Quadratic.root2(1, 2, 0), delta);
		assertEquals(-1.0 / 3, Quadratic.root2(3, 4, 4), delta);
		assertEquals(-1, Quadratic.root2(1, 5, 9), delta);
	}

	@Test
	public void testRomanNumerals() {
		for (int i = 0;  i < 4000;  ++i) {
			String roman = romanFromArabic(i);
			int arabic = arabicFromRoman(roman);
			assertEquals(i, arabic);
		}
	}
}
