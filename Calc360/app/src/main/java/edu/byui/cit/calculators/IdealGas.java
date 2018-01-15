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

import static edu.byui.cit.model.Chemistry.IdealGas.*;


public final class IdealGas extends SolveAll {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decP, decV, decN, decR, decT;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.ideal_gas, container, false);

		decP = new EditDec(view, R.id.decP, this);
		decV = new EditDec(view, R.id.decV, this);
		decN = new EditDec(view, R.id.decN, this);
		decR = new EditDec(view, R.id.decR, this);
		decT = new EditDec(view, R.id.decT, this);

		// An array of all the inputs for this calculator.
		Input[] inputs = { decP, decV, decN, decR, decT };

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

		// Initialize the code in the parent class SolveAll.
		initialize(view, R.id.btnClear, inputs, solvers);

		return view;
	}
}
