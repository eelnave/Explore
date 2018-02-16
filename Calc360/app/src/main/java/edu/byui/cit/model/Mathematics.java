package edu.byui.cit.model;


public final class Mathematics {
	private Mathematics() {
	}

	public static long lcm(long a, long b) {
		return a / gcd(a, b) *  b;
	}

	public static long gcd(long a, long b) {
		// Ensure a and b are not negative.
		a = Math.abs(a);
		b = Math.abs(b);

		// Loop until the greatest common divisor is found.
		do {
			long r = a % b;
			a = b;
			b = r;
		} while (b != 0);

		return a;
	}


	public static final class Quadratic {
		private Quadratic() {
		}

		public static double discrim(double a, double b, double c) {
			return b * b - (4.0 * a * c);
		}

		public static double onlyRoot(double a, double b) {
			return -b / (2.0 * a);
		}

		public static double root1(double a, double b, double discr) {
			return (-b - Math.sqrt(discr)) / (2.0 * a);
		}

		public static double root2(double a, double b, double discr) {
			return (-b + Math.sqrt(discr)) / (2.0 * a);
		}

		public static double[] imaginary(double a, double b, double discr) {
			double twoA = 2.0 * a;
			return new double[]{ -b / twoA, Math.sqrt(-discr) / twoA };
		}

		public static double anyRoot(double a, double b, double c) {
			double discr = discrim(a, b, c);
			double any = Double.NaN;
			if (discr > 0) {
				any = root1(a, b, discr);
			}
			else if (discr == 0) {
				any = onlyRoot(a, b);
			}
			return any;
		}

		static double posRoot(double a, double b, double c) {
			double discr = discrim(a, b, c);
			double pos = Double.NaN;
			if (discr >= 0) {
				double r;
				if (discr > 0) {
					r = root1(a, b, discr);
					if (r < 0) {
						r = root2(a, b, discr);
					}
				}
				else {
					r = onlyRoot(a, b);
				}
				if (r >= 0) {
					pos = r;
				}
			}
			return pos;
		}

		static double smallerRoot(double a, double b, double c) {
			double discr = discrim(a, b, c);
			double small = Double.NaN;
			if (discr >= 0) {
				if (discr > 0) {
					small = root1(a, b, discr);
					double other = root2(a, b, discr);
					if (other < small) {
						small = other;
					}
				}
				else {
					small = onlyRoot(a, b);
				}
			}
			return small;
		}

		static double largerRoot(double a, double b, double c) {
			double discr = discrim(a, b, c);
			double large = Double.NaN;
			if (discr >= 0) {
				if (discr > 0) {
					large = root1(a, b, discr);
					double other = root2(a, b, discr);
					if (other > large) {
						large = other;
					}
				}
				else {
					large = onlyRoot(a, b);
				}
			}
			return large;
		}
	}
}
