package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.model.Mathematics;


public final class Fraction extends CalcFragment {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal a1, b1, c1, d1;
	private SpinString dropdown;
	private TextWrapper fn1, fd1;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.fraction, container, false);

		a1 = new EditDecimal(view, R.id.a1, this);
		b1 = new EditDecimal(view, R.id.b1, this);
		c1 = new EditDecimal(view, R.id.c1, this);
		d1 = new EditDecimal(view, R.id.d1, this);

		dropdown = new SpinString(view, R.id.spinner1, null, this);

		fn1 = new TextWrapper(view, R.id.fn1);
		fd1 = new TextWrapper(view, R.id.fd1);

		EditWrapper[] inputs = { a1, b1, c1, d1 };
		ControlWrapper[] toClear = { a1, b1, c1, d1, fn1, fd1 };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		int numer, denom;
		char symbol = dropdown.getSelectedItem().charAt(0);
		switch (symbol) {
			case '+':
				numer = (int)((a1.getDec() * d1.getDec())
						+ (b1.getDec() * c1.getDec()));
				denom = (int)(b1.getDec() * d1.getDec());
				break;
			case '-':
				numer = (int)((a1.getDec() * d1.getDec())
						- (b1.getDec() * c1.getDec()));
				denom = (int)(b1.getDec() * d1.getDec());
				break;
			case 'x':
				numer = (int)(a1.getDec() * c1.getDec());
				denom = (int)(b1.getDec() * d1.getDec());
				break;
			default:  // '/':
				numer = (int)(a1.getDec() * d1.getDec());
				denom = (int)(b1.getDec() * c1.getDec());
				break;
		}

		int gcd = (int)Mathematics.gcd(numer, denom);
		numer = numer / gcd;
		denom = denom / gcd;
		fn1.setText(fmtrDec.format(numer));
		fd1.setText(fmtrDec.format(denom));
	}
}
