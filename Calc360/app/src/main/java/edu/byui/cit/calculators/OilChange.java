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
import edu.byui.cit.widget.TextChangeListener;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.SpinString;
import edu.byui.cit.widget.SpinUnit;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Unit;


public final class OilChange extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_PREFIX = "OilChange",
			KEY_ODOMETER_UNITS = KEY_PREFIX + ".decBegin",
			KEY_DIST_UNITS = KEY_PREFIX + ".distUnits",
			KEY_OIL_UNITS = KEY_PREFIX + ".oilUnits",
			KEY_DRIVE_UNITS = KEY_PREFIX + ".econUnits";

	private final NumberFormat fmtrDist;
	private EditDecimal decBegin, decEnd, decDist;
	private SpinUnit spinDistUnits;
	private SpinString spinOilUnits, spinDriveUnits;
	private TextWrapper decResult;
	private String result;

	public OilChange() {
		super();
		fmtrDist = NumberFormat.getInstance();
		fmtrDist.setMinimumFractionDigits(0);
		fmtrDist.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.oil_change, container,
				false);

		OdometerChanged dist = new OdometerChanged();

		// Create a wrapper object for each EditText
		// that appears in this calculator's layout.
		decBegin = new EditDecimal(view, R.id.decBegin, KEY_ODOMETER_UNITS, this);
		decEnd = new EditDecimal(view, R.id.decEnd, dist);
		decDist = new EditDecimal(view, R.id.decDist, new DistanceChanged());

		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinOilUnits = new SpinString(view, R.id.spinOilUnits,
				KEY_OIL_UNITS, this);
		spinDriveUnits = new SpinString(view, R.id.spinDriveUnits,
				KEY_DRIVE_UNITS, this);

		decResult = new TextWrapper(view, R.id.decResult);

		EditWrapper[] inputs = { decBegin, decEnd, decDist };
		WidgetWrapper[] toClear = {
				decBegin, decEnd, decDist
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	// The default values when you first start the app
	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		decBegin.restore(prefs,fmtrDist);
		spinDistUnits.restore(prefs, Length.mile);
		spinOilUnits.restore(prefs, 0);
		spinDriveUnits.restore(prefs, 0);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		if (decBegin.hasUserInput()) {
			decBegin.save(editor);
		}
		spinDistUnits.save(editor);
		spinOilUnits.save(editor);
		spinDriveUnits.save(editor);

	}


	private final class OdometerChanged implements TextChangeListener {
		@Override
		public void textChanged(EditWrapper source) {
			if (decBegin.notEmpty() || decEnd.notEmpty()) {
				decDist.clear();
			}
			compute(source);
		}
	}


	private final class DistanceChanged implements TextChangeListener {
		@Override
		public void textChanged(EditWrapper source) {
			if (decDist.notEmpty()) {
				decBegin.clear();
				decEnd.clear();
			}
			compute(source);
		}
	}


	@Override
	protected void compute(WidgetWrapper source) {
		double dist;
		double recommend = 0;
		if (decBegin.notEmpty() && decEnd.notEmpty()) {
			double begin = decBegin.getDec();
			double end = decEnd.getDec();
			dist = Math.abs(end - begin);

			decDist.setText(fmtrDist.format(dist));

			// Convert the distance to miles.
			Unit distUnit = spinDistUnits.getSelectedItem();
			Length length = Length.getInstance();
			dist = length.convert(Length.mile, dist, distUnit);

			// Perform calculations.
			if (spinOilUnits.getSelectedItem().equals("Regular")) {
				if (spinDriveUnits.getSelectedItem().equals("City Driving")) {
					recommend = 5000;
				}
				else if (spinDriveUnits.getSelectedItem().equals("Highway Driving")) {
					recommend = 7500;
				}
			}
			else if (spinOilUnits.getSelectedItem().equals("Full-Synthetic")) {
				if (spinDriveUnits.getSelectedItem().equals("City Driving")) {
					recommend = 8000;
				}
				else if (spinDriveUnits.getSelectedItem().equals("Highway Driving") ) {
					recommend = 10000;
				}
			}

			double distLeft = recommend - dist;


			if (distLeft <= 0) {
				result = "Overdue!";
			}
			else {
				if (distUnit.getID() == Length.mile ) {
					result = distLeft + " miles";
				}
				else if (distUnit.getID() == (Length.km)) {
					// Convert the results into the unit the user chose.
					double converted = length.convert(distUnit, distLeft, Length.mile);
					result = converted + " kilometers";

				}
			}

			decResult.setText(result);
		}
	}
}
