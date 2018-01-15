package edu.byui.cit.calculators;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.text.NumberFormat;

import edu.byui.cit.model.Fitness;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Mass;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public final class CaloriesBurned extends CalcFragment {
	private static final String KEY_MASS_UNITS = "CaloriesBurned.massUnits";

	private final NumberFormat fmtrDec;
	private EditDec decWeight;
	private SpinUnit spinMass;
	private EditInt intTime;
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
		SharedPreferences prefs = getActivity().getPreferences(
				Context.MODE_PRIVATE);
		spinMass.restore(prefs, Mass.pound);

		decWeight = new EditDec(view, R.id.decWeight, this);
		intTime = new EditInt(view, R.id.intTime, this);

		grpExercise = view.findViewById(R.id.grpExercise);
		new RadioWrapper(view, R.id.radWeight, this);
		new RadioWrapper(view, R.id.radWalk, this);
		new RadioWrapper(view, R.id.radRun, this);
		new RadioWrapper(view, R.id.radSwim, this);

		txtResult = new TextWrapper(view, R.id.txtResult);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	private static final class RatioPair {
		private final int id;
		private final double ratio;

		RatioPair(int id, double ratio) {
			this.id = id;
			this.ratio = ratio;
		}

		/**
		 * Linear search to find a ratio from
		 * the corresponding RadioButton id.
		 */
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

		private static final RatioPair[] ratios = {
				new RatioPair(R.id.radWeight, 0.024),
				new RatioPair(R.id.radWalk, 0.03),
				new RatioPair(R.id.radRun, 0.05),
				new RatioPair(R.id.radSwim, 0.07)
		};
	}


	@Override
	public void compute() {
		try {
			if (decWeight.notEmpty() && intTime.notEmpty()) {
				int id = grpExercise.getCheckedRadioButtonId();
				double ratio = RatioPair.getRatio(id);
				double weight = decWeight.getDec();
				double minutes = intTime.getInt();

				// Get from the spinner, the units that
				// the user chose for inputting his weight.
				Unit unitMass = spinMass.getSelectedItem();
				if (unitMass.getID() == Mass.kilogram) {
					Property mass = Mass.getInstance();
					weight = mass.convert(Mass.pound, weight, unitMass);
				}

				double calories = Fitness.computeCalories(ratio, weight,
						minutes);
				String result = fmtrDec.format(calories) + " " + getString(
						R.string.calories);
				txtResult.setText(result);
			}
		}
		catch (Exception ex) {
			txtResult.setText(getResources().getString(R.string.invalidN));
		}
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Save the ID of the units chosen by
		// the user into the preferences file.
		spinMass.save(editor);
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			intTime.clear();
			txtResult.clear();
			decWeight.requestFocus();
		}
	}
}
