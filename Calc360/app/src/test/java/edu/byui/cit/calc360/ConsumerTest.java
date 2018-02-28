package edu.byui.cit.calc360;

import org.junit.Test;

import edu.byui.cit.model.Consumer.*;

import static org.junit.Assert.assertEquals;


public final class ConsumerTest {
	private static final double delta = 1e-9;

	@Test
	public void testRate() {
		double x = 48;
		double y = 50;
		double z = 52;
		assertEquals(0.04, Ratio.rateChange(y, z), delta);
		assertEquals(-0.04, Ratio.rateChange(y, x), delta);
	}

	@Test
	public void testSalesTax() {
		double price = 100;
		double taxRate = 0.0825;
		double taxAmt = price * taxRate;
		double total = price + taxAmt;
		assertEquals(taxAmt, Ratio.amount(taxRate, price), delta);
		assertEquals(total, Ratio.total(taxRate, price), delta);
	}

	@Test
	public void testDiscount() {
		double price = 35.00;
		double discRate = 0.25;
		double taxRate = 0.06;
		double discAmt = price * discRate;
		double discPrice = price - discAmt;
		double discTotal = discPrice * (1 + taxRate);
		double origTotal = price * (1 + taxRate);
		double saved = origTotal - discTotal;
		assertEquals(discAmt, Ratio.amount(discRate, price), delta);
		assertEquals(saved, Discount.amountSaved(price, discRate, taxRate), delta);
	}

	@Test
	public void testComparePrices() {
	}

	@Test
	public void testTip() {
		double cost = 40;
		double taxAmt = 2.4;
		double tipRate = 0.20;
		double tipAmt = cost * tipRate;
		double total = cost + taxAmt + tipAmt;
		assertEquals(tipAmt, Ratio.amount(tipRate, cost), delta);
		assertEquals(tipAmt, Tip.tipAmount(cost, taxAmt, total), delta);
		assertEquals(tipRate, Ratio.rate(tipAmt, cost), delta);
		assertEquals(total, Tip.total(cost, taxAmt, tipAmt), delta);
	}

	@Test
	public  void testNetflix() {
		double price = 8.0;
		double firstHour = 4.0;
		double secondHour = 9.0;
		double thirdHour = 3.0;
		double firstMin = 22.0;
		double secondMin = 45.0;
		double time = NetflixComputing.time(firstHour, secondHour, thirdHour, firstMin, secondMin);
		
		assertEquals(0.12, NetflixComputing.sum(price, time), delta);
		assertEquals(10, NetflixComputing.movieLengthTwoHalf(firstHour), delta);
		assertEquals(18, NetflixComputing.movieLengthTwo(secondHour), delta);
		assertEquals(4.5, NetflixComputing.movieLengthOneHalf(thirdHour), delta);
		assertEquals(16.5, NetflixComputing.episodeLengthFourFive(firstMin), delta);
		assertEquals(16.65, NetflixComputing.episodeLengthTwoTwo(secondMin), delta);
	}
}
