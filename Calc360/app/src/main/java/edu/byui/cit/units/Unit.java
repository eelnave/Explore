package edu.byui.cit.units;

import android.content.res.Resources;

import org.jetbrains.annotations.NotNull;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;


public class Unit extends Named implements Comparable<Unit> {
	private double factor;
	private int pluralsID;

	Unit(int id, String abbrev, String name, double factor) {
		super(id, abbrev, name);
		this.factor = factor;
	}

	final void initialize(Resources res)
			throws NoSuchFieldException, IllegalAccessException {
		String name = getName();
		pluralsID = Calc360.getID(R.plurals.class, name);
		String localName = res.getQuantityString(pluralsID, Integer.MAX_VALUE);
		setLocalName(localName);
	}

	final void setFactor(double factor) {
		this.factor = factor;
	}

	final double getFactor() { return factor; }

	public final int getPluralsID() {
		return pluralsID;
	}

	@Override
	public int compareTo(@NotNull Unit other) {
		return (int)Math.signum(other.factor - factor);
	}
}
