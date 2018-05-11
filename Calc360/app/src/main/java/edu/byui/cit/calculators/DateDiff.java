package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import java.util.Calendar;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.DateWrapper;
import edu.byui.cit.widget.SpinUnit;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.units.Time;


public final class DateDiff extends CalcFragment {
	private static final String KEY_UNITS = "DateDiff.durationType";
	private final NumberFormat fmtrDec;

	private DateWrapper pickStart, pickEnd;
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
		pickStart = new DateWrapper(view, R.id.datePicker1, calendar, this);
		pickEnd = new DateWrapper(view, R.id.datePicker2, calendar, this);

		new ButtonWrapper(view, R.id.btnToday1, new TodayHandler(pickStart));
		new ButtonWrapper(view, R.id.btnToday2, new TodayHandler(pickEnd));

		// Set up the spinner to show the right values
		spinDuration = new SpinUnit(getActivity(), view, R.id.spinDuration,
				Time.getInstance(), R.array.durationChoices, KEY_UNITS,
				this);

		decDiff = new TextWrapper(view, R.id.decDiff);

		WidgetWrapper[] toClear = { decDiff };
		initialize(view, null, R.id.btnClear, toClear);
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
	private final class TodayHandler implements ClickListener {
		private final DateWrapper picker;

		TodayHandler(DateWrapper picker) {
			this.picker = picker;
		}

		@Override
		public void clicked(WidgetWrapper source) {
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
	protected void compute(WidgetWrapper source) {
		// Set the date to what the user has selected
		Calendar start = Calendar.getInstance();
		start.set(pickStart.getYear(), pickStart.getMonth(),
				pickStart.getDayOfMonth());

		// Set the date to what the user has selected
		Calendar end = Calendar.getInstance();
		end.set(pickEnd.getYear(), pickEnd.getMonth(),
				pickEnd.getDayOfMonth());

		long millis = end.getTimeInMillis() - start.getTimeInMillis();
		int units = spinDuration.getSelectedItem().getID();
		double factor;
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
}
