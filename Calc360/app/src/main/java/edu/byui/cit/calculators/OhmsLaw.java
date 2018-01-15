package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveAll;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;


/**
 * In Physics, Ohm's Law tells how the current flowing through
 * a circuit is related to the voltage and resistance:  V = IR
 */
public final class OhmsLaw extends SolveAll {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decVolt, decCur, decRes;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.ohms_law, container, false);

		decVolt = new EditDec(view, R.id.decVolt, this);
		decCur = new EditDec(view, R.id.decCur, this);
		decRes = new EditDec(view, R.id.decRes, this);

		// An array of all the inputs for this calculator.
		Input[] inputs = { decVolt, decCur, decRes };

		Solver[] solvers = new Solver[]{
				new Solver() {
					@Override
					public void solve() {
						// Compute voltage:  V = IR
						double i = decCur.getDec();
						double r = decRes.getDec();
						double v = i * r;
						decVolt.setText(fmtrDec.format(v));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						// Compute current:  I = V / R
						double v = decVolt.getDec();
						double r = decRes.getDec();
						double i = v / r;
						decCur.setText(fmtrDec.format(i));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						// Compute resistance:  R = V / I
						double v = decVolt.getDec();
						double i = decCur.getDec();
						double r = v / i;
						decRes.setText(fmtrDec.format(r));
					}
				}
		};

		// Initialize the code in the parent class SolveAll.
		initialize(view, R.id.btnClear, inputs, solvers);

		return view;
	}
}
