package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;

import static edu.byui.cit.model.Physics.Pendulum.*;


public class Pendulum extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decTime, decLength, decGravity;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pendulum, container, false);

		decTime = new EditDecimal(view, R.id.decTime, this);
		decLength = new EditDecimal(view, R.id.decLength, this);
		decGravity = new EditDecimal(view, R.id.decGravity, this);
		EditWrapper[] inputs = { decTime, decLength, decGravity };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double length = decLength.getDec();
						double gravity = decGravity.getDec();
						double time = time(length, gravity);
						decTime.setText(fmtrDec.format(time));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double time = decTime.getDec();
						double gravity = decGravity.getDec();
						double length = length(time, gravity);
						decLength.setText(fmtrDec.format(length));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double time = decTime.getDec();
						double length = decLength.getDec();
						double gravity = gravity(time, length);
						decGravity.setText(fmtrDec.format(gravity));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
