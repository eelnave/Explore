package edu.byui.cit.calc360;

import org.junit.Test;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import edu.byui.cit.model.Mathematics;
import edu.byui.cit.model.Fraction;
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
	public void testFraction() {
		String realStr = "3.984375";
		double real = Double.parseDouble(realStr);
		int sign = 1, whole = 3, numer = 63, denom = 64;

		Fraction fract = new Fraction(1, 3, 984375, 1000000);
		assertEquals(sign, fract.getSign());
		assertEquals(whole, fract.getWhole());
		assertEquals(numer, fract.getNumer());
		assertEquals(denom, fract.getDenom());
		assertEquals(real, fract.doubleValue(), delta);

		Fraction right = new Fraction(1, 5, 3, 4);
		Fraction sum = fract.add(right);
		assertEquals(1, sum.getSign());
		assertEquals(9, sum.getWhole());
		assertEquals(47, sum.getNumer());
		assertEquals(64, sum.getDenom());

		Fraction diff = fract.subtract(right);
		assertEquals(-1, diff.getSign());
		assertEquals(1, diff.getWhole());
		assertEquals(49, diff.getNumer());
		assertEquals(64, diff.getDenom());

		Fraction sum2 = diff.add(right);
		assertEquals(fract.getSign(), sum2.getSign());
		assertEquals(fract.getWhole(), sum2.getWhole());
		assertEquals(fract.getNumer(), sum2.getNumer());
		assertEquals(fract.getDenom(), sum2.getDenom());

		Fraction diff2 = fract.subtract(diff);
		assertEquals(right.getSign(), diff2.getSign());
		assertEquals(right.getWhole(), diff2.getWhole());
		assertEquals(right.getNumer(), diff2.getNumer());
		assertEquals(right.getDenom(), diff2.getDenom());

		Fraction diff3 = sum.subtract(right);
		assertEquals(fract.getSign(), diff3.getSign());
		assertEquals(fract.getWhole(), diff3.getWhole());
		assertEquals(fract.getNumer(), diff3.getNumer());
		assertEquals(fract.getDenom(), diff3.getDenom());
		
		Fraction prod = fract.multiply(right);
		assertEquals(1, prod.getSign());
		assertEquals(22, prod.getWhole());
		assertEquals(233, prod.getNumer());
		assertEquals(256, prod.getDenom());
		
		Fraction quot = fract.divide(right);
		assertEquals(1, quot.getSign());
		assertEquals(0, quot.getWhole());
		assertEquals(255, quot.getNumer());
		assertEquals(368, quot.getDenom());
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
