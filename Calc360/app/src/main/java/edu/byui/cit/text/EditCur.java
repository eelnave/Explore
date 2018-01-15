package edu.byui.cit.text;

import android.text.TextWatcher;
import android.view.View;


public final class EditCur extends EditWrapper {
	public EditCur(View parent, int resID, TextWatcher watcher) {
		super(parent, resID, watcher);
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
