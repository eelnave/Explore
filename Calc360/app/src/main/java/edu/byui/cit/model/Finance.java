package edu.byui.cit.model;

public final class Finance {
	// Don't allow objects to be created from this class.
	private Finance() {
	}


	public static double computeROI(double netProfit, double totalInvestment) {
		return netProfit / totalInvestment * 100.0;
	}


	public static double compoundInterest(
			double deposit, double annualRate, int periodsPerYear, int years) {
		double periodicRate = annualRate / 100 / periodsPerYear;
		int numberOfPeriods = periodsPerYear * years;
		return deposit * Math.pow(1 + periodicRate, numberOfPeriods);
	}


	public static double investPresentValue(
			double pay, int ppy, double ar, double y, double fv) {
		double i = ar / 100.0 / ppy;
		int n = (int)Math.ceil(y * ppy);
		return Investment.A(pay, i, n, fv);
	}

	public static double investPayment(
			double pv, int ppy, double ar, double y, double fv) {
		// Compute periodic payment.
		double i = ar / 100.0 / ppy;
		int n = (int)Math.ceil(y * ppy);
		return Investment.P(pv, i, n, fv);
	}

	public static double investAnnualRate(
			double pv, double pay, int ppy, double y, double fv) {
		int n = (int)Math.ceil(y * ppy);
		return Investment.i(pv, pay, n, fv) * ppy * 100.0;
	}

	public static double investYears(
			double pv, double pay, int ppy, double ar, double fv) {
		double i = ar / 100.0 / ppy;
		return Investment.N(pv, pay, i, fv) / ppy;
	}

	// Never compute periods per year because
	// the user must always choose it.

	public static double investFutureValue(
			double pv, double pay, int ppy, double ar, double y) {
		double i = ar / 100.0 / ppy;
		int n = (int)Math.ceil(y * ppy);
		return Investment.F(pv, pay,  i, n);
	}

	private static final class Investment {
		private Investment() {
		}

		/* A = principal, or in other words beginning lump sum
		 * P = payment per period
		 * i = interest or growth rate per period
		 * N = total number of periods
		 * F = future value
		 *                       P
		 * (1) F = A(1 + i)^N + ---[(1 + i)^N - 1]
		 *                       i
		 */

		/*         Fi - P[(1 + i)^N - 1]
		 * (2) A = ---------------------
		 *             i(1 + i)^N
		 */
		private static double A(double P, double i, int N, double F) {
			double g = Math.pow(1 + i, N);
			return (F * i - P * (g - 1)) / (i * g);
		}

		/*           F - A(1 + i)^N
		 * (3) P = i --------------
		 *           (1 + i)^N - 1
		 */
		private static double P(double A, double i, int N, double F) {
			double g = Math.pow(1 + i, N);
			return i * (F - A * g) / (g - 1);
		}

		/* Newton's method:
		 *             f(i)
		 * iNew = i - -----
		 *            f'(i)
		 * Starting from equation (1) we can get:
		 * f(i) = Fi - (Ai + P)(1 + i)^N + P
		 * f'(i) = F - A(1 + i)^N - N(Ai + P)(1 + i)^(N-1)
		 */
		private static double i(double A, double P, int N, double F) {
			double i0 = 0.1;
			double diff;
			do {
				double g = Math.pow(1 + i0, N);
				double g1 = Math.pow(1 + i0, N - 1);
				double f = F * i0 - (A * i0 + P) * g + P;
				double fp = F - A * g - N * (A * i0 + P) * g1;
				double i1 = i0 - f / fp;
				diff = Math.abs(i1 - i0);
				i0 = i1;
			} while (diff > 1e-9);
			return i0;
		}

		/*
		 *              Fi + P
		 *         log( ------ )
		 *              Ai + P
		 * (5) N = -------------
		 *           log(1 + i)
		 */
		private static double N(double A, double P, double i, double F) {
			double numer = Math.log((F * i + P) / (A * i + P));
			double denom = Math.log(1 + i);
			return numer / denom;
		}

		/*                       P
		 * (1) F = A(1 + i)^N + ---[(1 + i)^N - 1]
		 *                       i
		 */
		private static double F(double A, double P, double i, int N) {
			double g = Math.pow(1 + i, N);
			return A * g + P * (g - 1) / i;
		}
	}


	public static double loanAmount(double ar, double y, int ppy, double pay) {
		return Loan.A(ar / ppy, (int)Math.ceil(y * ppy), pay);
	}

	public static double loanAnnualRate(double amt, double y, int ppy,
			double pay) {
		return Loan.i(amt, (int)Math.ceil(y * ppy), pay) * ppy;
	}

	public static double loanYears(double amt, double ar, int ppy, double pay) {
		return Loan.N(amt, ar / ppy, pay) / ppy;
	}

	public static double loanPayment(int fract,
			double amt, double ar, double y, int ppy) {
		return Loan.P(fract, amt, ar / ppy, (int)Math.ceil(y * ppy));
	}

	public static double loanBalance(int fract,
			double amt, double ar, double y, int ppy, int ptd) {
		double i = ar / ppy;
		int N = (int)Math.ceil(y * ppy);
		return Loan.B(amt, i, Loan.P(fract, amt, i, N), ptd);
	}


	private static final class Loan {
		/* These loan formulas come from an article written by Stan Brown
		 * at https://brownmath.com/bsci/loan.htm
		 *
		 * A = principal, or in other words amount borrowed
		 * i = interest rate per period
		 * N = total number of periods
		 * P = payment per period
		 * B = payoff balance after n periods
		 * n = payments paid to date
		 *
		 *                       P
		 * (1) B = A(1 + i)^n + ---[(1 + i)^n - 1]
		 *                       i
		 */
		private Loan() {
		}

		/*          P
		 * (2) A = ---[1 - (1 + i)^-N]
		 *          i
		 */
		static double A(double i, int N, double P) {
			return P * (1 - Math.pow(1 + i, -N)) / i;
		}

		/* Newton's method:
		 *             f(i)
		 * iNew = i - -----
		 *            f'(i)
		 * Starting from equation (1) we can get:
		 * f(i) = P[1 - (1 + i)^-N] - Ai
		 * f'(i) = NP(1 + i)^(-N-1) - A
		 */
		static double i(double A, int N, double P) {
			double i0 = 0.1;
			double diff;
			do {
				double f = P * (1 - Math.pow(1 + i0, -N)) - A * i0;
				double fp = N * P * Math.pow(1 + i0, -N - 1) - A;
				double i1 = i0 - f / fp;
				diff = Math.abs(i1 - i0);
				i0 = i1;
			} while (diff > 1e-9);
			return i0;
		}

		/*                P
		 *         log( ------ )
		 *              P - Ai
		 * (4) N = -------------
		 *           log(1 + i)
		 */
		static double N(double A, double i, double P) {
			double numer = -Math.log(1 - i * A / P);
			double denom = Math.log(1 + i);
			return numer / denom;
		}

		/*              Ai
		 * (5) P = --------------
		 *         1 - (1 + i)^-N
		 */
		static double P(int fract, double A, double i, int N) {
			double p = A * i / (1 - Math.pow(1 + i, -N));
			double mult = Math.pow(10, fract);
			return Math.ceil(p * mult) / mult;
		}

		/*                       P
		 * (1) B = A(1 + i)^n + ---[(1 + i)^n - 1]
		 *                       i
		 */
		static double B(double A, double i, double P, int ptd) {
			double g = Math.pow(1 + i, ptd);
			return A * g - P * (g - 1) / i;
		}
	}
}
