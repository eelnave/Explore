package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;


public class EditInteger extends EditWrapper {
	public EditInteger(View parent, int resID) {
		super(parent, resID, null, null);
	}

	public EditInteger(View parent, int resID, String prefsKey) {
		super(parent, resID, prefsKey, null);
	}

	public EditInteger(View parent, int resID, TextChangeListener listener) {
		super(parent, resID, null, listener);
	}

	public EditInteger(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey, listener);
	}


	@Override
	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			editor.putInt(prefsKey, getInt());
		}
	}

	@Override
	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		if (!hasUserInput() && prefs.contains(prefsKey)) {
			int val = prefs.getInt(prefsKey, 0);
			setInput(fmtr.format(val));
		}
	}

	public void restore(SharedPreferences prefs, NumberFormat fmtr, int deflt) {
		if (!hasUserInput()) {
			int val = prefs.getInt(prefsKey, deflt);
			setInput(fmtr.format(val));
		}
	}


	public int getInt() throws NumberFormatException {
		return getInt(getText());
	}

	public int getInt(int deflt) throws NumberFormatException {
		return getInt(getText(), deflt);
	}

	public long getLong() throws NumberFormatException {
		return getLong(getText());
	}

	public long getBin() throws NumberFormatException {
		return Long.parseLong(getText(), 2);
	}

	public long getBase8() throws NumberFormatException {
		return Long.parseLong(getText(), 8);
	}

	public long getHex() throws NumberFormatException {
		return Long.parseLong(getText(), 16);
	}


	public static int getInt(String str, int deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getInt(str);
	}

	public static int getInt(String str) throws NumberFormatException {
		Number val;
		try {
			val = intFmtr.parse(str);
		}
		catch (Exception ex) {
			val = Integer.parseInt(str);
		}
		return val.intValue();
	}

	public static long getLong(String str, long deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getLong(str);
	}

	public static long getLong(String str) throws NumberFormatException {
		Number val;
		try {
			val = intFmtr.parse(str);
		}
		catch (Exception ex) {
			val = Long.parseLong(str);
		}
		return val.longValue();
	}
}
