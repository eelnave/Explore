package edu.byui.cit.calc360;

/**  A CalcDescriptor holds information about a calculator. */
final class CalcDescriptor extends Descriptor {
	CalcDescriptor(int calcID, int titleID, int iconID,
			Class<? extends CITFragment> calcClass) {
		super(calcID, titleID, iconID, calcClass, 0);
	}

	CalcDescriptor(int calcID, int titleID, int iconID,
			Class<? extends CITFragment> calcClass, int explainID) {
		super(calcID, titleID, iconID, calcClass, explainID);
	}
}
