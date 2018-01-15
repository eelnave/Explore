package edu.byui.cit.model;

public final class Consumer {
	private Consumer() {
	}


	public static final class Rate {
		private Rate() {
		}

		public static double rate(double part, double total) {
			return part / total;
		}

		public static double amount(double total, double rate) {
			return total * rate;
		}

		public static double total(double total, double rate) {
			return total * (1.0 + rate);
		}

		// Calculates the rate of change between two numbers.
		// Ex: from 20 to 100, there is an increase of 400%.
		public static double rateChange(double from, double to) {
			return (to - from) / from;
		}
	}


//	public static final class SalesTax {
//		private SalesTax() {
//		}
//
//		public static double price(double taxAmt, double total) {
//			return total - taxAmt;
//		}
//
//		public static double taxRate(double price, double taxAmt) {
//			return taxAmt / price;
//		}
//
//		public static double taxAmt(double price, double taxRate) {
//			return price * taxRate;
//		}
//
//		public static double total(double price, double taxRate) {
//			return price * (1.0 + taxRate);
//		}
//	}


	public static final class Discount {
		private Discount() {
		}

		public static double discountAmount(double price, double discRate) {
			return price * discRate;
		}

		public static double discountedPrice(double price, double discRate) {
			return price * (1.0 - discRate);
		}

		public static double amountSaved(double price, double discRate, double taxRate) {
			double origTotal = Rate.total(price, taxRate);
			double discTotal = Rate.total(discountedPrice(price, discRate), taxRate);
			return origTotal - discTotal;
		}
	}


	public static final class Tip {
		private Tip() {
		}

		public static double computeTipAmount(double cost, double rate) {
			return cost * rate;
		}

		public static double computeTotal(double cost, double tax, double amt) {
			return cost + tax + amt;
		}

		public static double computeTipRate(double amt, double cost) {
			return amt / cost;
		}

		public static double computeTipAmount(
				double cost, double tax, double total) {
			return total - (cost + tax);
		}
	}
}
