package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;

import static edu.byui.cit.model.Geometry.RectangularPrism.*;


public final class RectPrism extends SolveSeries {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decWidth, decHeight, decLength, decSurfArea, decVolume;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.rect_prism, container,
				false);

		decWidth = new EditDecimal(view, R.id.decWidth, this);
		decHeight = new EditDecimal(view, R.id.decHeight, this);
		decLength = new EditDecimal(view, R.id.decLength, this);
		decSurfArea = new EditDecimal(view, R.id.decSurfArea, this);
		decVolume = new EditDecimal(view, R.id.decVolume, this);
		EditWrapper[] inputs = new EditWrapper[]{
				decWidth, decHeight, decLength, decSurfArea, decVolume
		};

		Solver[] solvers = new Solver[]{
				new Solver(new EditWrapper[]{ decWidth, decHeight, decLength },
						new WidgetWrapper[]{ decSurfArea, decVolume }) {
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
						new WidgetWrapper[]{ decLength, decVolume }) {
					@Override
					public void solve() {
						solveSSA(decWidth, decHeight, decLength);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decHeight, decVolume },
						new WidgetWrapper[]{ decLength, decSurfArea }) {
					@Override
					public void solve() {
						solveSSV(decWidth, decHeight, decLength);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decLength, decSurfArea },
						new WidgetWrapper[]{ decHeight, decVolume }) {
					@Override
					public void solve() {
						solveSSA(decWidth, decLength, decHeight);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decLength, decVolume },
						new WidgetWrapper[]{ decHeight, decSurfArea }) {
					@Override
					public void solve() {
						solveSSV(decWidth, decLength, decHeight);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decSurfArea, decVolume },
						new WidgetWrapper[]{ decHeight, decLength }) {
					@Override
					public void solve() {
						solveSAV(decWidth, decHeight, decLength);
					}
				},

				new Solver(new EditWrapper[]{ decHeight, decLength, decSurfArea },
						new WidgetWrapper[]{ decWidth, decVolume }) {
					@Override
					public void solve() {
						solveSSA(decHeight, decLength, decWidth);
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decLength, decVolume },
						new WidgetWrapper[]{ decWidth, decSurfArea }) {
					@Override
					public void solve() {
						solveSSV(decHeight, decLength, decWidth);
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decSurfArea, decVolume },
						new WidgetWrapper[]{ decWidth, decLength }) {
					@Override
					public void solve() {
						solveSAV(decHeight, decWidth, decLength);
					}
				},

				new Solver(new EditWrapper[]{ decLength, decSurfArea, decVolume },
						new WidgetWrapper[]{ decWidth, decHeight }) {
					@Override
					public void solve() {
						solveSAV(decLength, decWidth, decHeight);
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}

	private void solveSSA(
			EditDecimal decSide1, EditDecimal decSide2, EditDecimal decSide3) {
		double side1 = decSide1.getDec();
		double side2 = decSide2.getDec();
		double A = decSurfArea.getDec();
		double side3 = sideSSA(side1, side2, A);
		double V = volume(side1, side2, side3);
		decSide3.setText(fmtrDec.format(side3));
		decVolume.setText(fmtrDec.format(V));
	}

	private void solveSSV(
			EditDecimal decSide1, EditDecimal decSide2, EditDecimal decSide3) {
		double side1 = decSide1.getDec();
		double side2 = decSide2.getDec();
		double V = decVolume.getDec();
		double side3 = sideSSV(side1, side2, V);
		double A = surfArea(side1, side2, side3);
		decSide3.setText(fmtrDec.format(side3));
		decSurfArea.setText(fmtrDec.format(A));
	}

	private void solveSAV(
			EditDecimal decSide1, EditDecimal decSide2, EditDecimal decSide3) {
		double side1 = decSide1.getDec();
		double A = decSurfArea.getDec();
		double V = decVolume.getDec();
		double side2 = sideSAV(side1, A, V);
		double side3 = sideSSV(side1, side2, V);
		decSide2.setText(fmtrDec.format(side2));
		decSide3.setText(fmtrDec.format(side3));
	}
}
