package edu.byui.cit.model;

public class Fraction extends org.apache.commons.math3.fraction.Fraction {
	public Fraction(int sign, int whole, int numer, int denom) {
		super(sign * (whole * denom + numer), denom);
	}

	private Fraction(org.apache.commons.math3.fraction.Fraction apache) {
		super(apache.getNumerator(), apache.getDenominator());
	}

	public int getSign() {
		return super.getNumerator() < 0 ? -1 : 1;
	}

	public int getWhole() {
		return Math.abs(super.intValue());
	}

	public int getNumer() {
		int whole = Math.abs(super.intValue());
		int numer = Math.abs(super.getNumerator());
		int denom = Math.abs(super.getDenominator());
		return Math.abs(numer - whole * denom);
	}

	public int getDenom() {
		return super.getDenominator();
	}

	@Override
	public Fraction add(org.apache.commons.math3.fraction.Fraction right) {
		return new Fraction(super.add(right));
	}

	@Override
	public Fraction subtract(org.apache.commons.math3.fraction.Fraction right) {
		return new Fraction(super.subtract(right));
	}

	@Override
	public Fraction multiply(org.apache.commons.math3.fraction.Fraction right) {
		return new Fraction(super.multiply(right));
	}

	@Override
	public Fraction divide(org.apache.commons.math3.fraction.Fraction right) {
		return new Fraction(super.divide(right));
	}
}
