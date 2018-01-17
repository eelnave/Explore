package edu.byui.cit.text;

import android.text.TextWatcher;
import android.view.View;


public final class EditPerc extends EditWrapper {
	public EditPerc(View parent, int resID, TextWatcher watcher) {
		super(parent, resID, null, watcher);
	}

	@Override
	public Number getValue() {
		return getPerc();
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
