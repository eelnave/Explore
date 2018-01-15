package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSome;
import edu.byui.cit.text.Control;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;

import static edu.byui.cit.model.Geometry.Cone.*;


public final class Cone extends SolveSome {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decRadius, decHeight, decSide, decSurfArea, decVolume;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.cone, container, false);

		decRadius = new EditDec(view, R.id.decRadius, this);
		decHeight = new EditDec(view, R.id.decHeight, this);
		decSide = new EditDec(view, R.id.decSide, this);
		decSurfArea = new EditDec(view, R.id.decSurfArea, this);
		decVolume = new EditDec(view, R.id.decVolume, this);
		Control[] toClear = new Control[] {
				decRadius, decHeight, decSide, decSurfArea, decVolume
		};

		Solver[] solvers = new Solver[] {
				new Solver(new Input[] { decRadius, decHeight }) {
					@Override
					public void solve() {
						double r = decRadius.getDec();
						double h = decHeight.getDec();
						decSide.setText(fmtrDec.format(sideLen(r, h)));
						decSurfArea.setText(fmtrDec.format(surfArea(r, h)));
						decVolume.setText(fmtrDec.format(volume(r, h)));
					}
				},
				new Solver(new Input[] { decRadius, decSide }) {
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
				new Solver(new Input[] { decHeight, decSide }) {
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
				new Solver(new Input[] { decRadius, decSurfArea}) {
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
				new Solver(new Input[] { decRadius, decVolume }) {
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
				new Solver(new Input[] { decHeight, decSurfArea}) {
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
				new Solver(new Input[] { decHeight, decVolume }) {
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

		initialize(view, R.id.btnClear, toClear, solvers);
		return view;
	}
}
