package edu.byui.cit.calc360;

/**  A CalcDescriptor holds information about a calculator */
final class CalcDescriptor extends Descriptor {
	CalcDescriptor(int descripID, int titleID, int iconID,
			Class<? extends OmniFragment> calcClass) {
		super(descripID, titleID, iconID, calcClass);
	}
}
