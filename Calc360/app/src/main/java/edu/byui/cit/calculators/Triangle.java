package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditAngle;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.ItemSelectedHandler;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.units.Angle;
import edu.byui.cit.units.Unit;

import static edu.byui.cit.model.Geometry.Triangle.*;


public final class Triangle extends SolveSeries {
	private final NumberFormat fmtrDec, fmtrAngle;
	private EditDec decA, decB, decC;
	private EditAngle decAlpha, decBeta, decGamma;
	private EditAngle[] angles;
	private TextWrapper decPerim, decArea;
	private SpinUnit spinner;

	public Triangle() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrAngle = NumberFormat.getInstance();
		fmtrAngle.setMaximumFractionDigits(5);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.triangle, container, false);

		Activity act = getActivity();
		spinner = new SpinUnit(act, view, R.id.spinner,
				Angle.getInstance(), R.array.triUnits,
				Calc360.KEY_ANGLE_UNITS, new AngleUnits());

		decA = new EditDec(view, R.id.decSideA, this);
		decB = new EditDec(view, R.id.decSideB, this);
		decC = new EditDec(view, R.id.decSideC, this);
		decAlpha = new EditAngle(view, R.id.decAlpha, this);
		decBeta = new EditAngle(view, R.id.decBeta, this);
		decGamma = new EditAngle(view, R.id.decGamma, this);
		decPerim = new TextWrapper(view, R.id.decPerim);
		decArea = new TextWrapper(view, R.id.decArea);
		angles = new EditAngle[]{ decAlpha, decBeta, decGamma };

		EditWrapper[] inputs = { decA, decB, decC, decAlpha, decBeta, decGamma };
		TextWrapper[] outputs = { decPerim, decArea };

		Solver[] solvers = new Solver[]{
				// side, side, side
				new Solver(new EditWrapper[]{ decA, decB, decC },
						new ControlWrapper[]{ decAlpha, decBeta, decGamma, decPerim, decArea }) {
					@Override
					public void solve() {
						// Let's label the length of the three sides as
						// s, m, and g. If the three lengths are related
						// like s < m < g, then we want to use the law of
						// cosines to solve for the angle that is
						// opposite of the side with length m.
						double a = decA.getDec();
						double b = decB.getDec();
						double c = decC.getDec();
						if (a < b) {
							if (b < c) {
								// Use the law of cosines to solve for beta.
								solveSSS(b, c, a, decBeta, decGamma, decAlpha);
							}
							else {  // c <= b
								// Use the law of cosines to solve for gamma.
								solveSSS(c, a, b, decGamma, decAlpha, decBeta);
							}
						}
						else {  // b <= a
							if (a < c) {
								// Use the law of cosines to solve for alpha.
								solveSSS(a, b, c, decAlpha, decBeta, decGamma);
							}
							else {  // c <= a
								// Use the law of cosines to solve for gamma.
								solveSSS(c, a, b, decGamma, decAlpha, decBeta);
							}
						}
					}
				},

				// side, angle, side
				new Solver(new EditWrapper[]{ decA, decGamma, decB },
						new ControlWrapper[]{ decC, decAlpha, decBeta, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSAS(decA, decGamma, decB, decC, decAlpha, decBeta);
					}
				},
				new Solver(new EditWrapper[]{ decB, decAlpha, decC },
						new ControlWrapper[]{ decA, decBeta, decGamma, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSAS(decB, decAlpha, decC, decA, decBeta, decGamma);
					}
				},
				new Solver(new EditWrapper[]{ decC, decBeta, decA },
						new ControlWrapper[]{ decB, decGamma, decAlpha, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSAS(decC, decBeta, decA, decB, decGamma, decAlpha);
					}
				},

				// side, side, angle
				new Solver(new EditWrapper[]{ decA, decB, decAlpha },
						new ControlWrapper[]{ decBeta, decGamma, decC, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSSA(decA, decB, decAlpha, decBeta, decGamma, decC);
					}
				},
				new Solver(new EditWrapper[]{ decB, decC, decBeta },
						new ControlWrapper[]{ decGamma, decAlpha, decA, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSSA(decB, decC, decBeta, decGamma, decAlpha, decA);
					}
				},
				new Solver(new EditWrapper[]{ decC, decA, decGamma },
						new ControlWrapper[]{ decAlpha, decBeta, decB, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSSA(decC, decA, decGamma, decAlpha, decBeta, decB);
					}
				},
				new Solver(new EditWrapper[]{ decA, decC, decAlpha },
						new ControlWrapper[]{ decGamma, decBeta, decB, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSSA(decA, decC, decAlpha, decGamma, decBeta, decB);
					}
				},
				new Solver(new EditWrapper[]{ decB, decA, decBeta },
						new ControlWrapper[]{ decAlpha, decGamma, decC, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSSA(decB, decA, decBeta, decAlpha, decGamma, decC);
					}
				},
				new Solver(new EditWrapper[]{ decC, decB, decGamma },
						new ControlWrapper[]{ decBeta, decAlpha, decA, decPerim, decArea }) {
					@Override
					public void solve() {
						solveSSA(decC, decB, decGamma, decBeta, decAlpha, decA);
					}
				},

				// angle, angle, side
				new Solver(new EditWrapper[]{ decAlpha, decBeta, decA },
						new ControlWrapper[]{ decB, decGamma, decC, decPerim, decArea }) {
					@Override
					public void solve() {
						solveAAS(decAlpha, decBeta, decA, decB, decGamma, decC);
					}
				},
				new Solver(new EditWrapper[]{ decBeta, decGamma, decB },
						new ControlWrapper[]{ decC, decAlpha, decA, decPerim, decArea }) {
					@Override
					public void solve() {
						solveAAS(decBeta, decGamma, decB, decC, decAlpha, decA);
					}
				},
				new Solver(new EditWrapper[]{ decGamma, decAlpha, decC },
						new ControlWrapper[]{ decA, decBeta, decB, decPerim, decArea }) {
					@Override
					public void solve() {
						solveAAS(decGamma, decAlpha, decC, decA, decBeta, decB);
					}
				},
				new Solver(new EditWrapper[]{ decAlpha, decGamma, decA },
						new ControlWrapper[]{ decC, decBeta, decB, decPerim, decArea }) {
					@Override
					public void solve() {
						solveAAS(decAlpha, decGamma, decA, decC, decBeta, decB);
					}
				},
				new Solver(new EditWrapper[]{ decBeta, decAlpha, decB },
						new ControlWrapper[]{ decA, decGamma, decC, decPerim, decArea }) {
					@Override
					public void solve() {
						solveAAS(decBeta, decAlpha, decB, decA, decGamma, decC);
					}
				},
				new Solver(new EditWrapper[]{ decGamma, decBeta, decC },
						new ControlWrapper[]{ decB, decAlpha, decA, decPerim, decArea }) {
					@Override
					public void solve() {
						solveAAS(decGamma, decBeta, decC, decB, decAlpha, decA);
					}
				},

				// ASA
				new Solver(new EditWrapper[]{ decAlpha, decC, decBeta },
						new ControlWrapper[]{ decGamma, decA, decB, decPerim, decArea }) {
					@Override
					public void solve() {
						solveASA(decAlpha, decC, decBeta, decGamma, decA, decB);
					}
				},
				new Solver(new EditWrapper[]{ decBeta, decA, decGamma },
						new ControlWrapper[]{ decAlpha, decB, decC, decPerim, decArea }) {
					@Override
					public void solve() {
						solveASA(decBeta, decA, decGamma, decAlpha, decB, decC);
					}
				},
				new Solver(new EditWrapper[]{ decGamma, decB, decAlpha },
						new ControlWrapper[]{ decBeta, decC, decA, decPerim, decArea }) {
					@Override
					public void solve() {
						solveASA(decGamma, decB, decAlpha, decBeta, decC, decA);
					}
				},

				// angle, angle
				new Solver(new EditWrapper[]{ decAlpha, decBeta },
						new ControlWrapper[]{ decGamma }) {
					@Override
					public void solve() {
						solveAA(decAlpha, decBeta, decGamma);
					}
				},
				new Solver(new EditWrapper[]{ decBeta, decGamma },
						new ControlWrapper[]{ decAlpha }) {
					@Override
					public void solve() {
						solveAA(decBeta, decGamma, decAlpha);
					}
				},
				new Solver(new EditWrapper[]{ decGamma, decAlpha },
						new ControlWrapper[]{ decBeta }) {
					@Override
					public void solve() {
						solveAA(decGamma, decAlpha, decBeta);
					}
				}
		};

		initialize(view, inputs, outputs, solvers, R.id.btnClear);
		return view;
	}


	private void solveSSS(double side1, double side2, double side3,
			EditAngle decAngle1, EditAngle decAngle2, EditAngle decAngle3) {
		double angle1 = angleCosines(side1, side2, side3);
		double angle2 = angleSines(side2, angle1, side1);
		double angle3 = angleAA(angle1, angle2);
		Unit user = spinner.getSelectedItem();
		decAngle1.setText(fmtrAngle, user, angle1);
		decAngle2.setText(fmtrAngle, user, angle2);
		decAngle3.setText(fmtrAngle, user, angle3);
		perimAndArea(side1, side2, side3);
	}

	private void solveSAS(
			EditDec decSide1, EditAngle decAngle3, EditDec decSide2,
			EditDec decSide3, EditAngle decAngle1, EditAngle decAngle2) {
		Unit user = spinner.getSelectedItem();
		double side1 = decSide1.getDec();
		double angle3 = decAngle3.getRad(user);
		double side2 = decSide2.getDec();
		double side3 = sideCosines(side1, angle3, side2);
		double angle1 = angleSines(side1, angle3, side3);
		double angle2 = angleAA(angle1, angle3);
		decSide3.setText(fmtrDec.format(side3));
		decAngle1.setText(fmtrAngle, user, angle1);
		decAngle2.setText(fmtrAngle, user, angle2);
		perimAndArea(side1, side2, side3);
	}

	private void solveSSA(
			EditDec decSide1, EditDec decSide2, EditAngle decAngle1,
			EditAngle decAngle2, EditAngle decAngle3, EditDec decSide3) {
		Unit user = spinner.getSelectedItem();
		double side1 = decSide1.getDec();
		double side2 = decSide2.getDec();
		double angle1 = decAngle1.getRad(user);
		double angle2 = angleSines(side2, angle1, side1);
		double angle3 = angleAA(angle1, angle2);
		double side3 = sideSines(angle3, side1, angle1);
		decAngle2.setText(fmtrAngle, user, angle2);
		decAngle3.setText(fmtrAngle, user, angle3);
		decSide3.setText(fmtrDec.format(side3));
		perimAndArea(side1, side2, side3);
	}

	private void solveAAS(
			EditAngle decAngle1, EditAngle decAngle2, EditDec decSide1,
			EditDec decSide2, EditAngle decAngle3, EditDec decSide3) {
		Unit user = spinner.getSelectedItem();
		double angle1 = decAngle1.getRad(user);
		double angle2 = decAngle2.getRad(user);
		double side1 = decSide1.getDec();
		double side2 = sideSines(angle2, side1, angle1);
		double angle3 = angleAA(angle1, angle2);
		double side3 = sideSines(angle3, side1, angle1);
		decSide2.setText(fmtrDec.format(side2));
		decAngle3.setText(fmtrAngle, user, angle3);
		decSide3.setText(fmtrDec.format(side3));
		perimAndArea(side1, side2, side3);
	}

	private void solveASA(
			EditAngle decAngle1, EditDec decSide3, EditAngle decAngle2,
			EditAngle decAngle3, EditDec decSide1, EditDec decSide2) {
		Unit user = spinner.getSelectedItem();
		double angle1 = decAngle1.getRad(user);
		double side3 = decSide3.getDec();
		double angle2 = decAngle2.getRad(user);
		double angle3 = angleAA(angle1, angle2);
		double side1 = sideSines(angle1, side3, angle3);
		double side2 = sideSines(angle2, side3, angle3);
		decAngle3.setText(fmtrAngle, user, angle3);
		decSide1.setText(fmtrDec.format(side1));
		decSide2.setText(fmtrDec.format(side2));
		perimAndArea(side1, side2, side3);
	}

	private void perimAndArea(double side1, double side2, double side3) {
		double perim = perimeter(side1, side2, side3);
		double area = area(side1, side2, side3);
		decPerim.setText(fmtrDec.format(perim));
		decArea.setText(fmtrDec.format(area));
	}

	private void solveAA(EditAngle decAngle1, EditAngle decAngle2,
			EditAngle decAngle3) {
		Unit user = spinner.getSelectedItem();
		double angle1 = decAngle1.getRad(user);
		double angle2 = decAngle2.getRad(user);
		double angle3 = angleAA(angle1, angle2);
		decAngle3.setText(fmtrAngle, user, angle3);
		decA.clear();
		decB.clear();
		decC.clear();
		decPerim.clear();
		decArea.clear();
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		spinner.restore(prefs, Angle.deg);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		spinner.save(editor);
	}


	private final class AngleUnits extends ItemSelectedHandler {
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
	}
}
