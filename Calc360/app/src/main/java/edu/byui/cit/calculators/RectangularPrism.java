package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveSome;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;

import static edu.byui.cit.model.Geometry.RectangularPrism.*;


public final class RectangularPrism extends SolveSome {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decWidth, decHeight, decLength, decSurfArea, decVolume;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.rectangular_prism, container,
				false);

		decWidth = new EditDec(view, R.id.decWidth, this);
		decHeight = new EditDec(view, R.id.decHeight, this);
		decLength = new EditDec(view, R.id.decLength, this);
		decSurfArea = new EditDec(view, R.id.decSurfArea, this);
		decVolume = new EditDec(view, R.id.decVolume, this);
		Input[] inputs = new Input[] {
				decWidth, decHeight, decLength, decSurfArea, decVolume
		};

		Solver[] solvers = new Solver[] {
				new Solver(new Input[] { decWidth, decHeight, decLength }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double h = decHeight.getDec();
						double g = decLength.getDec();
						decSurfArea.setText(fmtrDec.format(surfArea(w, h, g)));
						decVolume.setText(fmtrDec.format(volume(w, h, g)));
					}
				},

				new Solver(new Input[] { decWidth, decHeight, decSurfArea }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double h = decHeight.getDec();
						double A = decSurfArea.getDec();
						double g = sideSSA(w, h, A);
						decLength.setText(fmtrDec.format(g));
						decVolume.setText(fmtrDec.format(volume(w, h, g)));
					}
				},
				new Solver(new Input[] { decWidth, decHeight, decVolume }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double h = decHeight.getDec();
						double V = decVolume.getDec();
						double g = sideSSV(w, h, V);
						decLength.setText(fmtrDec.format(g));
						decSurfArea.setText(fmtrDec.format(surfArea(w, h, g)));
					}
				},
				new Solver(new Input[] { decWidth, decLength, decSurfArea }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double g = decLength.getDec();
						double A = decSurfArea.getDec();
						double h = sideSSA(w, g, A);
						decHeight.setText(fmtrDec.format(h));
						decVolume.setText(fmtrDec.format(volume(w, h, g)));
					}
				},
				new Solver(new Input[] { decWidth, decLength, decVolume }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double g = decLength.getDec();
						double V = decVolume.getDec();
						double h = sideSSA(w, g, V);
						decHeight.setText(fmtrDec.format(h));
						decSurfArea.setText(fmtrDec.format(surfArea(w, h, g)));
					}
				},
				new Solver(new Input[] { decHeight, decLength, decSurfArea }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double g = decLength.getDec();
						double A = decSurfArea.getDec();
						double w = sideSSA(h, g, A);
						decWidth.setText(fmtrDec.format(w));
						decVolume.setText(fmtrDec.format(volume(w, h, g)));
					}
				},
				new Solver(new Input[] { decHeight, decLength, decVolume }) {
					@Override
					public void solve() {
						double h = decHeight.getDec();
						double g = decLength.getDec();
						double V = decVolume.getDec();
						double w = sideSSA(h, g, V);
						decWidth.setText(fmtrDec.format(w));
						decSurfArea.setText(fmtrDec.format(surfArea(w, h, g)));
					}
				},

				new Solver(new Input[] { decWidth, decSurfArea, decVolume }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double A = decSurfArea.getDec();
						double V = decVolume.getDec();
						double h = sideSAV(w, A, V);
						double g = sideSSV(w, h, V);
						decWidth.setText(fmtrDec.format(w));
						decSurfArea.setText(fmtrDec.format(surfArea(w, h, g)));
					}
				},
		};

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}
}
