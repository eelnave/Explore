package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;

import static edu.byui.cit.model.Physics.CoulombsLaw.*;


public final class CoulombsLaw extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decForce, decQ1, decQ2, decDist;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coulombs_law, container, false);
		decForce = new EditDec(view, R.id.decForce, this);
		decQ1 = new EditDec(view, R.id.decQ1, this);
		decQ2 = new EditDec(view, R.id.decQ2, this);
		decDist = new EditDec(view, R.id.decDist, this);
		EditWrapper[] inputs = { decForce, decQ1, decQ2, decDist};

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double q1 = decQ1.getDec();
						double q2 = decQ2.getDec();
						double r = decDist.getDec();
						double F = force(q1, q2, r);
						decForce.setText(fmtrDec.format(F));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double F = decForce.getDec();
						double q2 = decQ2.getDec();
						double r = decDist.getDec();
						double q1 = charge(F, q2, r);
						decQ1.setText(fmtrDec.format(q1));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double F = decForce.getDec();
						double q1 = decQ1.getDec();
						double r = decDist.getDec();
						double q2 = charge(F, q1, r);
						decQ2.setText(fmtrDec.format(q2));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double F = decForce.getDec();
						double q1 = decQ1.getDec();
						double q2 = decQ2.getDec();
						double r = distance(F, q1, q2);
						decDist.setText(fmtrDec.format(r));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear);
		return view;
	}
}
