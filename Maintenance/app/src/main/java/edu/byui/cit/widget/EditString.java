package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;


public class EditString extends EditWrapper {
	public EditString(View parent, int resID) {
		super(parent, resID, null, null);
	}

	public EditString(View parent, int resID, String prefsKey) {
		super(parent, resID, prefsKey, null);
	}

	public EditString(View parent, int resID, TextChangeListener listener) {
		super(parent, resID, null, listener);
	}

	public EditString(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey, listener);
	}


	@Override
	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			editor.putString(prefsKey, getText());
		}
	}

	public void restore(SharedPreferences prefs) {
		if (!hasUserInput() && prefs.contains(prefsKey)) {
			String val = prefs.getString(prefsKey, null);
			setInput(val);
		}
	}

	@Override
	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		throw new UnsupportedOperationException();
	}
}
