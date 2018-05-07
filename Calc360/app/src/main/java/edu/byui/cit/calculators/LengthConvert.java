package edu.byui.cit.calculators;

import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Length;


public final class LengthConvert extends Converter {
	public LengthConvert() {
		super("LengthConvert", Length.getInstance(), R.array.kitchenLength);
	}
}
