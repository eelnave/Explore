package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Arrays;

import edu.byui.cit.calc360.CalcFragment;


public class EditDec extends EditWrapper {
	public EditDec(View parent, int resID, CalcFragment calculator) {
		super(parent, resID, null, calculator);
	}

	public EditDec(
			View parent, int resID, String prefsKey, CalcFragment calculator) {
		super(parent, resID, prefsKey, calculator);
	}

	public EditDec(View parent, int resID, TextChangeListener listener) {
		super(parent, resID, null, listener);
	}

	public EditDec(
			View parent, int resID, String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey, listener);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		if (isEmpty()) {
			editor.remove(prefsKey);
		}
		else {
			float val = (float)getDec();
			editor.putFloat(prefsKey, val);
		}
	}

	@Override
	public void restore(SharedPreferences prefs, NumberFormat fmtr) {
		if (prefs.contains(prefsKey)) {
			float val = prefs.getFloat(prefsKey, 0);
			setInput(fmtr.format(val));
		}
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

	private static double getDec(String str, double deflt)
			throws NumberFormatException {
		return str.length() == 0 ? deflt : getDec(str);
	}

	private static double getDec(String str) throws NumberFormatException {
		Number val;
		try {
			val = decFmtr.parse(str);
		}
		catch (Exception ex) {
			val = Double.parseDouble(str);
		}
		return val.doubleValue();
	}


	public static double[] getDecimals(EditText txtMulti)
			throws NumberFormatException {
		return getDecimals(txtMulti.getText().toString().trim());
	}

	public static double[] getDecimals(String str)
			throws NumberFormatException {
		String[] array = str.split("\\s+");
		double[] sample = new double[array.length];
		int count = 0;
		for (String elem : array) {
			sample[count] = getDec(elem);
			++count;
		}
		if (sample.length != count) {
			sample = Arrays.copyOf(sample, count);
		}
		return sample;
	}
}
