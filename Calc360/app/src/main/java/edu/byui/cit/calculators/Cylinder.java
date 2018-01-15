package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSome;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;

import static edu.byui.cit.model.Geometry.Cylinder.*;


public class Cylinder extends SolveSome {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decRadius, decHeight, decSurfArea, decVolume;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cylinder, container,
				false);
		decRadius = new EditDec(view, R.id.decRadius, this);
		decHeight = new EditDec(view, R.id.decHeight, this);
		decSurfArea = new EditDec(view, R.id.decSurfArea, this);
		decVolume = new EditDec(view, R.id.decVolume, this);
		Input[] inputs = { decRadius, decHeight, decSurfArea, decVolume };

		Solver[] solvers = new Solver[] {
				new Solver(new Input[] { decRadius, decHeight }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double h = decHeight.getDec();
						double a = surfArea(r, h);
						double v = volume(r, h);
						decSurfArea.setText(fmtrDec.format(a));
						decVolume.setText(fmtrDec.format(v));
					}
				},
				new Solver(new Input[] { decRadius, decSurfArea }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double a = decSurfArea.getDec();
						double h = heightRA(r, a);
						double v = volume(r, h);
						decHeight.setText(fmtrDec.format(h));
						decVolume.setText(fmtrDec.format(v));
					}
				},
				new Solver(new Input[] { decRadius, decVolume }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double v = decVolume.getDec();
						double h = heightRV(r, v);
						double a = surfArea(r, h);
						decHeight.setText(fmtrDec.format(h));
						decSurfArea.setText(fmtrDec.format(a));
					}
				},
				new Solver(new Input[] { decHeight, decSurfArea }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double a = decSurfArea.getDec();
						double r = radiusHA(h, a);
						double v = volume(r, h);
						decRadius.setText(fmtrDec.format(r));
						decVolume.setText(fmtrDec.format(v));
					}
				},
				new Solver(new Input[] { decHeight, decVolume }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double v = decVolume.getDec();
						double r = radiusHV(h, v);
						double a = surfArea(r, h);
						decRadius.setText(fmtrDec.format(r));
						decSurfArea.setText(fmtrDec.format(a));
					}
				}
		};

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}
}
