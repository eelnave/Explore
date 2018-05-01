package edu.byui.cit.model;


import java.util.regex.Pattern;


public final class Mathematics {
	private Mathematics() {
	}

	public static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
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
			return new double[]{-b / twoA, Math.sqrt(-discr) / twoA};
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


	public static final class Roman {
		// Don't allow objects to be created from this class.
		private Roman() {
		}

		// Roman numerals must follow this pattern.
		private static final Pattern pattern = Pattern.compile(
				"^M{0,3}(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

		private static final String[][] numerals = {
			{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
			{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
			{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
			{"", "M", "MM", "MMM"}
		};

		public static boolean isValid(String roman) {
			return pattern.matcher(roman).matches();
		}

		// Converts a base 10 Arabic number to Roman numerals.
		public static String romanFromArabic(int arabic) {
			StringBuilder roman = new StringBuilder();

			// Count down from the thousands column (3) to the ones column (0).
			for (int exponent = 3;  exponent >= 0;  exponent--) {
				int divisor = (int)Math.pow(10, exponent);
				int digit = arabic / divisor;
				roman.append(numerals[exponent][digit]);
				arabic -= digit * divisor;
			}

			return roman.toString();
		}

		// Converts Roman numerals to a base 10 Arabic number.
		public static int arabicFromRoman(String roman) {
			int arabic = 0;

			// Count down from the thousands column (3) to the ones column (0).
			for (int exponent = 3;  exponent >= 0;  exponent--) {

				// The longest possible "single digit" Roman
				// pattern has 4 characters, for example VIII
				int length = Math.min(roman.length(), 4);

				patternMatching:
				for (; length > 0; length--) {
					// Extract characters from the Roman number.
					String chars = roman.substring(0, length);

					// Find the extracted characters using linear search.
					for (int digit = 0; digit < numerals[exponent].length; digit++) {
						if (chars.equals(numerals[exponent][digit])) {

							// Found a Roman pattern, so add the corresponding
							// value to the Arabic number, and subtract the
							// Roman characters from the Roman number.
							arabic += digit * Math.pow(10, exponent);
							roman = roman.substring(length);

							break patternMatching;
						}
					}
				}
			}

			return arabic;
		}
	}
}
