package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditString;
import edu.byui.cit.text.EditWrapper;

import static edu.byui.cit.model.Mathematics.Roman.*;


public final class RomanNumerals extends CalcFragment {
	private final NumberFormat fmtrInt;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditInteger intDecimalNum;
	private EditString strRomanNum;


	public RomanNumerals() {
		// Call the constructor in the parent class CalcFragment.
		super();

		fmtrInt = NumberFormat.getIntegerInstance();
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.roman_numerals, container, false);

		// Get a reference to each of the text fields in this calculator. The
		// id's inside the R class connect this Java code to the elements in
		// the xml layout file. For example R.id.strRomanNum connects the new
		// EditString object to the xml element that has an id of "strRomanNum".
		intDecimalNum = new EditInteger(view, R.id.intDecimalNum, this);
		strRomanNum = new EditString(view, R.id.strRomanNum, this);

		EditWrapper[] inputs = {intDecimalNum, strRomanNum};
		ControlWrapper[] toClear = {strRomanNum, intDecimalNum};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (intDecimalNum.hasUserInput()) {
			int arabic = intDecimalNum.getInt();
			//roman numerals can't be larger than 3999
			if (arabic >= 4000){
				strRomanNum.setText("Invalid input");
			}
			else {
				String roman = romanFromArabic(arabic);
				strRomanNum.setText(roman);
			}
		}
		else if (strRomanNum.hasUserInput()) {
			String roman = strRomanNum.getText();
			//Roman numerals must follow certain formats
			if (!roman.matches("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")){
				intDecimalNum.setText("Invalid input");
			}
			else {
				int arabic = arabicFromRoman(roman);
				intDecimalNum.setText(fmtrInt.format(arabic));
			}
		}
		//clear inputs if one is empty
		if (intDecimalNum.isEmpty()){
			strRomanNum.clear();
		}
		if(strRomanNum.isEmpty()){
			intDecimalNum.clear();
		}

	}
}
