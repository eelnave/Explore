package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Speed;
import edu.byui.cit.units.Time;


public class RunPace extends CalcFragment {

	/*Initialize data types for
	Distance
	Speed
	Time
	These factors will go into the Calculations for pace, distance, and speed
	 */
	private static final String
			KEY_DIST_UNITS = "RunPace.distUnits";

	private EditDecimal Distance;
	private EditDecimal Speed;
	private TextWrapper Time;
	private SpinUnit spinDistUnits;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.run_pace, container,
				false);

		Activity spin = getActivity();
		spinDistUnits = new SpinUnit(spin, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.ttDistUnits,
				KEY_DIST_UNITS, this);

		Time = new TextWrapper(view, R.id.Time);
		return null;
	}

	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDistUnits.restore(prefs, Length.mile);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		// Get from the spinners, the units chosen by the user.
		spinDistUnits.save(editor);
	}
}
