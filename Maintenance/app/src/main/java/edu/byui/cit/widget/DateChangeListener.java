package edu.byui.cit.widget;

import android.widget.DatePicker;


public interface DateChangeListener {
	void afterChanged(DatePicker view, int year, int month, int dayOfMonth);
}
