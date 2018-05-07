package edu.byui.cit.calculators;

import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Mass;


public final class MassConvert extends Converter {
	public MassConvert() {
		super("MassConvert", Mass.getInstance(), R.array.kitchenMass);
	}
}
