package edu.byui.cit.calculators;

import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;

import static edu.byui.cit.model.Geometry.Quadratic.*;


public final class Quadratic extends SolveSeries {
	private final NumberFormat fmtrDec;
	private EditDec decA, decB, decC;
	private TextWrapper decRoot1, decRoot2;

	public Quadratic() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMinimumFractionDigits(1);
		fmtrDec.setMaximumFractionDigits(7);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.quadratic, container, false);

		decA = new EditDec(view, R.id.decA, this);
		decB = new EditDec(view, R.id.decB, this);
		decC = new EditDec(view, R.id.decC, this);
		decRoot1 = new TextWrapper(view, R.id.decRoot1);
		decRoot2 = new TextWrapper(view, R.id.decRoot2);

		EditWrapper[] inputs = { decA, decB, decC };
		ControlWrapper[] toClear = { decA, decB, decC, decRoot1, decRoot2 };

		Solver[] solvers = new Solver[] {
				new Solver(new EditWrapper[]{ decA, decB, decC },
						new ControlWrapper[]{ decRoot1, decRoot2 }) {
					@Override
					public void solve() {
						double a = decA.getDec();
						double b = decB.getDec();
						double c = decC.getDec();
						double discr = discrim(a, b, c);

						// This if block checks for the number of solutions
						// and then outputs them to the roots fields.
						if (discr > 0) {
							double rt1 = root1(a, b, discr);
							double rt2 = root2(a, b, discr);

							// Ensure that the lower root
							// is always displayed first.
							if (rt2 < rt1) {
								double swap = rt1;
								rt1 = rt2;
								rt2 = swap;
							}

							decRoot1.setText(fmtrDec.format(rt1));
							decRoot2.setText(fmtrDec.format(rt2));
						}
						else if (discr == 0) {
							double rt = root(a, b);
							decRoot1.setText(fmtrDec.format(rt));
							decRoot2.clear();
						}
						else {
							decRoot1.setText(getString(R.string.noRoots));
							decRoot2.clear();
						}
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, toClear);
		return view;
	}
}
