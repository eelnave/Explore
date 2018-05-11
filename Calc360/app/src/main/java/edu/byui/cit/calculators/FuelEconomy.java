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
import edu.byui.cit.widget.SpinUnit;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.units.FuelEcon;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Volume;


public final class FuelEconomy extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_PREFIX = "FuelEconomy",
			KEY_DIST_UNITS = KEY_PREFIX + ".distUnits",
			KEY_VOL_UNITS = KEY_PREFIX + ".volUnits",
			KEY_ECON_UNITS = KEY_PREFIX + ".econUnits";

	private final NumberFormat fmtrDist, fmtrEcon;
	private EditDecimal decBegin, decEnd, decDist, decVol;
	private SpinUnit spinDistUnits, spinVolUnits, spinEconUnits;
	private TextWrapper decEcon;


	public FuelEconomy() {
		super();
		fmtrDist = NumberFormat.getInstance();
		fmtrEcon = NumberFormat.getInstance();
		fmtrDist.setMinimumFractionDigits(0);
		fmtrDist.setMaximumFractionDigits(1);
		fmtrEcon.setMinimumFractionDigits(1);
		fmtrEcon.setMaximumFractionDigits(2);
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
		spinEconUnits = new SpinUnit(act, view, R.id.spinEconUnits,
				FuelEcon.getInstance(), R.array.fuelEcon,
				KEY_ECON_UNITS, this);

		decEcon = new TextWrapper(view, R.id.decEcon);

		EditWrapper[] inputs = { decBegin, decEnd, decDist, decVol };
		WidgetWrapper[] toClear = {
				decBegin, decEnd, decDist, decVol, decEcon
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinVolUnits.restore(prefs, Volume.gallon);
		spinEconUnits.restore(prefs, FuelEcon.mpg);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
		spinVolUnits.save(editor);
		spinEconUnits.save(editor);
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

			// Get the units that the user wants for the results.
			Unit fuelEconUnits = spinEconUnits.getSelectedItem();

			dist =  Length.getInstance().convert(Length.km, dist, distUnits);
			vol = Volume.getInstance().convert(Volume.liter, vol, volUnits);
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
	}
}
