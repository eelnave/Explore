package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class RomanNumerals extends CalcFragment {

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditInteger intDecToRom;

	// Each of these variables is a reference to one of the
	// TextViews where this calculator will display output.
	private TextWrapper romanNum;


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
		intDecToRom = new EditInteger(view, R.id.intDecToRom, this);

		// Get a reference to each of the TextViews
		// where this calculator will display output.
		romanNum = new TextWrapper(view, R.id.romanNum);

		EditWrapper[] inputs = { intDecToRom };
		ControlWrapper[] toClear = { romanNum, intDecToRom };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (intDecToRom.notEmpty()) {

			int inputNum = intDecToRom.getInt();

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
		else {
			// If one or both of the inputs are empty, clear the outputs.
			intDecToRom.clear();
			romanNum.clear();
		}
	}
}
