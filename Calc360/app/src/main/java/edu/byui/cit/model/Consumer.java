package edu.byui.cit.model;

public final class Consumer {
	private Consumer() {
	}


	public static final class Ratio {
		private Ratio() {
		}

		public static double rate(double part, double total) {
			return part / total;
		}

		public static double amount(double rate, double total) {
			return total * rate;
		}

		public static double total(double rate, double total) {
			return total * (1.0 + rate);
		}



		/** Calculates the rate of change between two numbers.
		 * Ex: from 20 to 100, there is an increase of 400%. */
		public static double rateChange(double from, double to) {
			return (to - from) / from;
		}
	}


	public static final class Discount {
		private Discount() {
		}

		public static double amountSaved(
				double price, double discRate, double taxRate) {
			double origTotal = Ratio.total(taxRate, price);
			double discTotal = Ratio.total(
					taxRate, Ratio.total(-discRate, price));
			return origTotal - discTotal;
		}
	}


	public static final class Tip {
		private Tip() {
		}

		/** Computes the tip amount from a known cost, taxAmt, and total. In
		 * other words, this function is used when the consumer knows the
		 * total, including tip that he wants to spend, and the calculator is
		 * supposed to compute the tip amount for the consumer's information. */
		public static double tipAmount(
				double cost, double taxAmt, double total) {
			return total - (cost + taxAmt);
		}

		public static double total(double cost, double taxAmt, double tipAmt) {
			return cost + taxAmt + tipAmt;
		}
	}
}
