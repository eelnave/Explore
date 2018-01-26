package edu.byui.cit.calculators;

import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;


public final class Ratio extends SolveEquation {
	// Create a number formatter object that
	// will format numbers for the user to see.
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decA, decB, decX, decY;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ratio, container, false);

		decA = new EditDec(view, R.id.decA, this);
		decB = new EditDec(view, R.id.decB, this);
		decX = new EditDec(view, R.id.decX, this);
		decY = new EditDec(view, R.id.decY, this);
		EditWrapper[] inputs = { decA, decB, decX, decY };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double b = decB.getDec();
						double x = decX.getDec();
						double y = decY.getDec();
						double a = b * x / y;
						decA.setText(fmtrDec.format(a));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double a = decA.getDec();
						double x = decX.getDec();
						double y = decY.getDec();
						double b = a * y / x;
						decB.setText(fmtrDec.format(b));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double a = decA.getDec();
						double b = decB.getDec();
						double y = decY.getDec();
						double x = a * y / b;
						decX.setText(fmtrDec.format(x));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double a = decA.getDec();
						double b = decB.getDec();
						double x = decX.getDec();
						double y = b * x / a;
						decY.setText(fmtrDec.format(y));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear);
		return view;
	}
}
