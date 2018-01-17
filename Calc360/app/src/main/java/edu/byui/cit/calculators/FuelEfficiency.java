package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangedHandler;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.FuelEffic;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Volume;


public final class FuelEfficiency extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_DIST_UNITS = "FuelEfficiency.distUnits",
			KEY_VOL_UNITS = "FuelEfficiency.volUnits",
			KEY_EFFIC_UNITS = "FuelEfficiency.efficUnits";

	private final NumberFormat fmtrDist, fmtrEffic;
	private EditDec decBegin, decEnd, decDist, decVol;
	private SpinUnit spinDistUnits, spinVolUnits, spinEfficUnits;
	private TextWrapper decEffic;


	public FuelEfficiency() {
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
		View view = inflater.inflate(R.layout.fuel_efficiency, container,
				false);

		ComputeDist dist = new ComputeDist();

		// Create a wrapper object for each EditText
		// that appears in this calculator's layout.
		decBegin = new EditDec(view, R.id.decBegin, dist);
		decEnd = new EditDec(view, R.id.decEnd, dist);
		decDist = new EditDec(view, R.id.decDist, this);
		decVol = new EditDec(view, R.id.decVol, this);

		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinVolUnits = new SpinUnit(act, view, R.id.spinVolUnits,
				Volume.getInstance(), R.array.feVolUnits,
				KEY_VOL_UNITS, this);
		spinEfficUnits = new SpinUnit(act, view, R.id.spinEfficUnits,
				FuelEffic.getInstance(), R.array.feEfficUnits,
				KEY_EFFIC_UNITS, this);

		decEffic = new TextWrapper(view, R.id.decEffic);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinVolUnits.restore(prefs, Volume.gallon);
		spinEfficUnits.restore(prefs, FuelEffic.mpg);
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


	private final class ComputeDist extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable s) {
			try {
				String output;
				if (decBegin.notEmpty() && decEnd.notEmpty()) {
					double begin = decBegin.getDec();
					double end = decEnd.getDec();
					double dist = Math.abs(end - begin);
					output = fmtrDist.format(dist);
				}
				else {
					output = "";
				}

				// Normally, when the computer changes a value in an EditText,
				// we don't want the text changed event to occur. However,
				// this is an unusual case. Because the user entered both the
				// starting and ending odometer readings, we want the computer
				// to now try to compute the fuel efficiency. Therefore, we
				// call getEdit and then setText instead of calling just
				// setText so that the text changed event will fire.
				decDist.getEdit().setText(output);
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}


	@Override
	protected void compute() {
		if (decDist.notEmpty() && decVol.notEmpty()) {
			double dist = decDist.getDec();
			double fuel = decVol.getDec();

			// Get from the spinners, the units that the user chose
			// for inputting the distance and the volume of fuel.
			Unit distUnits = spinDistUnits.getSelectedItem();
			Unit fuelUnits = spinVolUnits.getSelectedItem();

			// Get the length and volume properties so that they can
			// be used to convert values from one unit to another.
			Property length = Length.getInstance();
			Property volume = Volume.getInstance();

			// Get the units that the user wants for the results.
			Unit unit = spinEfficUnits.getSelectedItem();

			// If the user wants the results in miles per gallon,
			// then convert, if necessary, the distance and volume
			// of fuel entered by the user into miles and gallons.
			if (unit.getID() == FuelEffic.mpg) {
				dist = length.convert(Length.mile, dist, distUnits);
				fuel = volume.convert(Volume.gallon, fuel, fuelUnits);
			}

			// If the user wants the results in kilometers per liter,
			// then convert, if necessary, the distance and volume of
			// fuel entered by the user into kilometers and liters.
			else if (unit.getID() == FuelEffic.kpl) {
				dist = length.convert(Length.km, dist, distUnits);
				fuel = volume.convert(Volume.liter, fuel, fuelUnits);
			}

			double effic = dist / fuel;
			decEffic.setText(fmtrEffic.format(effic));
		}
		else {
			decEffic.clear();
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			decBegin.clear();
			decEnd.clear();
			decDist.clear();
			decVol.clear();
			decEffic.clear();
			decBegin.requestFocus();
		}
	}
}
