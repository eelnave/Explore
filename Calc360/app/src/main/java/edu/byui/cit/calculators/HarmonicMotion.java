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

import static edu.byui.cit.model.Physics.HarmonicMotion.*;


public final class HarmonicMotion extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decTime, decMass, decSpring;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.harmonic_motion, container, false);

		decTime = new EditDec(view, R.id.decTime, this);
		decMass = new EditDec(view, R.id.decMass, this);
		decSpring = new EditDec(view, R.id.decSpring, this);
		EditWrapper[] inputs = { decTime, decMass, decSpring };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double mass = decMass.getDec();
						double spring = decSpring.getDec();
						double time = time(mass, spring);
						decTime.setText(fmtrDec.format(time));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double time = decTime.getDec();
						double spring = decSpring.getDec();
						double mass = mass(time, spring);
						decMass.setText(fmtrDec.format(mass));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double time = decTime.getDec();
						double mass = decMass.getDec();
						double spring = spring(time, mass);
						decSpring.setText(fmtrDec.format(spring));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
