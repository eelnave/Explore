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

import static edu.byui.cit.model.Chemistry.GasEnergy.*;


public final class GasEnergy extends SolveAll {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decEnergy, decMoles, decGasConst, decTemp;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gas_energy, container, false);

		decEnergy = new EditDec(view, R.id.decEnergy, this);
		decMoles = new EditDec(view, R.id.decMoles, this);
		decGasConst = new EditDec(view, R.id.decGasConst, this);
		decTemp = new EditDec(view, R.id.decTemp, this);
		Input[] inputs = { decEnergy, decMoles, decGasConst, decTemp };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double moles = decMoles.getDec();
						double gasConst = decGasConst.getDec();
						double temp = decTemp.getDec();
						double energy = energy(moles, gasConst, temp);
						decEnergy.setText(fmtrDec.format(energy));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double energy = decEnergy.getDec();
						double gasConst = decGasConst.getDec();
						double temp = decTemp.getDec();
						double moles = moles(gasConst, temp, energy);
						decMoles.setText(fmtrDec.format(moles));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double energy = decEnergy.getDec();
						double moles = decMoles.getDec();
						double temp = decTemp.getDec();
						double gasConst = gasConst(moles, temp, energy);
						decGasConst.setText(fmtrDec.format(gasConst));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double energy = decEnergy.getDec();
						double moles = decMoles.getDec();
						double gasConst = decGasConst.getDec();
						double temp = temperature(moles, gasConst, energy);
						decTemp.setText(fmtrDec.format(temp));
					}
				}
		};

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}
}
