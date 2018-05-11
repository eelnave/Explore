package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;


public class ElectricEnergy extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decEner, decCur, decVolt, decTime;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.electric_energy, container, false);

		decEner = new EditDecimal(view, R.id.decEnergy, this);
		decCur = new EditDecimal(view, R.id.decCurrent, this);
		decVolt = new EditDecimal(view, R.id.decVoltage, this);
		decTime = new EditDecimal(view, R.id.decTime, this);

		EditWrapper[] inputs = {decEner, decCur, decVolt, decTime};

		Solver[] solvers = new Solver[]{
			new Solver() {
				@Override
				public void solve() {
					double current = decCur.getDec();
					double voltage = decVolt.getDec();
					double time = decTime.getDec();
					double energy = current * voltage * time;
					decEner.setText(fmtrDec.format(energy));
				}
			},
			new Solver() {
				@Override
				public void solve() {
					double energy = decEner.getDec();
					double voltage = decVolt.getDec();
					double time = decTime.getDec();
					double current = energy / (voltage * time);
					decCur.setText(fmtrDec.format(current));
				}
			},
			new Solver() {
				@Override
				public void solve() {
					double energy = decEner.getDec();
					double current = decCur.getDec();
					double time = decTime.getDec();
					double voltage = energy / (current * time);
					decVolt.setText(fmtrDec.format(voltage));
				}
			},
			new Solver() {
				@Override
				public void solve() {
					double energy = decEner.getDec();
					double voltage = decVolt.getDec();
					double current = decCur.getDec();
					double time = energy / (voltage * current);
					decVolt.setText(fmtrDec.format(time));
				}
			}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
