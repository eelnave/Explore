package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;


public final class EditFraction extends EditWrapper {
	public EditFraction(View parent, int resID) {
		super(parent, resID, null, null);
	}

	public EditFraction(View parent, int resID, String prefsKey) {
		super(parent, resID, prefsKey, null);
	}

	public EditFraction(View parent, int resID, TextChangeListener listener) {
		super(parent, resID, null, listener);
	}

	public EditFraction(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey, listener);
	}


	@Override
	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			// TODO
		}
	}

	@Override
	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		// TODO
	}


	public double getDec() throws NumberFormatException {
		return getDec(getText());
	}

	public double getDec(double deflt) throws NumberFormatException {
		return getDec(getText(), deflt);
	}


	public static double getDec(EditText edit) throws NumberFormatException {
		return getDec(edit.getText().toString());
	}

	public static double getDec(EditText edit, double deflt)
			throws NumberFormatException {
		return getDec(edit.getText().toString(), deflt);
	}

	public static double getDec(String str, double deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getDec(str);
	}

	public static double getDec(String str) throws NumberFormatException {
		double val;
		try {
			if (str.contains("/")) {
				String[] parts = str.split("/");
				if (parts.length == 1) {
					val = decFmtr.parse(str).doubleValue();
				}
				else if (parts.length > 1) {
					Number numer = decFmtr.parse(parts[0]);
					Number denom = decFmtr.parse(parts[1]);
					val = numer.doubleValue() / denom.doubleValue();
				}
				else {
					throw new NumberFormatException("bad fraction: " + str);
				}
			}
			else {
				val = decFmtr.parse(str).doubleValue();
			}
		}
		catch (Exception ex) {
			val = Double.parseDouble(str);
		}
		return val;
	}
}