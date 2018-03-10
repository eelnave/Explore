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

import static edu.byui.cit.model.Engineering.ElectricalEnergy.*;


public class Ecen extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal mAh, v, wh;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ecen, container, false);

		mAh = new EditDecimal(view, R.id.mAhInput, this);
		v = new EditDecimal(view, R.id.vInput, this);
		wh = new EditDecimal(view, R.id.whInput, this);

		EditWrapper[] inputs = { mAh, v, wh};

		SolveEquation.Solver[] solvers = new SolveEquation.Solver[]{
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double volt = v.getDec();
						double wattHour = wh.getDec();
						double milliampHour = milliampHour(volt, wattHour);
						mAh.setText(fmtrDec.format(milliampHour));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double milliampHour = mAh.getDec();
						double wattHour = wh.getDec();
						double volt = volt(milliampHour, wattHour);
						v.setText(fmtrDec.format(volt));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double milliampHour = mAh.getDec();
						double volt = v.getDec();
						double wattHour = wattHour(volt, milliampHour);
						wh.setText(fmtrDec.format(wattHour));
					}
				},
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}