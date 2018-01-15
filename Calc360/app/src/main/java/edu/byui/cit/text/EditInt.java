package edu.byui.cit.text;

import android.text.TextWatcher;
import android.view.View;


public final class EditInt extends EditWrapper {
	public EditInt(View parent, int resID, TextWatcher watcher) {
		super(parent, resID, watcher);
	}

	@Override
	public Number getValue() {
		return getInt();
	}

	public int getInt() throws NumberFormatException {
		return getInt(getText());
	}

	public int getInt(int deflt) throws NumberFormatException {
		return getInt(getText(), deflt);
	}

	public int getBin() throws NumberFormatException {
		return Integer.parseInt(getText(), 2);
	}

	public int getHex() throws NumberFormatException {
		return Integer.parseInt(getText(), 16);
	}


	private static int getInt(String str, int deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getInt(str);
	}

	private static int getInt(String str) throws NumberFormatException {
		Number val;
		try {
			val = intFmtr.parse(str);
		}
		catch (Exception ex) {
			val = Integer.parseInt(str);
		}
		return val.intValue();
	}
}
