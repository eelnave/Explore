package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;

import static edu.byui.cit.model.Geometry.Rectangle.*;


public final class Rectangle extends SolveSeries {
	private final NumberFormat fmtrDec = NumberFormat.getNumberInstance();
	private EditDec decWidth, decHeight, decDiag, decPerim, decArea;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.rectangle, container, false);

		//Attach all the fields to an EditDec or EditCur object
		decWidth = new EditDec(view, R.id.decWidth, this);
		decHeight = new EditDec(view, R.id.decHeight, this);
		decDiag = new EditDec(view, R.id.decDiag, this);
		decPerim = new EditDec(view, R.id.decPerim, this);
		decArea = new EditDec(view, R.id.decArea, this);

		EditWrapper[] inputs = { decWidth, decHeight, decDiag, decPerim, decArea };

		Solver[] solvers = new Solver[]{
				// width && (height || diag || perim || area)
				new Solver(new EditWrapper[]{ decWidth, decHeight },
						new ControlWrapper[]{ decDiag, decPerim, decArea }) {
					@Override
					public void solve() {
						double width = decWidth.getDec();
						double height = decHeight.getDec();
						double diag = diagonal(width, height);
						double perim = perimeter(width, height);
						double area = area(width, height);
						decDiag.setText(fmtrDec.format(diag));
						decPerim.setText(fmtrDec.format(perim));
						decArea.setText(fmtrDec.format(area));
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decDiag },
						new ControlWrapper[]{ decHeight, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSideDiag(decWidth, decHeight);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decPerim },
						new ControlWrapper[]{ decHeight, decDiag, decArea }) {
					@Override
					public void solve() {
						solveSidePerim(decWidth, decHeight);
					}
				},
				new Solver(new EditWrapper[]{ decWidth, decArea },
						new ControlWrapper[]{ decHeight, decDiag, decPerim }) {
					@Override
					public void solve() {
						solveSideArea(decWidth, decHeight);
					}
				},

				// height && (diag || perim || area)
				new Solver(new EditWrapper[]{ decHeight, decDiag },
						new ControlWrapper[]{ decWidth, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSideDiag(decHeight, decWidth);
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decPerim },
						new ControlWrapper[]{ decWidth, decDiag, decArea }) {
					@Override
					public void solve() {
						solveSidePerim(decHeight, decWidth);
					}
				},
				new Solver(new EditWrapper[]{ decHeight, decArea },
						new ControlWrapper[]{ decWidth, decDiag, decPerim }) {
					@Override
					public void solve() {
						solveSideArea(decHeight, decWidth);
					}
				},

				// diag && (perim || area)
				new Solver(new EditWrapper[]{ decDiag, decPerim },
						new ControlWrapper[]{ decWidth, decHeight, decArea }) {
					@Override
					public void solve() {
						double diag = decDiag.getDec();
						double perim = decPerim.getDec();
						double width = sideDP(diag, perim);
						double height = sideSD(width, diag);
						double area = area(width, height);
						decWidth.setText(fmtrDec.format(width));
						decHeight.setText(fmtrDec.format(height));
						decArea.setText(fmtrDec.format(area));
					}
				},
				new Solver(new EditWrapper[]{ decDiag, decArea },
						new ControlWrapper[]{ decWidth, decHeight, decPerim }) {
					@Override
					public void solve() {
						double diag = decDiag.getDec();
						double area = decArea.getDec();
						double width = sideDA(diag, area);
						double height = sideSD(width, diag);
						double perim = perimeter(width, height);
						decWidth.setText(fmtrDec.format(width));
						decHeight.setText(fmtrDec.format(height));
						decPerim.setText(fmtrDec.format(perim));
					}
				},

				// perim && area
				new Solver(new EditWrapper[]{ decPerim, decArea },
						new ControlWrapper[]{ decWidth, decHeight, decDiag }) {
					@Override
					public void solve() {
						double perim = decPerim.getDec();
						double area = decArea.getDec();
						double width = sidePA(perim, area);
						double height = sideSP(width, perim);
						double diag = diagonal(width, height);
						decWidth.setText(fmtrDec.format(width));
						decHeight.setText(fmtrDec.format(height));
						decDiag.setText(fmtrDec.format(diag));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}

	private void solveSideDiag(EditDec decSide1, EditDec decSide2) {
		double side1 = decSide1.getDec();
		double diag = decDiag.getDec();
		double side2 = sideSD(side1, diag);
		double perim = perimeter(side1, side2);
		double area = area(side1, side2);
		decSide2.setText(fmtrDec.format(side2));
		decPerim.setText(fmtrDec.format(perim));
		decArea.setText(fmtrDec.format(area));
	}

	private void solveSidePerim(EditDec decSide1, EditDec decSide2) {
		double side1 = decSide1.getDec();
		double perim = decPerim.getDec();
		double side2 = sideSP(side1, perim);
		double diag = diagonal(side1, side2);
		double area = area(side1, side2);
		decSide2.setText(fmtrDec.format(side2));
		decDiag.setText(fmtrDec.format(diag));
		decArea.setText(fmtrDec.format(area));
	}

	private void solveSideArea(EditDec decSide1, EditDec decSide2) {
		double side1 = decSide1.getDec();
		double area = decArea.getDec();
		double side2 = sideSA(side1, area);
		double diag = diagonal(side1, side2);
		double perim = perimeter(side1, side2);
		decSide2.setText(fmtrDec.format(side2));
		decDiag.setText(fmtrDec.format(diag));
		decPerim.setText(fmtrDec.format(perim));
	}
}
