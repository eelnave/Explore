package edu.byui.cit.text;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import edu.byui.cit.calc360.Calc360;


public abstract class DateChangeHandler extends Handler
		implements OnDateChangedListener, DateChangeListener {
	@Override
	public final void onDateChanged(DatePicker view,
			int year, int month, int dayOfMonth) {
		if (!isProgrammatic()) {
			try {
				afterChanged(view, year, month, dayOfMonth);
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}
}
