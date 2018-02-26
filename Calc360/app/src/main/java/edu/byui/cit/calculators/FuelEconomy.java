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
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangeHandler;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.FuelEcon;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Volume;


public final class FuelEconomy extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_DIST_UNITS = "FuelEconomy.distUnits",
			KEY_VOL_UNITS = "FuelEconomy.volUnits",
			KEY_EFFIC_UNITS = "FuelEconomy.efficUnits";

	private final NumberFormat fmtrDist, fmtrEffic;
	private EditDecimal decBegin, decEnd, decDist, decVol;
	private SpinUnit spinDistUnits, spinVolUnits, spinEfficUnits;
	private TextWrapper decEcon;


	public FuelEconomy() {
		super();
		fmtrDist = NumberFormat.getInstance();
		fmtrEffic = NumberFormat.getInstance();
		fmtrDist.setMinimumFractionDigits(0);
		fmtrDist.setMaximumFractionDigits(1);
		fmtrEffic.setMinimumFractionDigits(1);
		fmtrEffic.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.fuel_economy, container,
				false);

		OdometerChanged dist = new OdometerChanged();

		// Create a wrapper object for each EditText
		// that appears in this calculator's layout.
		decBegin = new EditDecimal(view, R.id.decBegin, dist);
		decEnd = new EditDecimal(view, R.id.decEnd, dist);
		decDist = new EditDecimal(view, R.id.decDist, new DistanceChanged());
		decVol = new EditDecimal(view, R.id.decVol, this);

		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinVolUnits = new SpinUnit(act, view, R.id.spinVolUnits,
				Volume.getInstance(), R.array.feVolUnits,
				KEY_VOL_UNITS, this);
		spinEfficUnits = new SpinUnit(act, view, R.id.spinEconUnits,
				FuelEcon.getInstance(), R.array.feEconUnits,
				KEY_EFFIC_UNITS, this);

		decEcon = new TextWrapper(view, R.id.decEcon);

		EditWrapper[] inputs = { decBegin, decEnd, decDist, decVol };
		ControlWrapper[] toClear = {
				decBegin, decEnd, decDist, decVol, decEcon
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinVolUnits.restore(prefs, Volume.gallon);
		spinEfficUnits.restore(prefs, FuelEcon.mpg);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
		spinVolUnits.save(editor);
		spinEfficUnits.save(editor);
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
		}
		else if (decDist.notEmpty()) {
			dist = decDist.getDec();
		}

		if (dist > 0 && decVol.notEmpty()) {
			double vol = decVol.getDec();

			// Get from the spinners, the units that the user chose
			// for inputting the distance and the volume of fuel.
			Unit distUnits = spinDistUnits.getSelectedItem();
			Unit volUnits = spinVolUnits.getSelectedItem();

			// Get the length and volume properties so that they can
			// be used to convert values from one unit to another.
			Property length = Length.getInstance();
			Property volume = Volume.getInstance();

			// Get the units that the user wants for the results.
			Unit unit = spinEfficUnits.getSelectedItem();

			// If the user wants the results in miles per gallon,
			// then convert, if necessary, the distance and volume
			// of fuel entered by the user into miles and gallons.
			if (unit.getID() == FuelEcon.mpg) {
				dist = length.convert(Length.mile, dist, distUnits);
				vol = volume.convert(Volume.gallon, vol, volUnits);
			}

			// If the user wants the results in kilometers per liter,
			// then convert, if necessary, the distance and volume of
			// fuel entered by the user into kilometers and liters.
			else if (unit.getID() == FuelEcon.kpl) {
				dist = length.convert(Length.km, dist, distUnits);
				vol = volume.convert(Volume.liter, vol, volUnits);
			}

			double effic = dist / vol;
			decEcon.setText(fmtrEffic.format(effic));
		}
		else {
			decEcon.clear();
		}
	}
}