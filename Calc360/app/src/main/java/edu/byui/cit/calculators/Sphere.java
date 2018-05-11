package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;

import static edu.byui.cit.model.Geometry.Sphere.*;


public final class Sphere extends SolveSeries {
	private final NumberFormat fmtrStandard = NumberFormat.getInstance();
	private EditDecimal decVol, decRad, decSur;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Inflate layout
		View view = inflater.inflate(R.layout.sphere, container, false);

		decRad = new EditDecimal(view, R.id.decRad, this);
		decSur = new EditDecimal(view, R.id.decSur, this);
		decVol = new EditDecimal(view, R.id.decVol, this);
		EditWrapper[] inputs = { decRad, decSur, decVol };

		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decVol },
						new WidgetWrapper[]{ decRad, decSur }) {
					@Override
					public void solve() {
						double v = decVol.getDec();
						double r = radiusV(v);
						double s = surfArea(r);
						decRad.setText(fmtrStandard.format(r));
						decSur.setText(fmtrStandard.format(s));
					}
				},
				new Solver(new EditWrapper[]{ decSur },
						new WidgetWrapper[]{ decRad, decVol }) {
					@Override
					public void solve() {
						double s = decSur.getDec();
						double r = radiusA(s);
						double v = volume(r);
						decRad.setText(fmtrStandard.format(r));
						decVol.setText(fmtrStandard.format(v));
					}
				},
				new Solver(new EditWrapper[]{ decRad },
						new WidgetWrapper[]{ decSur, decVol }) {
					@Override
					public void solve() {
						double r = decRad.getDec();
						double s = surfArea(r);
						double v = volume(r);
						decSur.setText(fmtrStandard.format(s));
						decVol.setText(fmtrStandard.format(v));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
