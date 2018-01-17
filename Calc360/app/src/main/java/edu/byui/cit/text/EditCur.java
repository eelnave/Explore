package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.text.TextWatcher;
import android.view.View;

import java.text.NumberFormat;


public class EditCur extends EditWrapper {
	public EditCur(View parent, int resID, TextWatcher watcher) {
		super(parent, resID, null, watcher);
	}

	public EditCur(
			View parent, int resID, String prefsKey, TextWatcher watcher) {
		super(parent, resID, prefsKey, watcher);
	}

	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			float val = (float)getCur();
			editor.putFloat(prefsKey, val);
		}
	}

	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		if (prefs.contains(prefsKey)) {
			float val = prefs.getFloat(prefsKey, 0);
			setInput(fmtr.format(val));
		}
	}

	@Override
	public Number getValue() {
		return getCur();
	}

	public double getCur() throws NumberFormatException {
		return getCur(getText());
	}

	public double getCur(double deflt) throws NumberFormatException {
		return getCur(getText(), deflt);
	}


	private static double getCur(String str, double deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getCur(str);
	}

	private static double getCur(String str) throws NumberFormatException {
		Number val;
		try {
			val = curFmtr.parse(str);
		}
		catch (Exception ex) {
			try {
				val = decFmtr.parse(str);
			}
			catch (Exception ex2) {
				val = Double.parseDouble(str);
			}
		}
		return val.doubleValue();
	}
}
