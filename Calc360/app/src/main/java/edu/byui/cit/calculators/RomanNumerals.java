package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;

public final class RomanNumerals extends CalcFragment {

	private EditInteger intDecToRom;
	private TextWrapper romanNum;

	public RomanNumerals() {
		super();
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.roman_numerals, container, false);

		intDecToRom = new EditInteger(view, R.id.intDecToRom, this);

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

			StringBuffer decToRoman = new StringBuffer();

			if (inputNum < 4000 && inputNum > 0) {
				for (int i = 0; i < decimalValue.length; i++) {
					while (decimalValue[i] <= inputNum) {
						decToRoman.append(romanNumeral[i]);
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
