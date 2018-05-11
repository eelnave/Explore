package edu.byui.cit.widget;

public interface DateChangeListener {
//	void afterChanged(DatePicker view, int year, int month, int dayOfMonth);
	void afterChanged(DateWrapper source, int year, int month, int dayOfMonth);
}
