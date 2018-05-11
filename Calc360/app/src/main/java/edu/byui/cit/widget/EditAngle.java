package edu.byui.cit.widget;

import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.units.Angle;
import edu.byui.cit.units.Unit;


public final class EditAngle extends EditDecimal {
	private double rad;

	public EditAngle(View parent, int resID) {
		super(parent, resID, null, null);
	}

	public EditAngle(View parent, int resID, String prefsKey) {
		super(parent, resID, prefsKey, null);
	}

	public EditAngle(View parent, int resID, TextChangeListener listener) {
		super(parent, resID, null, listener);
	}

	public EditAngle(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey, listener);
	}


	public double getRad(Unit user) {
		rad = Angle.getInstance().convert(Angle.rad, getDec(), user);
		return rad;
	}

	public void setText(NumberFormat fmtr, Unit user) {
		setText(fmtr.format(Angle.getInstance().convert(user, rad, Angle.rad)));
	}

	public void setText(NumberFormat fmtr, Unit user, double rad) {
		this.rad = rad;
		setText(fmtr.format(Angle.getInstance().convert(user, rad, Angle.rad)));
	}
}
