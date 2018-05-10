package edu.byui.cit.widget;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import edu.byui.cit.maintenance.MainActivity;


public abstract class DateChangeHandler
		implements OnDateChangedListener, DateChangeListener {
	@Override
	public final void onDateChanged(DatePicker view,
			int year, int month, int dayOfMonth) {
		try {
			afterChanged(view, year, month, dayOfMonth);
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, "exception", ex);
		}
	}
}
