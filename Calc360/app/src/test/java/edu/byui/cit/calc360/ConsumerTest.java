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
		assertEquals(0.04, Rate.rateChange(y, z), delta);
		assertEquals(-0.04, Rate.rateChange(y, x), delta);
	}

	@Test
	public void testSalesTax() {
		double price = 100;
		double taxRate = 0.0825;
		double taxAmt = price * taxRate;
		double total = price + taxAmt;
		assertEquals(taxAmt, Rate.amount(price, taxRate), delta);
		assertEquals(total, Rate.total(price, taxRate), delta);
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
		assertEquals(discAmt, Discount.discountAmount(price, discRate), delta);
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
		assertEquals(tipAmt, Tip.computeTipAmount(cost, tipRate), delta);
		assertEquals(total, Tip.computeTotal(cost, taxAmt, tipAmt), delta);
		assertEquals(tipRate, Tip.computeTipRate(tipAmt, cost), delta);
		assertEquals(tipAmt, Tip.computeTipAmount(cost, taxAmt, total), delta);
	}
}
