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
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangeHandler;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.ChangeOil;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Unit;


public final class OilChange extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_DIST_UNITS = "OilChange.distUnits",
			KEY_Oil_UNITS = "OilChange.oilTypes",
			KEY_DRIVE_UNITS = "OilChange.econUnits";

	private final NumberFormat fmtrDist;
	private EditDecimal decBegin, decEnd, decDist;
	private SpinUnit spinDistUnits;
	private SpinString spinOilUnits, spinDriveUnits;
	private TextWrapper decDrive, decOil, decResult;


	public OilChange() {
		super();
		fmtrDist = NumberFormat.getInstance();
		//	fmtrEcon = NumberFormat.getInstance();
		fmtrDist.setMinimumFractionDigits(0);
		fmtrDist.setMaximumFractionDigits(1);
		//	fmtrEcon.setMinimumFractionDigits(1);
		//	fmtrEcon.setMaximumFractionDigits(2);
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
		decBegin = new EditDecimal(view, R.id.decBegin, dist);
		decEnd = new EditDecimal(view, R.id.decEnd, dist);
		decDist = new EditDecimal(view, R.id.decDist, new DistanceChanged());

		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinOilUnits = new SpinString(view, R.id.spinOilUnits,
				KEY_Oil_UNITS, this);
		spinDriveUnits = new SpinString(view, R.id.spinDriveUnits,
				KEY_DRIVE_UNITS, this);

		decResult = new TextWrapper(view, R.id.decResult);

		EditWrapper[] inputs = { decBegin, decEnd, decDist };
		ControlWrapper[] toClear = {
				decBegin, decEnd, decDist, decOil, decDrive
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinOilUnits.restore(prefs, 0);
		spinDriveUnits.restore(prefs, 0);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
		spinOilUnits.save(editor);
		spinDriveUnits.save(editor);
	}


	private final class OdometerChanged extends TextChangeHandler {
		@Override
		public void textChanged(CharSequence s) {
			if (decBegin.notEmpty() || decEnd.notEmpty()) {
				decDist.clear();
			}
			callCompute();
		}
	}


	private final class DistanceChanged extends TextChangeHandler {
		@Override
		public void textChanged(CharSequence s) {
			if (decDist.notEmpty()) {
				decBegin.clear();
				decEnd.clear();
			}
			callCompute();
		}
	}


	@Override
	protected void compute() {
		double dist = 0;
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

			// Convert the results into the unit the user chose.
//			result = length.convert(distUnit, result, Length.mile);
		} /*
		else if (decDist.notEmpty()) {
			dist = decDist.getDec();
		}

		if (dist > 0 && decVol.notEmpty()) {
			double vol = decVol.getDec();

			// Get from the spinners, the units that the user chose
			// for inputting the distance and the volume of fuel.
			Unit distUnits = spinDistUnits.getSelectedItem();
			Unit volUnits = spinOilTypes.getSelectedItem();

			// Get the units that the user wants for the results.
			Unit fuelEconUnits = spinEconUnits.getSelectedItem();

			dist =  Length.getInstance().convert(Length.km, dist, distUnits);
			//vol = Volume.getInstance().convert(Volume.liter, vol, volUnits);
			double econ = dist / vol;
			econ = FuelEcon.getInstance().convert(fuelEconUnits, econ, FuelEcon.kpl);

//			// Get the length and volume properties so that they can
//			// be used to convert values from one unit to another.
//			Property length = Length.getInstance();
//			Property volume = Volume.getInstance();
//
//			// If the user wants the results in miles per gallon,
//			// then convert, if necessary, the distance and volume
//			// of fuel entered by the user into miles and gallons.
//			if (fuelEconUnits.getID() == FuelEcon.mpg) {
//				dist = length.convert(Length.mile, dist, distUnits);
//				vol = volume.convert(Volume.gallon, vol, volUnits);
//			}
//
//			// If the user wants the results in kilometers per liter,
//			// then convert, if necessary, the distance and volume of
//			// fuel entered by the user into kilometers and liters.
//			else if (fuelEconUnits.getID() == FuelEcon.kpl) {
//				dist = length.convert(Length.km, dist, distUnits);
//				vol = volume.convert(Volume.liter, vol, volUnits);
//			}
//
//			double econ = dist / vol;
			decEcon.setText(fmtrEcon.format(econ));
		}
		else {
			decEcon.clear();
		}
	} */
	}
}
