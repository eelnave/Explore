package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.WidgetWrapper;

import static edu.byui.cit.model.Geometry.Circle.*;


public class Circle extends SolveSeries {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decRadius, decDiam, decCircum, decArea;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.circle, container,
				false);
		decRadius = new EditDecimal(view, R.id.decRadius, this);
		decDiam = new EditDecimal(view, R.id.decDiam, this);
		decCircum = new EditDecimal(view, R.id.decCircum, this);
		decArea = new EditDecimal(view, R.id.decArea, this);

		EditWrapper[] inputs = { decRadius, decDiam, decCircum, decArea };

		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decRadius },
						new WidgetWrapper[]{ decDiam, decCircum, decArea }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double d = diameter(r);
						double c = circum(r);
						double a = area(r);
						decDiam.setText(fmtrDec.format(d));
						decCircum.setText(fmtrDec.format(c));
						decArea.setText(fmtrDec.format(a));
					}
				},
				new Solver(new EditWrapper[]{ decDiam },
						new WidgetWrapper[]{ decRadius, decCircum, decArea }) {
					@Override
					public void solve() {
						double d = decDiam.getDec();
						double r = radiusD(d);
						double c = circum(r);
						double a = area(r);
						decRadius.setText(fmtrDec.format(r));
						decCircum.setText(fmtrDec.format(c));
						decArea.setText(fmtrDec.format(a));
					}
				},
				new Solver(new EditWrapper[]{ decCircum },
						new WidgetWrapper[]{ decRadius, decDiam, decArea }) {
					@Override
					public void solve() {
						double c = decCircum.getDec();
						double r = radiusC(c);
						double d = diameter(r);
						double a = area(r);
						decRadius.setText(fmtrDec.format(r));
						decDiam.setText(fmtrDec.format(d));
						decArea.setText(fmtrDec.format(a));
					}
				},
				new Solver(new EditWrapper[]{ decArea },
						new WidgetWrapper[]{ decRadius, decDiam, decCircum }) {
					@Override
					public void solve() {
						double a = decArea.getDec();
						double r = radiusA(a);
						double d = diameter(r);
						double c = circum(r);
						decRadius.setText(fmtrDec.format(r));
						decDiam.setText(fmtrDec.format(d));
						decCircum.setText(fmtrDec.format(c));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
