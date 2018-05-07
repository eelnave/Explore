package edu.byui.cit.calculators;

import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Area;


public final class AreaConvert extends Converter {
	public AreaConvert() {
		super("AreaConvert", Area.getInstance(), R.array.kitchenArea);
	}
}
