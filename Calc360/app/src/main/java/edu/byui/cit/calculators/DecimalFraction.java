package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;


public final class DecimalFraction extends CalcFragment {
	/*
	 * fraction -> decimal
	 * numerator/denominator
	 *
	 * decimal -> fraction
	 * if 1 decimal place then put over 10 (compare)
	 * 	loop 1-10
	 * 		if % of (10/i) = 0
	 * 		and if % of (decimal/i) = 0
	 * 		then divide (10/i) and (decimal/i)
	 * loop again using new denominator instead of 10
	 * if % never = 0 then end loop
	 * display decimal in numerator - and display new numerator
	 */

	private final NumberFormat fmtrInt, fmtrDec;
	private final String decSepPat;

	private EditDecimal decimal;
	private EditInteger intWhole;
	private EditInteger intNumer;
	private EditInteger intDenom;

	public DecimalFraction() {
		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(Integer.MAX_VALUE);
		decSepPat = "\\" + fmtrDec.format(1.2).charAt(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.decimal_fraction, container,
				false);
		decimal = new EditDecimal(view, R.id.decimal, this);
		intWhole = new EditInteger(view, R.id.wholeNumF, this);
		intNumer = new EditInteger(view, R.id.numerator, this);
		intDenom = new EditInteger(view, R.id.denominator, this);

		EditWrapper[] inputs = { decimal, intWhole, intNumer, intDenom };
		ControlWrapper[] toClear = { decimal, intWhole, intNumer, intDenom };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	protected void compute() {
//		if (decimal.hasFocus()) {
//			intWhole.clear();
//			intNumer.clear();
//			intDenom.clear();
//		}
//		else {
//			decimal.clear();
//		}

		if (decimal.hasFocus()) {
			String text = decimal.getText();
			String[] parts = text.split(decSepPat);
			String left = parts[0];
			String right = parts.length == 1 ? "" : parts[1];

			long whole = EditInteger.getLong(left, 0);
			if (whole > 0) {
				intWhole.setText(fmtrInt.format(whole));
			}
			else {
				intWhole.clear();
			}

			if (right.length() > 0) {
				int digits = right.length();
				long denom = (long)Math.pow(10, digits);
				long numer = EditInteger.getLong(right);
				long gcd = edu.byui.cit.model.Mathematics.gcd(numer, denom);
				numer /= gcd;
				denom /= gcd;
				intNumer.setText(fmtrInt.format(numer));
				intDenom.setText(fmtrInt.format(denom));
			}
			else {
				intNumer.clear();
				intDenom.clear();
			}
		}
		else if (intWhole.notEmpty() ||
				intNumer.notEmpty() || intDenom.notEmpty()) {
			if (intNumer.isEmpty() == intDenom.isEmpty()) {
				double deci = intWhole.getInt(0);

				if (intDenom.notEmpty()) {
					long denom = intDenom.getLong();
					if (denom == 0) {
						deci = Double.NaN;
					}
					else {
						long numer = intNumer.getLong();
						deci += numer / (double)denom;
					}
				}

				decimal.setText(fmtrDec.format(deci));
			}
			else {
				decimal.clear();
			}
		}
	}
}
