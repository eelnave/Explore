package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class RomanNumerals extends CalcFragment {

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditInteger intDecimalNum;
	private EditWrapper romanNum;

	// Each of these variables is a reference to one of the
	// TextViews where this calculator will display output.



	public RomanNumerals() {
		// Call the constructor in the parent class CalcFragment.
		super();

	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.roman_numerals, container, false);

		// Get a reference to each of the text fields in this calculator. The
		// id's inside the R class connect this Java code to the elements in
		// the xml layout file. For example R.id.romanNum connects the new
		// EditString object to the xml element that has an id of "romanNum".
		intDecimalNum = new EditInteger(view, R.id.intDecimalNum, this);
		romanNum = new EditWrapper(view, R.id.romanNum, this);

		// Get a reference to each of the TextViews
		// where this calculator will display output.
//		romanNum = new TextWrapper(view, R.id.romanNum);

		EditWrapper[] inputs = { intDecimalNum, romanNum };
		ControlWrapper[] toClear = { romanNum, intDecimalNum };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {

		//decimal to roman
		if (intDecimalNum.notEmpty()) {

			int inputNum = intDecimalNum.getInt();

			int decimalValue[] = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
			String romanNumeral[] = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

			String decToRoman = "";

			if (inputNum < 4000 && inputNum > 0) {

				for (int i = 0; i <= decimalValue.length - 1; i++) {
					while (decimalValue[i] <= inputNum) {
						decToRoman += romanNumeral[i];
						inputNum -= decimalValue[i];
					}
				}
				romanNum.setText(decToRoman);
			}
			else{
				romanNum.setText("Out of bounds");
			}

		}

		//roman to decimal
		else if (romanNum.notEmpty()) {
				int decimal = 0;
				int lastNumber = 0;
				String romanNumeral = romanNum;
				for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
					char convertToDecimal = romanNumeral.charAt(x);

					switch (convertToDecimal) {
						case 'M':
							decimal = processDecimal(1000, lastNumber, decimal);
							lastNumber = 1000;
							break;

						case 'D':
							decimal = processDecimal(500, lastNumber, decimal);
							lastNumber = 500;
							break;

						case 'C':
							decimal = processDecimal(100, lastNumber, decimal);
							lastNumber = 100;
							break;

						case 'L':
							decimal = processDecimal(50, lastNumber, decimal);
							lastNumber = 50;
							break;

						case 'X':
							decimal = processDecimal(10, lastNumber, decimal);
							lastNumber = 10;
							break;

						case 'V':
							decimal = processDecimal(5, lastNumber, decimal);
							lastNumber = 5;
							break;

						case 'I':
							decimal = processDecimal(1, lastNumber, decimal);
							lastNumber = 1;
							break;
					}
				}

				intDecimalNum.setText(decimal);
			}
		else {
			// If one or both of the inputs are empty, clear the outputs.
			intDecimalNum.clear();
			romanNum.clear();
		}

	}

	public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
		if (lastNumber > decimal) {
			return lastDecimal - decimal;
		} else {
			return lastDecimal + decimal;
		}
	}
}
