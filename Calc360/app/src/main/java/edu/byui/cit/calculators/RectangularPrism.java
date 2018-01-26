package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;

import static edu.byui.cit.model.Geometry.RectangularPrism.*;


public final class RectangularPrism extends SolveSeries {
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
		EditWrapper[] inputs = new EditWrapper[]{
				decWidth, decHeight, decLength, decSurfArea, decVolume
		};

		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decWidth, decHeight, decLength },
						new ControlWrapper[]{ decSurfArea, decVolume }) {
					@Override
					public void solve() {
						double w = decWidth.getDec();
						double h = decHeight.getDec();
						double g = decLength.getDec();
						decSurfArea.setText(fmtrDec.format(surfArea(w, h, g)));
						decVolume.setText(fmtrDec.format(volume(w, h, g)));
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decHeight, decSurfArea },
						new ControlWrapper[]{ decLength, decVolume }) {
					@Override
					public void solve() {
						solveSSA(decWidth, decHeight, decLength);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decHeight, decVolume },
						new ControlWrapper[]{ decLength, decSurfArea }) {
					@Override
					public void solve() {
						solveSSV(decWidth, decHeight, decLength);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decLength, decSurfArea },
						new ControlWrapper[]{ decHeight, decVolume }) {
					@Override
					public void solve() {
						solveSSA(decWidth, decLength, decHeight);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decLength, decVolume },
						new ControlWrapper[]{ decHeight, decSurfArea }) {
					@Override
					public void solve() {
						solveSSV(decWidth, decLength, decHeight);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decSurfArea, decVolume },
						new ControlWrapper[]{ decHeight, decLength }) {
					@Override
					public void solve() {
						solveSAV(decWidth, decHeight, decLength);
					}
				},

				new Solver(new EditWrapper[]{ decHeight, decLength, decSurfArea },
						new ControlWrapper[]{ decWidth, decVolume }) {
					@Override
					public void solve() {
						solveSSA(decHeight, decLength, decWidth);
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decLength, decVolume },
						new ControlWrapper[]{ decWidth, decSurfArea }) {
					@Override
					public void solve() {
						solveSSV(decHeight, decLength, decWidth);
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decSurfArea, decVolume },
						new ControlWrapper[]{ decWidth, decLength }) {
					@Override
					public void solve() {
						solveSAV(decHeight, decWidth, decLength);
					}
				},

				new Solver(new EditWrapper[]{ decLength, decSurfArea, decVolume },
						new ControlWrapper[]{ decWidth, decHeight }) {
					@Override
					public void solve() {
						solveSAV(decLength, decWidth, decHeight);
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear);
		return view;
	}

	private void solveSSA(
			EditDec decSide1, EditDec decSide2, EditDec decSide3) {
		double side1 = decSide1.getDec();
		double side2 = decSide2.getDec();
		double A = decSurfArea.getDec();
		double side3 = sideSSA(side1, side2, A);
		double V = volume(side1, side2, side3);
		decSide3.setText(fmtrDec.format(side3));
		decVolume.setText(fmtrDec.format(V));
	}

	private void solveSSV(
			EditDec decSide1, EditDec decSide2, EditDec decSide3) {
		double side1 = decSide1.getDec();
		double side2 = decSide2.getDec();
		double V = decVolume.getDec();
		double side3 = sideSSV(side1, side2, V);
		double A = surfArea(side1, side2, side3);
		decSide3.setText(fmtrDec.format(side3));
		decSurfArea.setText(fmtrDec.format(A));
	}

	private void solveSAV(
			EditDec decSide1, EditDec decSide2, EditDec decSide3) {
		double side1 = decSide1.getDec();
		double A = decSurfArea.getDec();
		double V = decVolume.getDec();
		double side2 = sideSAV(side1, A, V);
		double side3 = sideSSV(side1, side2, V);
		decSide2.setText(fmtrDec.format(side2));
		decSide3.setText(fmtrDec.format(side3));
	}
}
