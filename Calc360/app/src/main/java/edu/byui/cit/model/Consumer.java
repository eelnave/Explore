package edu.byui.cit.model;

public final class Consumer {
	private Consumer() {
	}


	public static final class Ratio {
		private Ratio() {
		}

		public static double rate(double part, double whole) {
			return part / whole;
		}

		public static double amount(double rate, double subtotal) {
			return subtotal * rate;
		}

		public static double total(double rate, double subtotal) {
			return subtotal * (1.0 + rate);
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


	public static final class StreamingCost {
		private StreamingCost() {
		}

		public static double durationMovie2_5(double quantity) {
			return quantity * 2.5;
		}

		public static double durationMovie2(double quantity) {
			return quantity * 2;
		}

		public static double durationMovie1_5(double quantity) {
			return quantity * 1.5;
		}

		public static double durationEpisode45(double quantity) {
			return quantity * 0.75;
		}

		public static double durationEpisode22(double quantity) {
			return quantity * 0.37;
		}
	}
}
