package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import java.util.Calendar;

import edu.byui.cit.calc360.Calc360;


public class DateWrapper extends InputWrapper implements OnDateChangedListener {
	private final DatePicker picker;
	private final DateChangeListener listener;

	public DateWrapper(View parent, int resID, Calendar calendar) {
		this(parent, resID, calendar, null, null);
	}

	public DateWrapper(View parent, int resID, Calendar calendar,
			String prefsKey) {
		this(parent, resID, calendar, prefsKey, null);
	}

	public DateWrapper(View parent, int resID, Calendar calendar,
			DateChangeListener listener) {
		this(parent, resID, calendar, null, listener);
	}

	public DateWrapper(View parent, int resID, Calendar calendar,
			String prefsKey, DateChangeListener listener) {
		super(parent, resID, prefsKey);
		this.picker = parent.findViewById(resID);
		picker.init(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE),
				this);
		this.listener = listener;
	}


	@Override
	public void save(SharedPreferences.Editor editor) {
	}


	@Override
	public final DatePicker getView() {
		return picker;
	}

	@Override
	public final boolean isEmpty() {
		return false;
	}

	@Override
	public final boolean isEnabled() {
		return picker.isEnabled();
	}

	@Override
	public final void setEnabled(boolean enabled) {
		picker.setEnabled(enabled);
	}


	@Override
	public final boolean hasFocus() {
		return picker.hasFocus();
	}

	@Override
	public final void requestFocus() {
		picker.requestFocus();
	}


	@Override
	public void onDateChanged(
			DatePicker view, int year, int month, int dayOfMonth) {
		if (listener != null) {
			try {
//				listener.afterChanged(view, year, month, dayOfMonth);
				listener.afterChanged(this, year, month, dayOfMonth);
			}
			catch (NumberFormatException ex) {
				// Do nothing.
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}

	public final void updateDate(int year, int month, int dayOfMonth) {
		picker.updateDate(year, month, dayOfMonth);
	}

	public final int getYear() {
		return picker.getYear();
	}

	public final int getMonth() {
		return picker.getMonth();
	}

	public final int getDayOfMonth() {
		return picker.getDayOfMonth();
	}
}
