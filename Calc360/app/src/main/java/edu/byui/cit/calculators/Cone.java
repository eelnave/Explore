package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;

import static edu.byui.cit.model.Geometry.Cone.*;


public final class Cone extends SolveSeries {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decRadius, decHeight, decSide, decSurfArea, decVolume;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.cone, container, false);

		decRadius = new EditDecimal(view, R.id.decRadius, this);
		decHeight = new EditDecimal(view, R.id.decHeight, this);
		decSide = new EditDecimal(view, R.id.decSide, this);
		decSurfArea = new EditDecimal(view, R.id.decSurfArea, this);
		decVolume = new EditDecimal(view, R.id.decVolume, this);

		EditWrapper[] inputs = new EditWrapper[]{
				decRadius, decHeight, decSide, decSurfArea, decVolume
		};

		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decRadius, decHeight },
						new ControlWrapper[]{ decSide, decSurfArea, decVolume }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double h = decHeight.getDec();
						decSide.setText(fmtrDec.format(sideLen(r, h)));
						decSurfArea.setText(fmtrDec.format(surfArea(r, h)));
						decVolume.setText(fmtrDec.format(volume(r, h)));
					}
				},
				new Solver(new EditWrapper[]{ decRadius, decSide },
						new ControlWrapper[]{ decHeight, decSurfArea, decVolume }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double s = decSide.getDec();
						double h = heightRS(r, s);
						decHeight.setText(fmtrDec.format(h));
						decSurfArea.setText(fmtrDec.format(surfArea(r, h)));
						decVolume.setText(fmtrDec.format(volume(r, h)));
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decSide },
						new ControlWrapper[]{ decRadius, decSurfArea, decVolume }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double s = decSide.getDec();
						double r = radiusHS(h, s);
						decRadius.setText(fmtrDec.format(r));
						decSurfArea.setText(fmtrDec.format(surfArea(r, h)));
						decVolume.setText(fmtrDec.format(volume(r, h)));
					}
				},
				new Solver(new EditWrapper[]{ decRadius, decSurfArea },
						new ControlWrapper[]{ decHeight, decSide, decVolume }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double a = decSurfArea.getDec();
						double h = heightRA(r, a);
						decHeight.setText(fmtrDec.format(h));
						decSide.setText(fmtrDec.format(sideLen(r, h)));
						decVolume.setText(fmtrDec.format(volume(r, h)));
					}
				},
				new Solver(new EditWrapper[]{ decRadius, decVolume },
						new ControlWrapper[]{ decHeight, decSide, decSurfArea }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double v = decVolume.getDec();
						double h = heightRV(r, v);
						decHeight.setText(fmtrDec.format(h));
						decSide.setText(fmtrDec.format(sideLen(r, h)));
						decSurfArea.setText(fmtrDec.format(surfArea(r, h)));
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decSurfArea },
						new ControlWrapper[]{ decRadius, decSide, decVolume }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double a = decSurfArea.getDec();
						double r = radiusHA(h, a);
						decRadius.setText(fmtrDec.format(r));
						decSide.setText(fmtrDec.format(sideLen(r, h)));
						decVolume.setText(fmtrDec.format(volume(r, h)));
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decVolume },
						new ControlWrapper[]{ decRadius, decSide, decSurfArea }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double v = decVolume.getDec();
						double r = radiusHV(h, v);
						decRadius.setText(fmtrDec.format(r));
						decSide.setText(fmtrDec.format(sideLen(r, h)));
						decSurfArea.setText(fmtrDec.format(surfArea(r, h)));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}
