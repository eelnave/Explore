package edu.byui.cit.calculators;

import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Volume;


public final class VolumeConvert extends Converter {
	public VolumeConvert() {
		super("VolConv", Volume.getInstance(), R.array.kitchenVolume);
	}
}
