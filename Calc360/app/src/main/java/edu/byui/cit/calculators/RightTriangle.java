package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSome;
import edu.byui.cit.text.Control;
import edu.byui.cit.text.EditAngle;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.units.Angle;
import edu.byui.cit.units.Unit;

import static edu.byui.cit.model.Geometry.RightTriangle.*;


public class RightTriangle extends SolveSome {
	private final NumberFormat fmtrDec, fmtrAngle;
	private EditDec decA, decB, decHyp;
	private EditAngle decAlpha, decBeta;
	private EditAngle[] angles;
	private TextWrapper decPerim, decArea;
	private SpinUnit spinner;

	public RightTriangle() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrAngle = NumberFormat.getInstance();
		fmtrAngle.setMaximumFractionDigits(5);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.right_triangle, container,
				false);

		Activity act = getActivity();
		spinner = new SpinUnit(act, view, R.id.spinner,
				Angle.getInstance(), R.array.triUnits,
				Calc360.KEY_ANGLE_UNITS, this);
		SharedPreferences prefs = act.getPreferences(
				Context.MODE_PRIVATE);
		spinner.restore(prefs, Angle.deg);

		decA = new EditDec(view, R.id.decSideA, this);
		decB = new EditDec(view, R.id.decSideB, this);
		decHyp = new EditDec(view, R.id.decSideC, this);
		decAlpha = new EditAngle(view, R.id.decAlpha, this);
		decBeta = new EditAngle(view, R.id.decBeta, this);
		EditAngle decGamma = new EditAngle(view, R.id.decGamma, this);
		decPerim = new TextWrapper(view, R.id.decPerim);
		decArea = new TextWrapper(view, R.id.decArea);
		angles = new EditAngle[]{ decAlpha, decBeta, decGamma };
		Control[] toClear = new Control[]{
				decA, decB, decHyp, decAlpha, decBeta,
				decPerim, decArea
		};

		// Call getRad to initialize the double radians value inside decGamma.
		decGamma.getRad(Angle.getInstance().get(Angle.deg));

		Solver[] solvers = new Solver[]{
				// side1 && side2
				new Solver(new Input[]{ decA, decB }) {
					@Override
					public void solve() {
						solveSS();
					}
				},

				// side && hypotenuse
				new Solver(new Input[]{ decA, decHyp }) {
					@Override
					public void solve() {
						solveSH(decA, decAlpha, decB, decBeta);
					}
				},
				new Solver(new Input[]{ decB, decHyp }) {
					@Override
					public void solve() {
						solveSH(decB, decBeta, decA, decAlpha);
					}
				},

				// side && opposite angle
				new Solver(new Input[]{ decA, decAlpha }) {
					@Override
					public void solve() {
						solveSO(decA, decAlpha, decB, decBeta);
					}
				},
				new Solver(new Input[]{ decB, decBeta }) {
					@Override
					public void solve() {
						solveSO(decB, decBeta, decA, decAlpha);
					}
				},

				// side && adjacent angle
				new Solver(new Input[]{ decA, decBeta }) {
					@Override
					public void solve() {
						solveSA(decA, decBeta, decB, decAlpha);
					}
				},
				new Solver(new Input[]{ decB, decAlpha }) {
					@Override
					public void solve() {
						solveSA(decB, decAlpha, decA, decBeta);
					}
				},

				// hypotenuse && angle
				new Solver(new Input[]{ decHyp, decAlpha }) {
					@Override
					public void solve() {
						solveHA(decAlpha, decA, decB, decBeta);
					}
				},
				new Solver(new Input[]{ decHyp, decBeta }) {
					@Override
					public void solve() {
						solveHA(decBeta, decB, decA, decAlpha);
					}
				},

				// one angle only
				new Solver(new Input[]{ decAlpha }) {
					@Override
					public void solve() {
						solveA(decAlpha, decBeta);
					}
				},
				new Solver(new Input[]{ decBeta }) {
					@Override
					public void solve() {
						solveA(decBeta, decAlpha);
					}
				}
		};

		initialize(view, R.id.btnClear, toClear, solvers);
		return view;
	}


	private void solveSS() {
		double a = decA.getDec();
		double b = decB.getDec();
		double hyp = hypot(a, b);
		double alpha = angleOH(a, hyp);
		double beta = angleA(alpha);
		decHyp.setText(fmtrDec.format(hyp));
		Unit user = spinner.getSelectedItem();
		decAlpha.setText(fmtrAngle, user, alpha);
		decBeta.setText(fmtrAngle, user, beta);
		perimAndArea(a, b, hyp);
	}

	private void solveSH(EditDec decSide1, EditAngle decAngle1,
			EditDec decSide2, EditAngle decAngle2) {
		double side1 = decSide1.getDec();
		double hyp = decHyp.getDec();
		double side2 = sideSH(side1, hyp);
		double angle1 = angleOH(side1, hyp);
		double angle2 = angleA(angle1);
		decSide2.setText(fmtrDec.format(side2));
		Unit user = spinner.getSelectedItem();
		decAngle1.setText(fmtrAngle, user, angle1);
		decAngle2.setText(fmtrAngle, user, angle2);
		perimAndArea(side1, side2, hyp);
	}

	private void solveSO(EditDec decSide1, EditAngle decAngle1,
			EditDec decSide2, EditAngle decAngle2) {
		Unit user = spinner.getSelectedItem();
		double side1 = decSide1.getDec();
		double angle1 = decAngle1.getRad(user);
		double angle2 = angleA(angle1);
		double side2 = sideSO(side1, angle2);
		double hyp = hypot(side1, side2);
		decAngle2.setText(fmtrAngle, user, angle2);
		decSide2.setText(fmtrDec.format(side2));
		decHyp.setText(fmtrDec.format(hyp));
		perimAndArea(side1, side2, hyp);
	}

	private void solveSA(EditDec decSide1, EditAngle decAngle2,
			EditDec decSide2, EditAngle decAngle1) {
		Unit user = spinner.getSelectedItem();
		double side1 = decSide1.getDec();
		double angle2 = decAngle2.getRad(user);
		double angle1 = angleA(angle2);
		double side2 = sideSO(side1, angle2);
		double hyp = hypot(side1, side2);
		decHyp.setText(fmtrDec.format(hyp));
		decSide2.setText(fmtrDec.format(side2));
		decAngle1.setText(fmtrAngle, user, angle1);
		perimAndArea(side1, side2, hyp);
	}

	private void solveHA(EditAngle decAngle1, EditDec decSide1,
			EditDec decSide2, EditAngle decAngle2) {
		Unit user = spinner.getSelectedItem();
		double hyp = decHyp.getDec();
		double angle1 = decAngle1.getRad(user);
		double angle2 = angleA(angle1);
		double side1 = sideHO(hyp, angle1);
		double side2 = sideHO(hyp, angle2);
		decAngle2.setText(fmtrAngle, user, angle2);
		decSide1.setText(fmtrDec.format(side1));
		decSide2.setText(fmtrDec.format(side2));
		perimAndArea(side1, side2, hyp);
	}

	private void perimAndArea(double side1, double side2, double hyp) {
		double perim = perimeter(side1, side2, hyp);
		double area = area(side1, side2);
		decPerim.setText(fmtrDec.format(perim));
		decArea.setText(fmtrDec.format(area));
	}

	private void solveA(EditAngle decAngle1, EditAngle decAngle2) {
		Unit user = spinner.getSelectedItem();
		double angle1 = decAngle1.getRad(user);
		double angle2 = angleA(angle1);
		decAngle2.setText(fmtrAngle, user, angle2);
	}


	@Override
	public void itemSelected(
			AdapterView<?> parent, View view, int pos, long id) {
		Unit user = spinner.getSelectedItem();
		for (EditAngle edit : angles) {
			if (edit.notEmpty()) {
				edit.setText(fmtrAngle, user);
			}
		}
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		spinner.save(editor);
	}
}
