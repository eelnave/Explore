package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;

import static edu.byui.cit.model.Chemistry.IdealGas.*;


public final class IdealGas extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decP, decV, decN, decR, decT;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.ideal_gas, container, false);

		decP = new EditDecimal(view, R.id.decP, this);
		decV = new EditDecimal(view, R.id.decV, this);
		decN = new EditDecimal(view, R.id.decN, this);
		decR = new EditDecimal(view, R.id.decR, this);
		decT = new EditDecimal(view, R.id.decT, this);

		// An array of all the inputs for this calculator.
		EditWrapper[] inputs = { decP, decV, decN, decR, decT };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double v = decV.getDec();
						double n = decN.getDec();
						double r = decR.getDec();
						double t = decT.getDec();
						double p = pressure(v, n, r, t);
						decP.setText(fmtrDec.format(p));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double p = decP.getDec();
						double n = decN.getDec();
						double r = decR.getDec();
						double t = decT.getDec();
						double v = volume(p, n, r, t);
						decV.setText(fmtrDec.format(v));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double p = decP.getDec();
						double v = decV.getDec();
						double r = decR.getDec();
						double t = decT.getDec();
						double n = moles(p, v, r, t);
						decN.setText(fmtrDec.format(n));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double p = decP.getDec();
						double v = decV.getDec();
						double n = decN.getDec();
						double t = decT.getDec();
						double r = gasConst(p, v, n, t);
						decR.setText(fmtrDec.format(r));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double p = decP.getDec();
						double v = decV.getDec();
						double n = decN.getDec();
						double r = decR.getDec();
						double t = temperature(p, v, n, r);
						decT.setText(fmtrDec.format(t));
					}
				}
		};

		// Initialize the code in the parent class SolveEquation.
		initialize(view, inputs, solvers, R.id.btnClear, inputs);

		return view;
	}
}
