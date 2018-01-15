package edu.byui.cit.text;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;


public class EditDec extends EditWrapper {
	public EditDec(View parent, int resID, TextWatcher watcher) {
		super(parent, resID, watcher);
	}

	@Override
	public Number getValue() {
		return getDec();
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
