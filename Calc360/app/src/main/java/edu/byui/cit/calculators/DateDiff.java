package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import java.util.Calendar;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.DateWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Time;


public final class DateDiff extends CalcFragment {
	private static final String KEY_UNITS = "DateDiff.durationType";
	private final NumberFormat fmtrDec;

	private DateWrapper picker1, picker2;
	private SpinUnit spinDuration;
	private TextWrapper decDiff;

	public DateDiff() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(2);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.date_diff, container, false);

		Calendar calendar = Calendar.getInstance();
		picker1 = new DateWrapper(view, R.id.datePicker1, calendar, this);
		picker2 = new DateWrapper(view, R.id.datePicker2, calendar, this);

		new ButtonWrapper(view, R.id.btnToday1, new Today(picker1));
		new ButtonWrapper(view, R.id.btnToday2, new Today(picker2));

		// Set up the spinner to show the right values
		Activity act = getActivity();
		spinDuration = new SpinUnit(act, view, R.id.spinDuration, Time.getInstance(), R.array.durationChoices, KEY_UNITS, this);

		decDiff = new TextWrapper(view, R.id.decDiff);

		new ButtonWrapper(view, R.id.btnClear,
				new ClickListener() {
					@Override
					public void clicked(View button) {
						decDiff.clear();
					}
				}
		);

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


	/** Handles a click on a Today button. */
	private final class Today implements ClickListener {
		private final DateWrapper picker;

		Today(DateWrapper picker) {
			this.picker = picker;
		}

		@Override
		public void clicked(View button) {
			// Get today's date.
			Calendar calendar = Calendar.getInstance();
			// Set the datePicker to the current date
			picker.updateDate(
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DATE));
		}
	}


	@Override
	protected void compute() {
		try {
			// Set the date to what the user has selected
			Calendar from = Calendar.getInstance();
			from.set(picker1.getYear(), picker1.getMonth(),
					picker1.getDayOfMonth());

			// Set the date to what the user has selected
			Calendar to = Calendar.getInstance();
			to.set(picker2.getYear(), picker2.getMonth(),
					picker2.getDayOfMonth());

			long millis = Math.abs(
					from.getTimeInMillis() - to.getTimeInMillis());
			double factor;

			// Find out what unit we are using and add it
			int units = spinDuration.getSelectedItem().getID();
			switch (units) {
				case Time.year:
					factor = 1000 * 60 * 60 * 24 * 365.25;
					break;
				case Time.month:
					factor = 1000.0 * 60 * 60 * 24 * 30;
					break;
				case Time.week:
					factor = 1000 * 60 * 60 * 24 * 7;
					break;
				default:  // day
					factor = 1000 * 60 * 60 * 24;
			}

			double duration = millis / factor;
			decDiff.setText(fmtrDec.format(duration));
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}
}
