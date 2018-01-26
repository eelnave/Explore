package edu.byui.cit.text;

import android.content.Context;
import android.view.View;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public final class SpinUnit extends SpinContainer<Unit> {
	public SpinUnit(View parent, int spinID, CalcFragment calculator) {
		super(parent, spinID, calculator);
	}

	public SpinUnit(View parent, int spinID, ItemSelectedListener listener) {
		super(parent, spinID, listener);
	}

	/** Initializes a spinner with all of the
	 * units inside of the given property. */
	public SpinUnit(Context ctx, View parent, int spinID,
			Property prop, String prefsKey, CalcFragment calculator) {
		super(ctx, parent, spinID, prop, prefsKey, calculator);
	}

	public SpinUnit(Context ctx, View parent, int spinID,
			Property prop, int arrayID, String prefsKey,
			CalcFragment calculator) {
		super(ctx, parent, spinID, prop, arrayID, prefsKey, calculator);
	}

	public SpinUnit(Context ctx, View parent, int spinID,
			Property prop, int arrayID, String prefsKey,
			ItemSelectedListener listener) {
		super(ctx, parent, spinID, prop, arrayID, prefsKey, listener);
	}
}
