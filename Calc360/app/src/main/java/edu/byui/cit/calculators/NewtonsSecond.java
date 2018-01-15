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

import static edu.byui.cit.model.Physics.NewtonsSecond.*;


public final class NewtonsSecond extends SolveAll {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decForce, decMass, decAccel;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.newtons_second, container, false);

		decForce = new EditDec(view, R.id.decForce, this);
		decMass = new EditDec(view, R.id.decMass, this);
		decAccel = new EditDec(view, R.id.decAccel, this);
		Input[] inputs = { decForce, decMass, decAccel };

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

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}
}
