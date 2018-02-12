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

import static edu.byui.cit.model.Physics.NewtonsSecond.*;


public final class NewtonsSecond extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decForce, decMass, decAccel;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.newtons_second, container, false);

		decForce = new EditDecimal(view, R.id.decForce, this);
		decMass = new EditDecimal(view, R.id.decMass, this);
		decAccel = new EditDecimal(view, R.id.decAccel, this);
		EditWrapper[] inputs = { decForce, decMass, decAccel };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double mass = decMass.getDec();
						double accel = decAccel.getDec();
						double force = force(mass, accel);
						decForce.setText(fmtrDec.format(force));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double force = decForce.getDec();
						double accel = decAccel.getDec();
						double mass = mass(force, accel);
						decMass.setText(fmtrDec.format(mass));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double force = decForce.getDec();
						double mass = decMass.getDec();
						double accel = accel(force, mass);
						decAccel.setText(fmtrDec.format(accel));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
