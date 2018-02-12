package edu.byui.cit.calc360;

import android.view.View;

import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditWrapper;


public abstract class SolveEquation extends SolveSeries {
	@Override
	public void initialize(View view, EditWrapper[] all,
			SolveSeries.Solver[] solvers,
			int btnClearID, ControlWrapper[] toClear) {

		// Create the list of inputs for each solver.
		int inLen = all.length - 1;
		for (int i = 0; i < all.length; ++i) {
			SolveSeries.Solver solver = solvers[i];
			EditWrapper[] inputs = new EditWrapper[inLen];
			System.arraycopy(all, 0, inputs, 0, i);
			EditWrapper[] outputs = { all[i] };
			System.arraycopy(all, i + 1, inputs, i, inputs.length - i);
			solver.init(inputs, outputs);
		}

		super.initialize(view, all, null, solvers, btnClearID, toClear);
	}

	@Override
	public void initialize(View view, EditWrapper[] inputs,
			EditWrapper[][] groups, SolveSeries.Solver[] solvers,
			int btnClearID, ControlWrapper[] toClear) {
		throw new UnsupportedOperationException();
	}

	// Todo: allow user to choose number of decimal places.


	public static abstract class Solver extends SolveSeries.Solver {
		protected Solver() {
			super(null, null);
		}
	}
}
