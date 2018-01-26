package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.EditWrapper;


public final class Binary extends SolveSeries {
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

		EditWrapper[] inputs = { decimal, binary, hexadecimal };
		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decimal },
						new ControlWrapper[]{ binary, hexadecimal }) {
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
				new Solver(new EditWrapper[]{ binary },
						new ControlWrapper[]{ hexadecimal, decimal }) {
					@Override
					public void solve() {
						int num = binary.getBin();
						String decStr = fmtrInt.format(num);
						String hexStr = Integer.toHexString(num);
						decimal.setText(decStr);
						hexadecimal.setText(hexStr);
					}
				},
				new Solver(new EditWrapper[]{ hexadecimal },
						new ControlWrapper[]{ decimal, binary }) {
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

		initialize(view, inputs, solvers, R.id.btnClear);
		return view;
	}

	private static boolean decimalCheck(double s) {
		return s % 1 > 0;
	}
}
