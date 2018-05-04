package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.regex.Pattern;

import edu.byui.cit.model.Fraction;
import edu.byui.cit.model.Mathematics;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.TextWrapper;


public final class Fractions extends CalcFragment {
	private static final int WHOLE = 0, NUMER = 1, DENOM = 2;
	private static final HashMap<String, Operator> operators;

	private interface Operator {
		Fraction compute(Fraction left, Fraction right);
	}

	static {
		operators = new HashMap<>(6);
		operators.put("+", new Operator() {
					@Override
					public Fraction compute(Fraction left, Fraction right) {
						return left.add(right);
					}
				});
		operators.put("\u2212",
				new Operator() {
					@Override
					public Fraction compute(Fraction left, Fraction right) {
						return left.subtract(right);
					}
				});
		operators.put("\u00d7",
				new Operator() {
					@Override
					public Fraction compute(Fraction left, Fraction right) {
						return left.multiply(right);
					}
				});
		operators.put("\u00f7",
				new Operator() {
					@Override
					public Fraction compute(Fraction left, Fraction right) {
						Fraction result = null;
						if (right.getNumerator() != 0) {
							result = left.divide(right);
						}
						return result;
					}
				});
	}

	private final Pattern separator;
	private final NumberFormat fmtrInt, fmtrDec;

	private EditInteger[] lefts, rights;
	private EditDecimal leftReal, rightReal;
	private SpinString spinner;
	private TextWrapper[] results;
	private TextWrapper resultReal;

	public Fractions() {
		super();
		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(Integer.MAX_VALUE);
		separator = Pattern.compile("\\" + fmtrDec.format(1.2).charAt(1));
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.fractions, container, false);

		lefts = new EditInteger[]{
				new EditInteger(view, R.id.leftWhole, this),
				new EditInteger(view, R.id.leftNumer, this),
				new EditInteger(view, R.id.leftDenom, this)
		};
		leftReal = new EditDecimal(view, R.id.leftReal, this);

		spinner = new SpinString(view, R.id.spinner, null, this);

		rights = new EditInteger[]{
				new EditInteger(view, R.id.rightWhole, this),
				new EditInteger(view, R.id.rightNumer, this),
				new EditInteger(view, R.id.rightDenom, this)
		};
		rightReal = new EditDecimal(view, R.id.rightReal, this);

		results = new TextWrapper[]{
				new TextWrapper(view, R.id.resultWhole),
				new TextWrapper(view, R.id.resultNumer),
				new TextWrapper(view, R.id.resultDenom)
		};
		resultReal = new TextWrapper(view, R.id.resultReal);

		EditWrapper[] inputs = {
				lefts[WHOLE], lefts[NUMER], lefts[DENOM], leftReal,
				rights[WHOLE], rights[NUMER], rights[DENOM], leftReal
		};
		ControlWrapper[] toClear = {
				lefts[WHOLE], lefts[NUMER], lefts[DENOM], leftReal,
				rights[WHOLE], rights[NUMER], rights[DENOM], leftReal,
				results[WHOLE], results[NUMER], results[DENOM], resultReal
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		Fraction leftFract = readFractionOrReal(lefts, leftReal);
		Fraction rightFract = readFractionOrReal(rights, rightReal);
		if (leftFract != null && rightFract != null) {
			String item = spinner.getSelectedItem();
			Operator op = operators.get(item);
			Fraction resultFract = op.compute(leftFract, rightFract);
			if (resultFract != null) {
				showFraction(results, resultFract);
				showReal(resultReal, resultFract);
			}
		}
		else {
			clearAll(results);
			resultReal.clear();
		}
	}


	private Fraction readFractionOrReal(EditInteger[] inputs,
			EditDecimal real) {
		Fraction fract = null;
		if (EditWrapper.anyNotEmpty(inputs) &&
				(EditWrapper.anyHaveFocus(inputs) || real.notFocus())) {
			fract = readFraction(inputs);
			showReal(real, fract);
		}
		else if (real.hasFocus()) {
			if (real.notEmpty()) {
				fract = readReal(real);
				showFraction(inputs, fract);
			}
			else {
				clearAll(inputs);
			}
		}
		else {
			real.clear();
		}
		return fract;
	}


	private Fraction readFraction(EditInteger[] inputs) {
		String text = inputs[WHOLE].getText();
		int minus = text.indexOf('-');
		int sign = 1;
		if (minus != -1) {
			sign = -1;
			text = text.substring(minus + 1);
		}
		int whole = EditInteger.getInt(text, 0);
		int numer = inputs[NUMER].getInt(0);
		int denom = inputs[DENOM].getInt(0);
		if (denom == 0) {
			numer = 0;
			denom = 1;
		}
		return new Fraction(sign, whole, numer, denom);
	}


	private Fraction readReal(EditDecimal input) {
		String text = input.getText();
		String[] parts = separator.split(text);
		int minus = parts[0].indexOf('-');
		int sign = 1;
		if (minus != -1) {
			sign = -1;
			parts[0] = parts[0].substring(minus + 1);
		}
		int whole = EditInteger.getInt(parts[0], 0);
		int numer = 0;
		int denom = 1;
		if (parts.length > 1) {
			if (parts[1].length() > 0) {
				numer = EditInteger.getInt(parts[1], 0);
				int digits = Integer.toString(numer).length();
				denom = (int)Math.pow(10, digits);
				int gcd = (int)Mathematics.gcd(numer, denom);
				numer /= gcd;
				denom /= gcd;
			}
		}
		return new Fraction(sign, whole, numer, denom);
	}


	private void showFraction(ControlWrapper[] ctrls, Fraction fract) {
		int sign = fract.getSign();
		int whole = fract.getWhole();
		String text = "";
		if (whole == 0) {
			if (sign < 0) {
				text = "-";
			}
		}
		else {
			text = fmtrInt.format(sign * whole);
		}
		ctrls[WHOLE].setText(text);

		int numer = fract.getNumer();
		if (numer != 0) {
			int denom = fract.getDenom();
			ctrls[NUMER].setText(fmtrInt.format(numer));
			ctrls[DENOM].setText(fmtrInt.format(denom));
		}
		else {
			ctrls[NUMER].clear();
			ctrls[DENOM].clear();
		}
	}


	private void showReal(ControlWrapper ctrl, Fraction fract) {
		ctrl.setText(fmtrDec.format(fract.doubleValue()));
	}
}
