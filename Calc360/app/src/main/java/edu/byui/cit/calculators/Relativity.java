package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;

import static edu.byui.cit.model.Physics.MassEnergy.*;


public final class Relativity extends SolveSeries {
	private final DecimalFormat fmtrScientific = new DecimalFormat("0.000E0");
	private final NumberFormat fmtrStandard = NumberFormat.getInstance();
	private EditDecimal decEnergy, decMass;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.relativity, container, false);
		decEnergy = new EditDecimal(view, R.id.decEnergy, this);
		decMass = new EditDecimal(view, R.id.decMass, this);

		EditWrapper[] inputs = { decEnergy, decMass };

		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decMass }, new WidgetWrapper[]{ decEnergy }) {
					@Override
					public void solve() {
						double m = decMass.getDec();
						double e = energy(m);
						decEnergy.setText(format(e));
					}
				},
				new Solver(new EditWrapper[]{ decEnergy }, new WidgetWrapper[]{ decMass }) {
					@Override
					public void solve() {
						double e = decEnergy.getDec();
						double m = mass(e);
						decMass.setText(format(m));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}

	private String format(double x) {
		double pos = Math.abs(x);
		NumberFormat fmtr = 0.001 <= pos && pos < 1000 ?
				fmtrStandard : fmtrScientific;
		return fmtr.format(x);
	}
}
