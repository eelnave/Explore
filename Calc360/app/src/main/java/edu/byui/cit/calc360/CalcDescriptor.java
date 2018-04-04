package edu.byui.cit.calc360;

/**  A CalcDescriptor holds information about a folder or a calculator. */
final class CalcDescriptor extends Descriptor {
	CalcDescriptor(int calcID, int titleID, int iconID,
			Class<? extends InfoFragment> calcClass) {
		super(calcID, titleID, iconID, calcClass);
	}
}
