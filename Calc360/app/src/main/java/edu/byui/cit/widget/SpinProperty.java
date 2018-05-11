package edu.byui.cit.widget;

import android.content.Context;
import android.view.View;

import edu.byui.cit.units.Property;
import edu.byui.cit.units.World;


public final class SpinProperty extends SpinContainer<Property> {
	public SpinProperty(Context ctx, View parent, int spinID, int arrayID,
			String prefsKey, ItemSelectedListener listener) {
		super(ctx, parent, spinID, World.getInstance(), arrayID,
				prefsKey, listener);
	}
}
