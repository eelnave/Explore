package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSome;
import edu.byui.cit.text.Control;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.Input;


public final class Binary extends SolveSome {
	private final NumberFormat fmtrInt = NumberFormat.getIntegerInstance();

	private EditDec decimal;
	private EditInt binary;
	private EditInt hexadecimal;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate layout
		View view = inflater.inflate(R.layout.binary, container,
				false);

		decimal = new EditDec(view, R.id.decimal, this);
		binary = new EditInt(view, R.id.binary, this);
		hexadecimal = new EditInt(view, R.id.hexadecimal, this);

		Control[] toClear = new Control[]{ decimal, binary, hexadecimal };
		Solver[] solvers = new Solver[]{
				new Solver(new Input[]{ decimal }) {
					@Override
					public void solve() {
						double num = decimal.getDec();
						String binStr, hexStr;
						boolean decCheck = decimalCheck(num);
						if (decCheck) {
							long longBits = Double.doubleToLongBits(num);
							binStr = Long.toBinaryString(longBits);
							hexStr = Long.toHexString(longBits);
						}
						else {
							int intNum = (int)num;
							binStr = Integer.toBinaryString(intNum);
							hexStr = Integer.toHexString(intNum);
						}
						binary.setText(binStr);
						hexadecimal.setText(hexStr);
					}
				},
				new Solver(new Input[]{ binary }) {
					@Override
					public void solve() {
						int num = binary.getBin();
						String decStr = fmtrInt.format(num);
						String hexStr = Integer.toHexString(num);
						decimal.setText(decStr);
						hexadecimal.setText(hexStr);
					}
				},
				new Solver(new Input[]{ hexadecimal }) {
					@Override
					public void solve() {
						int num = hexadecimal.getHex();
						String decStr = fmtrInt.format(num);
						String binStr = Integer.toBinaryString(num);
						decimal.setText(decStr);
						binary.setText(binStr);
					}
				}
		};

		initialize(view, R.id.btnClear, toClear, solvers);
		return view;
	}

	private static boolean decimalCheck(double s) {
		return s % 1 > 0;
	}
}
