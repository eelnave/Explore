package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;


public abstract class InputWrapper extends WidgetWrapper {
	// These formatters are used for parsing numbers.
	static final NumberFormat
			intFmtr = NumberFormat.getIntegerInstance(),
			decFmtr = NumberFormat.getInstance(),
			curFmtr = NumberFormat.getCurrencyInstance();
	final String prefsKey;

	InputWrapper(View parent, int resID, String prefsKey) {
		super(parent, resID);
		this.prefsKey = prefsKey;
	}

	public abstract void save(SharedPreferences.Editor editor);

	public abstract boolean isEmpty();

	public final boolean notEmpty() {
		return !isEmpty();
	}
}
