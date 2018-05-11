package edu.byui.cit.calculators;

import android.view.View;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;

import static edu.byui.cit.model.Mathematics.Quadratic.*;


public final class Quadratic extends CalcFragment {
	private final NumberFormat fmtrDec;
	private EditDecimal decA, decB, decC;
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

		decA = new EditDecimal(view, R.id.decA, this);
		decB = new EditDecimal(view, R.id.decB, this);
		decC = new EditDecimal(view, R.id.decC, this);
		decRoot1 = new TextWrapper(view, R.id.decRoot1);
		decRoot2 = new TextWrapper(view, R.id.decRoot2);

		EditWrapper[] inputs = { decA, decB, decC };
		WidgetWrapper[] toClear = { decA, decB, decC, decRoot1, decRoot2 };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		if (EditWrapper.allNotEmpty(decA, decB, decC)) {
			String s1, s2 = null;
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

				s1 = fmtrDec.format(rt1);
				s2 = fmtrDec.format(rt2);
			}
			else if (discr == 0) {
				double rt = onlyRoot(a, b);
				s1 = fmtrDec.format(rt);
			}
			else {
				double[] rt = imaginary(a, b, discr);
				s1 = getString(R.string.noRoots);
				String pm = getString(R.string.neg);
				String i = getString(R.string.imaginary);
				s2 = fmtrDec.format(rt[0]) + ' ' + pm + ' ' +
						fmtrDec.format(rt[1]) + ' ' + i;
			}

			decRoot1.setText(s1);
			decRoot2.setText(s2);
		}
		else {
			decRoot1.clear();
			decRoot2.clear();
		}
	}
}
