package edu.byui.cit.calc360;

import org.junit.Test;

import static edu.byui.cit.model.Finance.*;

import static org.junit.Assert.assertEquals;


public final class FinanceTest {
	@Test
	public void testCompoundInterest() {
		double delta = 0.005;
		assertEquals(110.49, compoundInterest(100, 5, 12, 24), delta);
		assertEquals(110.45, compoundInterest(100, 5, 4, 24), delta);
		assertEquals(110.25, compoundInterest(100, 5, 1, 24), delta);
		assertEquals(122.04, compoundInterest(100, 10, 12, 24), delta);
		assertEquals(114.98, compoundInterest(100, 7, 12, 24), delta);
		assertEquals(9895.85, compoundInterest(8956, 5, 12, 24), delta);
		assertEquals(9414.21, compoundInterest(8956, 5, 12, 12), delta);
		assertEquals(10230.57, compoundInterest(8956, 5, 12, 32), delta);
		assertEquals(8993.32, compoundInterest(8956, 5, 12, 1), delta);
	}

	@Test
	public void testInvestment() {
		double pv = 100;
		double pay = 5;
		double ar = 7;
		int y = 10;
		int ppy = 4;
		double fv = 486.33;
		double delta = 0.005;
		assertEquals(pv, investPresentValue(pay, ppy, ar, y, fv), delta);
		assertEquals(pay, investPayment(pv, ppy, ar, y, fv), delta);
		assertEquals(ar, investAnnualRate(pv, pay, ppy, y, fv), delta);
		assertEquals(y, investYears(pv, pay, ppy, ar, fv), delta);
		assertEquals(fv, investFutureValue(pv, pay, ppy, ar, y), delta);
	}

	@Test
	public void testLoan() {
		double amt = 80000;
		double ar = 0.05;
		int years = 30;
		int ppy = 12;
		double pay = 429.46;
		int ptd = 180;
		double bal = 54306.40;  // 54307.13;
		double delta = 0.005;
		assertEquals(amt, loanAmount(ar, years, ppy, pay), 0.6);
		assertEquals(ar, loanAnnualRate(amt, years, ppy, pay), 1e-5);
		assertEquals(years, loanYears(amt, ar, ppy, pay), delta);
		assertEquals(pay, loanPayment(2, amt, ar, years, ppy), delta);
		assertEquals(bal, loanBalance(2, amt, ar, years, ppy, ptd), delta);
	}
}
