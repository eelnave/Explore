package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;


public class EditCurrency extends EditWrapper {
	public EditCurrency(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey, listener);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			float val = (float)getCur();
			editor.putFloat(prefsKey, val);
		}
	}

	@Override
	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		restore(prefs, fmtr, 0);
	}

	public void restore(
			SharedPreferences prefs, NumberFormat fmtr, float deflt) {
		if (!hasUserInput() && prefs.contains(prefsKey)) {
			float val = prefs.getFloat(prefsKey, deflt);
			setInput(fmtr.format(val));
		}
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
