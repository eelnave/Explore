package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;


public final class EditPerc extends EditWrapper {
	public EditPerc(View parent, int resID, CalcFragment calculator) {
		super(parent, resID, null, calculator);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			// ToDo
		}
	}

	@Override
	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		// ToDo
	}

	public double getPerc() throws NumberFormatException {
		return getPerc(getText());
	}

	public double getPerc(double deflt) throws NumberFormatException {
		return getPerc(getText(), deflt);
	}


	private static double getPerc(String str, double deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getPerc(str);
	}

	private static double getPerc(String str) throws NumberFormatException {
		Number val;
		try {
			val = perFmtr.parse(str);
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
