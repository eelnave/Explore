package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Speed;
import edu.byui.cit.units.Unit;


public final class TravelTime extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_DIST_UNITS = "TravelTime.distUnits",
			KEY_SPE_UNITS = "TravelTime.speUnits";

	private final NumberFormat fmtrInt = NumberFormat.getIntegerInstance();
	private EditDec decDist, decSpeed;
	private SpinUnit spinDistUnits, spinSpeedUnits;
	private TextWrapper decTime;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.travel_time, container,
				false);

		// Create a wrapper object for each EditText
		// that appears in this calculator's layout.
		decDist = new EditDec(view, R.id.decDist, this);
		decSpeed = new EditDec(view, R.id.decSpeed, this);

		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.ttDistUnits,
				KEY_DIST_UNITS, this);
		spinSpeedUnits = new SpinUnit(act, view, R.id.spinSpeedUnits,
				Speed.getInstance(), R.array.ttSpeedUnits,
				KEY_SPE_UNITS, this);

		decTime = new TextWrapper(view, R.id.decTime);

		EditWrapper[] inputs = { decDist, decSpeed };
		ControlWrapper[] toClear = { decDist, decSpeed, decTime };
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
		spinSpeedUnits.restore(prefs, Speed.mph);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
		spinSpeedUnits.save(editor);
	}


	@Override
	protected void compute() {
		if (decDist.notEmpty() && decSpeed.notEmpty()) {
			double dist = decDist.getDec();
			double spe = decSpeed.getDec();

			// Get from the spinners, the units that the user chose
			// for inputting the distance and the average speed.
			Unit distUnits = spinDistUnits.getSelectedItem();
			Unit speedUnits = spinSpeedUnits.getSelectedItem();

			// Get the length and speed properties so that they can
			// be used to convert values from one unit to another.
			Property length = Length.getInstance();
			Property speed = Speed.getInstance();

			dist = length.convert(Length.km, dist, distUnits);
			spe = speed.convert(Speed.kmph, spe, speedUnits);

//			// If the user wants the results in miles per gallon,
//			// then convert, if necessary, the distance and volume
//			// of fuel entered by the user into miles and gallons.
//			if (speedUnits.getID() == Speed.mph) {
//				dist = length.convert(Length.mile, dist, distUnits);
//				spe = speed.convert(Speed.mph, spe, speedUnits);
//			}
//
//			// If the user wants the results in kilometers per mile,
//			// then convert, if necessary, the distance and speed of
//			// distance entered by the user into kilometers and kmph.
//			else if (speedUnits.getID() == Speed.kmph) {
//				dist = length.convert(Length.km, dist, distUnits);
//				spe = speed.convert(Speed.kmph, spe, speedUnits);
//			}

			double time = dist / spe;  // In hours
			int hours = (int)Math.floor(time);
			int min = (int)Math.round((time - hours) * 60);

			Resources res = getResources();
			String result = fmtrInt.format(hours) + " " +
					res.getQuantityString(R.plurals.hour, hours) + " " +
					fmtrInt.format(min) + " " +
					res.getQuantityString(R.plurals.minute, min);
			decTime.setText(result);

//			if (hours == 1) {
//				if (min == 1) {
//					String result = hours + " hour " + min + " minute";
//					decTime.setText(result);
//				}
//				else {
//					String result = hours + " hour " + min + " minutes";
//					decTime.setText(result);
//				}
//			}
//			else if (hours > 1) {
//				if (min == 1) {
//					String result = hours + " hours " + min + " minute";
//					decTime.setText(result);
//				}
//				else {
//					String result = hours + " hours " + min + " minutes";
//					decTime.setText(result);
//				}
//			}
//			else if (hours < 1){
//				if(min == 1) {
//					String result = hours + " hours " + min + " minute";
//					decTime.setText(result);
//				}
//				else {
//					String result = hours + " hours " + min + " minutes";
//					decTime.setText(result);
//				}
//			}


			//}
			//String formatTime = fmtrEffic.format(time) + " Hours";
			//decTime.setText(result);

		}
		else {
			decTime.clear();
		}
	}
}
