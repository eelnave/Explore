package edu.byui.cit.calculators;

import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Temperature;


public final class TemperatureConvert extends Converter {
	public TemperatureConvert() {
		super("TempConv",
				Temperature.getInstance(), R.array.kitchenTemperature);
		this.fmtrDec.setMaximumFractionDigits(2);
	}
}
