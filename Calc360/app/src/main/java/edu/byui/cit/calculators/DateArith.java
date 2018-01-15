package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import java.text.DateFormat;
import java.util.Calendar;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Time;


public final class DateArith extends CalcFragment
		implements OnDateChangedListener {
	private static final String KEY_UNITS = "DateArith.durationType";
	private final DateFormat fmtrDate;

	private DatePicker datePicker;
	private EditInt intDuration;
	private SpinUnit spinDuration;
	private TextWrapper dateResult;

	public DateArith() {
		super();
		fmtrDate = DateFormat.getDateInstance(DateFormat.MEDIUM);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.date_arith, container, false);

		datePicker = view.findViewById(R.id.datePicker);
		Calendar calendar = Calendar.getInstance();
		datePicker.init(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE),
				this);

		new ButtonWrapper(view, R.id.btnToday, this);
		intDuration = new EditInt(view, R.id.intDuration, this);

		// Set up the spinner to show the right values
		Activity act = getActivity();
		spinDuration = new SpinUnit(act, view, R.id.spinDuration, Time.getInstance(), R.array.durationChoices, KEY_UNITS, this);

		// Get the saved value for the spinner, if there is one
		SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
		spinDuration.restore(prefs, Time.day);

		dateResult = new TextWrapper(view, R.id.dateResult);

		new ButtonWrapper(view, R.id.btnClear,
				new OnClickListener() {
					@Override
					public void onClick(View button) {
						intDuration.clear();
						dateResult.clear();
						intDuration.requestFocus();
					}
				}
		);

		return view;
	}


	@Override
	public void onDateChanged(
			DatePicker datePicker, int year, int month, int dayOfMonth) {
		callCompute();
	}

	/** Handles a click on the Today button. */
	@Override
	public void onClick(View button) {
		// Get today's date.
		Calendar calendar = Calendar.getInstance();
		// Set the datePicker to the current date
		datePicker.updateDate(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE)
		);
	}

	@Override
	protected void compute() {
		try {
			if (intDuration.notEmpty()) {
				// Reset the date
				Calendar calendar = Calendar.getInstance();
				// Set the date to what the user has selected
				calendar.set(datePicker.getYear(), datePicker.getMonth(),
						datePicker.getDayOfMonth());

				// Find out what unit we are using and add it
				int units = spinDuration.getSelectedItem().getID();
				int durat = intDuration.getInt();
				int interval;
				switch (units) {
					case Time.year:
						interval = Calendar.YEAR;
						break;
					case Time.month:
						interval = Calendar.MONTH;
						break;
					case Time.week:
						interval = Calendar.DATE;
						durat *= 7;
						break;
					default:  // day
						interval = Calendar.DATE;
						break;
				}
				calendar.add(interval, durat);
				// Format our new date
				String str = fmtrDate.format(calendar.getTime());
				// Set the new date in the view
				dateResult.setText(str);
			}
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		spinDuration.save(editor);
	}
}
