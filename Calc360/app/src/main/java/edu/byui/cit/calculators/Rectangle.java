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

import static edu.byui.cit.model.Geometry.Rectangle.*;


public final class Rectangle extends SolveSome {
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
		Control[] toClear = new Control[]{
				decWidth, decHeight, decDiag, decPerim, decArea
		};

		Solver[] solvers = new Solver[]{
				// width && (height || diag || perim || area)
				new Solver(new Input[]{ decWidth, decHeight }) {
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
				new Solver(new Input[]{ decWidth, decDiag }) {
					@Override
					public void solve() {
						double width = decWidth.getDec();
						double diag = decDiag.getDec();
						double height = sideSD(width, diag);
						double perim = perimeter(width, height);
						double area = area(width, height);
						decHeight.setText(fmtrDec.format(height));
						decPerim.setText(fmtrDec.format(perim));
						decArea.setText(fmtrDec.format(area));
					}
				},
				new Solver(new Input[]{ decWidth, decPerim }) {
					@Override
					public void solve() {
						double width = decWidth.getDec();
						double perim = decPerim.getDec();
						double height = sideSP(width, perim);
						double diag = diagonal(width, height);
						double area = area(width, height);
						decHeight.setText(fmtrDec.format(height));
						decDiag.setText(fmtrDec.format(diag));
						decArea.setText(fmtrDec.format(area));
					}
				},
				new Solver(new Input[]{ decWidth, decArea }) {
					@Override
					public void solve() {
						double width = decWidth.getDec();
						double area = decArea.getDec();
						double height = sideSA(width, area);
						double diag = diagonal(width, height);
						double perim = perimeter(width, height);
						decHeight.setText(fmtrDec.format(height));
						decDiag.setText(fmtrDec.format(diag));
						decPerim.setText(fmtrDec.format(perim));
					}
				},

				// height && (diag || perim || area)
				new Solver(new Input[]{ decHeight, decDiag }) {
					@Override
					public void solve() {
						double height = decHeight.getDec();
						double diag = decDiag.getDec();
						double width = sideSD(height, diag);
						double perim = perimeter(width, height);
						double area = area(width, height);
						decWidth.setText(fmtrDec.format(width));
						decPerim.setText(fmtrDec.format(perim));
						decArea.setText(fmtrDec.format(area));
					}
				},
				new Solver(new Input[]{ decHeight, decPerim }) {
					@Override
					public void solve() {
						double height = decHeight.getDec();
						double perim = decPerim.getDec();
						double width = sideSP(height, perim);
						double diag = diagonal(width, height);
						double area = area(width, height);
						decWidth.setText(fmtrDec.format(width));
						decDiag.setText(fmtrDec.format(diag));
						decArea.setText(fmtrDec.format(area));
					}
				},
				new Solver(new Input[]{ decHeight, decArea }) {
					@Override
					public void solve() {
						double height = decHeight.getDec();
						double area = decArea.getDec();
						double width = sideSA(height, area);
						double diag = diagonal(width, height);
						double perim = perimeter(width, height);
						decWidth.setText(fmtrDec.format(width));
						decDiag.setText(fmtrDec.format(diag));
						decPerim.setText(fmtrDec.format(perim));
					}
				},

				// diag && (perim || area)
				new Solver(new Input[]{ decDiag, decPerim }) {
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
				new Solver(new Input[]{ decDiag, decArea }) {
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
				new Solver(new Input[]{ decPerim, decArea }) {
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

		initialize(view, R.id.btnClear, toClear, solvers);
		return view;
	}
}
