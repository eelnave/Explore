package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;


public class EditString extends EditWrapper {
	public EditString(View parent, int resID, CalcFragment calculator) {
		super(parent, resID, null, calculator);
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
