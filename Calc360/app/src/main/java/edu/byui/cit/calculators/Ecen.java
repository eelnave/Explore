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
						double a = v.getDec();
						double b = wh.getDec();
						double c = (b*1000)/a;
						mAh.setText(fmtrDec.format(c));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double a = mAh.getDec();
						double b = wh.getDec();
						double c = (b*1000)/a;
						v.setText(fmtrDec.format(c));
					}
				},
				new SolveEquation.Solver() {
					@Override
					public void solve() {
						double a = mAh.getDec();
						double b = v.getDec();
						double c = (a*b)/1000;
						wh.setText(fmtrDec.format(c));
					}
				},
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}