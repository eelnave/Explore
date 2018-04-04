package edu.byui.cit.calculators;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.text.NumberFormat;

import edu.byui.cit.model.Fitness;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;

import edu.byui.cit.text.RadioWrapper;

import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Mass;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public final class CaloriesBurned extends CalcFragment {
	private static final String KEY_MASS_UNITS = "CaloriesBurned.massUnits";
	private final NumberFormat fmtrDec;
	private EditDecimal decWeight;
	private SpinUnit spinMass;
	private EditInteger intTime;
	private RadioGroup grpExercise;
	private TextWrapper txtResult;

	public CaloriesBurned() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.calories_burned, container,
				false);

		spinMass = new SpinUnit(getActivity(), view, R.id.spinMassUnits,
				Mass.getInstance(), R.array.cbMassUnits, KEY_MASS_UNITS, this);

		decWeight = new EditDecimal(view, R.id.decWeight, this);
		intTime = new EditInteger(view, R.id.intTime, this);

		grpExercise = view.findViewById(R.id.grpExercise);
		new RadioWrapper(view, R.id.radWalk, this);
		new RadioWrapper(view, R.id.radRun, this);
		new RadioWrapper(view, R.id.radCycle, this);
		new RadioWrapper(view, R.id.radSwim, this);
		new RadioWrapper(view, R.id.radDance, this);
		new RadioWrapper(view, R.id.radKickbox, this);
		new RadioWrapper(view, R.id.radBasketball, this);
		new RadioWrapper(view, R.id.radLift, this);
		new RadioWrapper(view, R.id.radTv, this);

		txtResult = new TextWrapper(view, R.id.txtResult);

		EditWrapper[] inputs = { decWeight, intTime };
		ControlWrapper[] toClear = { decWeight, intTime, txtResult };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinMass.restore(prefs, Mass.pound);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Save the ID of the units chosen by
		// the user into the preferences file.
		spinMass.save(editor);
	}


	private static final class RatioPair {
		private final int id;
		private final double ratio;

		RatioPair(int id, double ratio) {
			this.id = id;
			this.ratio = ratio;
		}
	}

	private static final RatioPair[] ratios = {
			new RatioPair(R.id.radWalk, 3.5),
			new RatioPair(R.id.radRun, 12.5),
			new RatioPair(R.id.radCycle, 9.0),
			new RatioPair(R.id.radSwim, 8.0),
			new RatioPair(R.id.radDance, 6.0),
			new RatioPair(R.id.radKickbox,10.5),
			new RatioPair(R.id.radBasketball, 6.5),
			new RatioPair(R.id.radLift, 6.0),
			new RatioPair(R.id.radTv, 1.0)
	};

	private static double getRatio(int id) {
		double r = -1;
		for (RatioPair pair : ratios) {
			if (pair.id == id) {
				r = pair.ratio;
				break;
			}
		}
		return r;
	}


	@Override
	public void compute() {
		if (decWeight.notEmpty() && intTime.notEmpty()) {
			int id = grpExercise.getCheckedRadioButtonId();
			double ratio = getRatio(id);
			double weight = decWeight.getDec();
			double minutes = intTime.getInt();

			// Convert the user's weight to kilograms.
			Property mass = Mass.getInstance();
			Unit unitMass = spinMass.getSelectedItem();
			weight = mass.convert(Mass.kilogram, weight, unitMass);

			double calories = Fitness.computeCalWithMet(ratio, weight, minutes);
			String result = fmtrDec.format(calories) + " " + getString(
					R.string.calories);
			txtResult.setText(result);
		}
		else {
			clearOutput();
		}
	}
}
