package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.model.Fitness;


public class RunPace extends CalcFragment {

	/*Initialize data types for
	Distance
	Speed
	Time
	These factors will go into the Calculations for pace, distance, and time
	 */
	private static final String
			KEY_DIST_UNITS = "RunPace.distUnits";

	private EditDecimal distance;
	private EditInteger hours;
	private EditInteger minutes;
	private EditInteger seconds;
	private TextWrapper pace;
	private SpinUnit spinDistUnits;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.run_pace, container, false);


		distance = new EditDecimal(view, R.id.distInput, this);
		hours = new EditInteger(view, R.id.hours, this);
		minutes = new EditInteger(view, R.id.minutes, this);
		seconds = new EditInteger(view, R.id.seconds, this);
		pace = new TextWrapper(view, R.id.paceOutput);

		Activity spin = getActivity();
		spinDistUnits = new SpinUnit(spin, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.ttDistUnits,
				KEY_DIST_UNITS, this);

		EditWrapper[] inputs = {distance, hours, minutes, seconds};
		ControlWrapper[] toClear = {distance, hours, minutes, seconds, pace};
		
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
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

	protected void compute(){
		double d = distance.getDec();
		int h = hours.getInt();
		int m = minutes.getInt();
		int s = seconds.getInt();

		String output = Fitness.calcPace(d, h, m, s);

		pace.setText(output);
	}
}
