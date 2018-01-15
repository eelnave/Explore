package edu.byui.cit.calculators;

import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.DataSize;


public final class DataSizeConvert extends Converter {
	public DataSizeConvert() {
		super("DataSizeConv", DataSize.getInstance(), R.array.dataSize);
	}
}
