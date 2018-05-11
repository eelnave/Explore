package edu.byui.cit.widget;

import android.content.Context;
import android.view.View;

import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public final class SpinUnit extends SpinContainer<Unit> {
	public SpinUnit(View parent, int spinID, ItemSelectedListener listener) {
		super(parent, spinID, listener);
	}

	/** Initializes a spinner with all of the
	 * units inside of the given property. */
	public SpinUnit(Context ctx, View parent, int spinID,
			Property prop,
			String prefsKey, ItemSelectedListener listener) {
		super(ctx, parent, spinID, prop, prefsKey, listener);
	}

	public SpinUnit(Context ctx, View parent, int spinID,
			Property prop, int arrayID,
			String prefsKey, ItemSelectedListener listener) {
		super(ctx, parent, spinID, prop, arrayID, prefsKey, listener);
	}
}
