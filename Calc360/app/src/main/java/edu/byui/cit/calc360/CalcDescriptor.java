package edu.byui.cit.calc360;

/**  A CalcDescriptor holds information about a folder or a calculator. */
final class CalcDescriptor extends Descriptor {
	CalcDescriptor(String calcID) {
		super(calcID, 0, 0, null);
	}

	CalcDescriptor(int titleID, int iconID,
			Class<? extends CITFragment> calcClass) {
		super(calcClass.getSimpleName(), titleID, iconID, calcClass);
	}
}
