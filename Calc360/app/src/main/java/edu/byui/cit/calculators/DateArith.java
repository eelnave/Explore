package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.Calendar;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.DateWrapper;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Time;


public final class DateArith extends CalcFragment {
	private static final String KEY_UNITS = "DateArith.durationType";
	private final DateFormat fmtrDate;

	private DateWrapper datePicker;
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

		Calendar calendar = Calendar.getInstance();
		datePicker = new DateWrapper(view, R.id.datePicker, calendar, this);

		new ButtonWrapper(view, R.id.btnToday, new TodayHandler());
		intDuration = new EditInt(view, R.id.intDuration, this);

		// Set up the spinner to show the right values
		spinDuration = new SpinUnit(getActivity(), view, R.id.spinDuration,
				Time.getInstance(), R.array.durationChoices, KEY_UNITS,
				this);

		dateResult = new TextWrapper(view, R.id.dateResult);

		EditWrapper[] inputs = { intDuration };
		ControlWrapper[] toClear = { intDuration, dateResult };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinDuration.restore(prefs, Time.day);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		spinDuration.save(editor);
	}


	/** Handles a click on the Today button. */
	private final class TodayHandler implements ClickListener {
		/** Handles a click on the Today button. */
		@Override
		public void clicked(View button) {
			// Get today's date.
			Calendar calendar = Calendar.getInstance();
			// Set the datePicker to the current date
			datePicker.updateDate(
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DATE)
			);
		}
	}


	@Override
	protected void compute() {
		if (intDuration.notEmpty()) {
			// Set the date to what the user has selected
			Calendar calendar = Calendar.getInstance();
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
			dateResult.setText(fmtrDate.format(calendar.getTime()));
		}
	}
}
