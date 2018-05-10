package edu.byui.cit.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import edu.byui.cit.maintenance.MainActivity;


public abstract class TextChangeHandler
		implements TextWatcher, TextChangeListener {
	@Override
	public final void beforeTextChanged(
			CharSequence s, int start, int count, int after) {
	}

	@Override
	public final void onTextChanged(
			CharSequence s, int start, int before, int count) {
		if (before != 0 || count != 0) {
			try {
				textChanged(s);
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG, "exception", ex);
			}
		}
	}

	@Override
	public final void afterTextChanged(Editable edit) {
	}
}
