package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangeHandler;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.FuelEffic;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Speed;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Volume;


public final class TravelTime extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_DIST_UNITS = "FuelEfficiency.distUnits",
			KEY_SPE_UNITS = "FuelEfficiency.speUnits";

	private final NumberFormat fmtrDist, fmtrEffic;
	private EditDec decBegin, decEnd, decDist, decSpe;
	private SpinUnit spinDistUnits, spinSpeUnits;
	private TextWrapper decTime;


	public TravelTime() {
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
		View view = inflater.inflate(R.layout.travel_time, container,
				false);

		//OdometerChanged dist = new OdometerChanged();

		// Create a wrapper object for each EditText
		// that appears in this calculator's layout.
		//decBegin = new EditDec(view, R.id.decBegin, dist);
		//decEnd = new EditDec(view, R.id.decEnd, dist);
		decDist = new EditDec(view, R.id.decDist, this);
		decSpe = new EditDec(view, R.id.decSpe, this);

		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinSpeUnits = new SpinUnit(act, view, R.id.spinSpeUnits,
				Speed.getInstance(), R.array.ttSpeedUnits,
				KEY_SPE_UNITS, this);

		decTime = new TextWrapper(view, R.id.decTime);

		EditWrapper[] inputs = { decDist, decSpe };
		TextWrapper[] outputs = { decTime };
		initialize(view, inputs, outputs, R.id.btnClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinSpeUnits.restore(prefs, Speed.mph);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
		spinSpeUnits.save(editor);
	}


	/*private final class OdometerChanged extends TextChangeHandler {
		@Override
		public void afterChanged(Editable s) {
			if (decBegin.notEmpty() || decEnd.notEmpty()) {
				decDist.clear();
			}
			callCompute();
		}
	}


	private final class DistanceChanged extends TextChangeHandler {
		@Override
		public void afterChanged(Editable s) {
			if (decDist.notEmpty()) {
				decBegin.clear();
				decEnd.clear();
			}
			callCompute();
		}
	}
*/

	@Override
	protected void compute() {
		double dist = 0;
		if (decDist.notEmpty()) {
			dist = decDist.getDec();
		}

		if (dist > 0 && decSpe.notEmpty()) {
			double spe = decSpe.getDec();

			/*
			// Get from the spinners, the units that the user chose
			// for inputting the distance and the volume of fuel.
			Unit distUnits = spinDistUnits.getSelectedItem();
			Unit speUnits = spinSpeUnits.getSelectedItem();

			// Get the length and volume properties so that they can
			// be used to convert values from one unit to another.
			Property length = Length.getInstance();
			Property speed = Speed.getInstance();

			// Get the units that the user wants for the results.
			Unit unit = spinEfficUnits.getSelectedItem();

			// If the user wants the results in miles per gallon,
			// then convert, if necessary, the distance and volume
			// of fuel entered by the user into miles and gallons.
			if (unit.getID() == FuelEffic.mpg) {
				dist = length.convert(Length.mile, dist, distUnits);
				spe = speed.convert(Speed.mph, spe, speUnits);
			}

			// If the user wants the results in kilometers per mile,
			// then convert, if necessary, the distance and speed of
			// distance entered by the user into kilometers and kmph.
			else if (unit.getID() == FuelEffic.kpl) {
				dist = length.convert(Length.km, dist, distUnits);
				spe = speed.convert(Speed.kmph, spe, speUnits);
			}
*/
			double time = dist / spe ;
			decTime.setText(fmtrEffic.format(time));

		}
		else {
			decTime.clear();
		}
	}
}
