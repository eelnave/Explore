package edu.byui.cit.model;

import java.util.Arrays;


public final class Statistics {
	public final double min, first, second, third, max;
	public final double sum, mean, var, stdev;
	public final int count;

	public Statistics(double[] data) {
		int length = data.length;
		count = length;

		if (length == 0) {
			min = first = second = third = max = sum = mean = var = stdev = Double.NaN;
		}
		else if (length == 1) {
			min = first = second = third = max = sum = mean = data[0];
			var = stdev = 0;
		}
		else {
			/* Sort the data so that all seemingly insignificant
			 * values such as 0.000000003 will be at the beginning
			 * of the array and their contribution to the mean and
			 * variance of the data will not be lost because of
			 * the precision of the CPU. */
			Arrays.sort(data, 0, length);

			/* Since the data is now sorted, the minimum value
			 * is at the beginning of the array, the median
			 * value is in the middle of the array, and the
			 * maximum value is at the end of the array. */
			min = data[0];

			/* Compute the 1st, 2nd, and 3rd quartiles. The
			 * 2nd quartile is also known as the median. */
			int half = length / 2;
			if (isEven(length)) {
				first = median(data, 0, half);
				second = median(data, 0, length);
				third = median(data, half, length);
			}
			else {
				int one, three;
				double w1;
				if ((length - 1) % 4 == 0) {
					int n = (length - 1) / 4;
					one = n - 1;
					three = n * 3;
					w1 = 0.25;
				}
				else {
					int n = (length - 3) / 4;
					one = n;
					three = n * 3 + 1;
					w1 = 0.75;
				}
				double w2 = 1.0 - w1;
				first = data[one] * w1 + data[one + 1] * w2;
				second = data[half];
				third = data[three] * w2 + data[three + 1] * w1;
			}

			max = data[length - 1];

			/* Compute the mean and variance using
			 * a numerically stable algorithm. */
			double sqsum = 0.0;
			double m = data[0];
			for (int i = 1; i < length; ++i) {
				double x = data[i];
				double delta = x - m;
				m += delta / (i + 1.0);
				double weight = i / (i + 1.0);
				sqsum += delta * delta * weight;
			}
			mean = m;
			sum = mean * length;
			var = sqsum / length;
			stdev = Math.sqrt(var);
		}
	}

	private static double median(double[] data, int start, int end) {
		int length = end - start;
		int half = start + length / 2;
		double med;
		if (isEven(length)) {
			med = (data[half - 1] + data[half]) / 2.0;
		}
		else {
			med = data[half];
		}
		return med;
	}

	private static boolean isEven(int n) {
		return (n % 2) == 0;
	}


	/** Computes the correlation coefficient of two samples. */
	public static double correlation(double[] dataX, double[] dataY) {
		double sumX, sumY, sumX2, sumY2, sumXY;
		sumX = sumY = sumX2 = sumY2 = sumXY = 0.0;
		for (int i = 0;  i < dataX.length;  ++i) {
			double x = dataX[i];
			double y = dataY[i];
			sumX += x;
			sumY += y;
			sumX2 += x * x;
			sumY2 += y * y;
			sumXY += x * y;
		}
		double n = dataX.length;
		double meanX = sumX / n;
		double meanY = sumY / n;
		double sdevX = Math.sqrt(sumX2 / n - meanX * meanX);
		double sdevY = Math.sqrt(sumY2 / n - meanY * meanY);
		double covar = sumXY / n - meanX * meanY;
		return covar / (sdevX * sdevY);
	}

	public static double binDistProb(int n, int x, double p) {

		double probability;
		int numerator = 1;
		int xDenominator = 1;
		int nxDenominator = 1;
		double denominator;

		for (int i = 1; i <= n; i++) {
			numerator *= i;
		}

		for (int i = 1; i <= x; i++) {
			xDenominator *= i;
		}

		for (int i = 1; i <= (n - x); i++) {
			nxDenominator *= i;
		}

		denominator = xDenominator * nxDenominator;

		probability = numerator / denominator * Math.pow(p, x) *
				Math.pow((1-p), (n-x));


		return probability;
	}
}
