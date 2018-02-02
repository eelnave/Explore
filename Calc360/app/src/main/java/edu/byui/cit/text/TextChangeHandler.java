package edu.byui.cit.text;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import edu.byui.cit.calc360.Calc360;


public abstract class TextChangeHandler
		implements TextWatcher, TextChangeListener {
	@Override
	public final void beforeTextChanged(
			CharSequence s, int start, int count, int after) {
	}

	@Override
	public final void onTextChanged(
			CharSequence s, int start, int before, int count) {
	}

	@Override
	public final void afterTextChanged(Editable edit) {
		try {
			afterChanged(edit);
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}
}
