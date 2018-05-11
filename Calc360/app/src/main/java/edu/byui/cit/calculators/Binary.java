package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.WidgetWrapper;


public final class Binary extends CalcFragment {
	private final NumberFormat fmtrInt = NumberFormat.getIntegerInstance();

	private EditDecimal decimal;
	private EditInteger binary;
	private EditInteger hexadecimal;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate layout
		View view = inflater.inflate(R.layout.binary, container, false);

		decimal = new EditDecimal(view, R.id.decimal, this);
		binary = new EditInteger(view, R.id.binary, this);
		hexadecimal = new EditInteger(view, R.id.hexadecimal, this);

		EditWrapper[] inputs = { decimal, binary, hexadecimal };
		initialize(view, inputs, R.id.btnClear, inputs);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		clearOutput();

		if (decimal.hasUserInput()) {
			double num = decimal.getDec();
			String binStr, hexStr;
			if (isInteger(num)) {
				long intNum = (long)num;
				binStr = Long.toBinaryString(intNum);
				hexStr = Long.toHexString(intNum);
			}
			else {
				long longBits = Double.doubleToLongBits(num);
				binStr = Long.toBinaryString(longBits);
				hexStr = Long.toHexString(longBits);
			}
			binary.setText(binStr);
			hexadecimal.setText(hexStr);
		}
		else if (binary.hasUserInput()) {
			long num = binary.getBin();
			String decStr = fmtrInt.format(num);
			String hexStr = Long.toHexString(num);
			decimal.setText(decStr);
			hexadecimal.setText(hexStr);
		}
		else if (hexadecimal.hasUserInput()) {
			long num = hexadecimal.getHex();
			String decStr = fmtrInt.format(num);
			String binStr = Long.toBinaryString(num);
			decimal.setText(decStr);
			binary.setText(binStr);
		}
	}

	private static boolean isInteger(double d) {
		return d % 1 == 0;
	}
}
