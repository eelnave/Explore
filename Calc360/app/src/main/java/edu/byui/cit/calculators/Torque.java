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

import static edu.byui.cit.model.Physics.Torque.*;


public class Torque extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decTorque, decForce, decRadius, decTheta;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.torque, container, false);
		decTorque = new EditDec(view, R.id.decTorque, this);
		decForce = new EditDec(view, R.id.decForce, this);
		decRadius = new EditDec(view, R.id.decRadius, this);
		decTheta = new EditDec(view, R.id.decTheta, this);
		EditWrapper[] inputs = { decTorque, decForce, decRadius, decTheta };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double force = decForce.getDec();
						double radius = decRadius.getDec();
						double theta = decTheta.getDec();
						double torque = torque(force, radius, theta);
						decTorque.setText(fmtrDec.format(torque));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double torque = decTorque.getDec();
						double radius = decRadius.getDec();
						double theta = decTheta.getDec();
						double force = force(torque, radius, theta);
						decForce.setText(fmtrDec.format(force));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double torque = decTorque.getDec();
						double force = decForce.getDec();
						double theta = decTheta.getDec();
						double radius = radius(torque, force, theta);
						decRadius.setText(fmtrDec.format(radius));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double torque = decTorque.getDec();
						double force = decForce.getDec();
						double radius = decRadius.getDec();
						double theta = theta(torque, force, radius);
						decTheta.setText(fmtrDec.format(theta));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear);
		return view;
	}
}
