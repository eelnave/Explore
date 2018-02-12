package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;


public abstract class InputWrapper extends ControlWrapper {
	// These formatters are used for parsing numbers.
	static final NumberFormat
			intFmtr = NumberFormat.getIntegerInstance(),
			decFmtr = NumberFormat.getInstance(),
			perFmtr = NumberFormat.getPercentInstance(),
			curFmtr = NumberFormat.getCurrencyInstance();
	final String prefsKey;

	InputWrapper(View parent, int resID, String prefsKey, CalcFragment calculator) {
		super(parent, resID, calculator);
		this.prefsKey = prefsKey;
	}

	public abstract void save(SharedPreferences.Editor editor);

	public abstract boolean isEmpty();

	public final boolean notEmpty() {
		return !isEmpty();
	}
}
