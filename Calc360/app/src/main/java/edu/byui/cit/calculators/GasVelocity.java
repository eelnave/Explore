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

import static edu.byui.cit.model.Chemistry.GasVelocity.*;


public final class GasVelocity extends SolveAll {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decVeloc, decGasConst, decTemp, decMass;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_velocity, container, false);

		decVeloc = new EditDec(view, R.id.decV, this);
		decGasConst = new EditDec(view, R.id.decR, this);
		decTemp = new EditDec(view, R.id.decT, this);
		decMass = new EditDec(view, R.id.decM, this);
		Input[] inputs = { decVeloc, decGasConst, decTemp, decMass };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double gasConst = decGasConst.getDec();
						double temp = decTemp.getDec();
						double mass = decMass.getDec();
						double veloc = velocity(gasConst, temp, mass);
						decVeloc.setText(fmtrDec.format(veloc));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double veloc = decVeloc.getDec();
						double temp = decTemp.getDec();
						double mass = decMass.getDec();
						double gasConst = gasConst(temp, mass, veloc);
						decGasConst.setText(fmtrDec.format(gasConst));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double veloc = decVeloc.getDec();
						double gasConst = decGasConst.getDec();
						double mass = decMass.getDec();
						double temp = temperature(gasConst, mass, veloc);
						decTemp.setText(fmtrDec.format(temp));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double veloc = decVeloc.getDec();
						double gasConst = decGasConst.getDec();
						double temp = decTemp.getDec();
						double mass = molarMass(gasConst, temp, veloc);
						decMass.setText(fmtrDec.format(mass));
					}
				}
		};

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}
}
