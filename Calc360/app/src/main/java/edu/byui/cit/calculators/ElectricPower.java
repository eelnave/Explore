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


public class ElectricPower extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decPow, decCur, decVolt;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.electric_power, container, false);

		decPow = new EditDecimal(view, R.id.decPow, this);
		decCur = new EditDecimal(view, R.id.decCur, this);
		decVolt = new EditDecimal(view, R.id.decVolt, this);

		EditWrapper[] inputs = {decPow, decCur, decVolt};

		SolveEquation.Solver[] solvers = new SolveEquation.Solver[]{
			new SolveEquation.Solver() {
				@Override
				public void solve() {
					double current = decCur.getDec();
					double voltage = decVolt.getDec();
					double power = current * voltage;
					decPow.setText(fmtrDec.format(power));
				}
			},
			new SolveEquation.Solver() {
				@Override
				public void solve() {
					double power = decPow.getDec();
					double voltage = decVolt.getDec();
					double current = power / voltage;
					decCur.setText(fmtrDec.format(current));
				}
			},
			new SolveEquation.Solver() {
				@Override
				public void solve() {
					double power = decPow.getDec();
					double current = decCur.getDec();
					double voltage = power / current;
					decVolt.setText(fmtrDec.format(voltage));
				}
			},
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
