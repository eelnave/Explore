package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;
import edu.byui.cit.model.Fitness;


public class Pace extends CalcFragment {
	private static final String
			KEY_DIST_UNITS = "Pace.distUnits",
			KEY_PACE_UNITS = "Pace.paceUnits";

	private final NumberFormat fmtrInt, fmtrDec;
	private EditDecimal distance;
	private EditInteger hours;
	private EditInteger minutes;
	private EditInteger seconds;
	private TextWrapper pace;
	private SpinUnit spinDistUnits;
	private SpinUnit spinPaceUnits;

	public Pace() {
		super();

		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(1);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.pace, container, false);

		distance = new EditDecimal(view, R.id.decDist, this);
		hours = new EditInteger(view, R.id.hours, this);
		minutes = new EditInteger(view, R.id.minutes, this);
		seconds = new EditInteger(view, R.id.seconds, this);
		pace = new TextWrapper(view, R.id.paceOutput);

		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.ttDistUnits,
				KEY_DIST_UNITS, this);
		spinPaceUnits = new SpinUnit(act, view, R.id.spinPaceUnits,
				Length.getInstance(), R.array.ttDistUnits,
				KEY_PACE_UNITS, this);

		EditWrapper[] inputs = { distance, hours, minutes, seconds };
		ControlWrapper[] toClear = { distance, hours, minutes, seconds, pace };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinPaceUnits.restore(prefs, Length.mile);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
		spinPaceUnits.save(editor);
	}

	protected void compute() {
		if (distance.notEmpty() &&
				(hours.notEmpty() || minutes.notEmpty() || seconds.notEmpty())) {
			Unit distUnit = spinDistUnits.getSelectedItem();
			Unit paceUnit = spinPaceUnits.getSelectedItem();
			double dist = distance.getDec();
			Property length = Length.getInstance();
			dist = length.convert(paceUnit, dist, distUnit);

			int h = 0, m = 0, s = 0;
			if (hours.notEmpty()) {
				h = hours.getInt();
			}
			if (minutes.notEmpty()) {
				m = minutes.getInt();
			}
			if (seconds.notEmpty()) {
				s = seconds.getInt();
			}

			double[] p = Fitness.calcPace(dist, h, m, s);
			pace.setText(fmtrInt.format(p[0]) +
					":" + fmtrInt.format(p[1]) +
					":" + fmtrDec.format(p[2]));
		}
		else {
			pace.clear();
		}
	}
}
