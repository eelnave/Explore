package edu.byui.cit.units;

import org.jetbrains.annotations.NotNull;


public class Unit extends Named implements Comparable<Unit> {
	private double factor;

	Unit(int id, String abbrev, String name, double factor) {
		super(id, abbrev, name);
		this.factor = factor;
	}

	void setFactor(double factor) {
		this.factor = factor;
	}

	double getFactor() { return factor; }

	@Override
	public int compareTo(@NotNull Unit other) {
		return (int)Math.signum(other.factor - factor);
	}
}
