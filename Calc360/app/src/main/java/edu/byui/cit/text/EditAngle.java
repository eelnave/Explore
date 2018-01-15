package edu.byui.cit.text;

import android.text.TextWatcher;
import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.units.Angle;
import edu.byui.cit.units.Unit;


public final class EditAngle extends EditDec {
	private double rad;

	public EditAngle(View parent, int resID, TextWatcher watcher) {
		super(parent, resID, watcher);
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
