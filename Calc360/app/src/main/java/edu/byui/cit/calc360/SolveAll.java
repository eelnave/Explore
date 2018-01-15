package edu.byui.cit.calc360;

import android.view.View;

import edu.byui.cit.text.Input;


public abstract class SolveAll extends SolveSome {
	public void initialize(
			View view, int btnClearID, Input[] all, Solver[] solvers) {

		// Create the list of inputs for each solver.
		int inLen = all.length - 1;
		for (int i = 0; i < all.length; ++i) {
			Solver solver = solvers[i];
			Input[] inputs = new Input[inLen];
			System.arraycopy(all, 0, inputs, 0, i);
			System.arraycopy(all, i + 1, inputs, i, inputs.length - i);
			solver.init(inputs);
		}

		super.initialize(view, btnClearID, all, solvers);
	}


	public static abstract class Solver extends SolveSome.Solver {
		protected Solver() {
			super(null);
		}
	}
}
