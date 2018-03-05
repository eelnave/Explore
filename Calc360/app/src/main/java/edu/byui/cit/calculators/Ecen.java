package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;


public class Ecen extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal mAh, v, wh;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ratio, container, false);

		mAh = new EditDecimal(view, R.id.mAhInput, this);
		v = new EditDecimal(view, R.id.vInput, this);
		wh = new EditDecimal(view, R.id.whInput, this);

		EditWrapper[] inputs = { mAh, v, wh};

		SolveEquation.Solver[] solvers = new SolveEquation.Solver[]{
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double b = v.getDec();
						double x = wh.getDec();
						double y = decY.getDec();
						double a = b * x / y;
						mAh.setText(fmtrDec.format(a));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double a = mAh.getDec();
						double x = wh.getDec();
						double y = decY.getDec();
						double b = a * y / x;
						v.setText(fmtrDec.format(b));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double a = mAh.getDec();
						double b = v.getDec();
						double y = decY.getDec();
						double x = y * a / b;
						wh.setText(fmtrDec.format(x));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double a = mAh.getDec();
						double b = v.getDec();
						double x = wh.getDec();
						double y = x * b / a;
						decY.setText(fmtrDec.format(y));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.ecenclear, inputs);
		return view;
	}
}