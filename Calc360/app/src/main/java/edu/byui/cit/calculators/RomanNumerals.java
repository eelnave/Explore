package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Mathematics.Roman;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditString;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;


public final class RomanNumerals extends CalcFragment {
	private final NumberFormat fmtrInt = NumberFormat.getIntegerInstance();

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditInteger intDecimalNum;
	private EditString strRomanNum;
	private TextWrapper invalidDecimal, invalidRoman;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.roman_numerals, container,
				false);

		// Get a reference to each of the text fields in this calculator. The
		// id's inside the R class connect this Java code to the elements in
		// the xml layout file. For example R.id.strRomanNum connects the new
		// EditString object to the xml element that has an id of
		// "strRomanNum".
		intDecimalNum = new EditInteger(view, R.id.intDecimalNum, this);
		strRomanNum = new EditString(view, R.id.strRomanNum, this);
		invalidDecimal = new TextWrapper(view, R.id.invalidDecimal);
		invalidRoman = new TextWrapper(view, R.id.invalidRoman);

		EditWrapper[] inputs = { intDecimalNum, strRomanNum };
		WidgetWrapper[] toClear = {
				intDecimalNum, strRomanNum, invalidDecimal, invalidRoman
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		invalidDecimal.clear();
		invalidRoman.clear();
		if (intDecimalNum.hasFocus() && intDecimalNum.hasUserInput()) {
			int arabic = intDecimalNum.getInt();
			if (arabic < 4000) {
				String roman = Roman.romanFromArabic(arabic);
				strRomanNum.setText(roman);
			}
			else {
				invalidDecimal.setText(R.string.invalid);
				strRomanNum.clear();
			}
		}
		else if (strRomanNum.hasFocus() && strRomanNum.hasUserInput()) {
			String roman = strRomanNum.getText();
			if (Roman.isValid(roman)) {
				int arabic = Roman.arabicFromRoman(roman);
				intDecimalNum.setText(fmtrInt.format(arabic));
			}
			else {
				invalidRoman.setText(R.string.invalid);
				intDecimalNum.clear();
			}
		}
		else {
			intDecimalNum.clear();
			strRomanNum.clear();
		}
	}
}
