package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;


public class EditInteger extends EditWrapper {
	public EditInteger(View parent, int resID, CalcFragment calculator) {
		super(parent, resID, null, calculator);
	}

	public EditInteger(
			View parent, int resID, String prefsKey, CalcFragment calculator) {
		super(parent, resID, prefsKey, calculator);
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
		restore(prefs, fmtr, 0);
	}

	public void restore(SharedPreferences prefs, NumberFormat fmtr, int deflt) {
		if (!hasUserInput() && prefs.contains(prefsKey)) {
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

	public long getBin() throws NumberFormatException {
		return Long.parseLong(getText(), 2);
	}

	public long getHex() throws NumberFormatException {
		return Long.parseLong(getText(), 16);
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
