package edu.byui.cit.calc360;

import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.text.NumberFormat;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.Input;


public abstract class SolveEverything extends CalcFragment
		implements TextWatcher, OnItemSelectedListener, OnClickListener {
	public interface Solver {
		Number solve(Number[] values);
	}

	private static final int NONE = -1;

	private Input[] inputs;
	private Solver[] solvers;
	private int mostDesired, secondDesired;
	private NumberFormat[] fmtrs;
	private int toSolve;


	public void initialize(View view, Input[] inputs,
			Solver[] solvers, NumberFormat[] fmtrs, int btnClearID) {
		initialize(view, inputs, solvers, 0, 1, fmtrs, btnClearID);
	}


	public void initialize(View view, Input[] inputs,
			Solver[] solvers, int mostDesired, int secondDesired,
			NumberFormat[] fmtrs, int btnClearID) {
		this.inputs = inputs;
		this.solvers = solvers;
		this.mostDesired = mostDesired;
		this.secondDesired = secondDesired;
		this.fmtrs = fmtrs;
		new ButtonWrapper(view, btnClearID, new ClearHandler());
		this.toSolve = NONE;
	}


	@Override
	protected void compute() {
		try {
			// Todo: allow user to choose number of decimal places.

			// Determine which value should be computed.
			boolean canSolve = true;
			int empty = Input.countEmpty(inputs);
			if (empty > 1) {
				canSolve = false;
				toSolve = NONE;
			}
			else if (empty == 1) {
				empty = Input.indexOfEmpty(inputs);
				if (inputs[empty].hasFocus()) {
					// don't solve and don't change toSolve
					canSolve = false;
				}
				else {
					toSolve = empty;
				}
			}
			else {  // empty == 0
				int focus = Input.indexOfFocus(inputs);
				if (focus == toSolve) {
					toSolve = (focus == mostDesired ?
							secondDesired : mostDesired);
				}
			}

			if (canSolve) {
				// Get the user input.
				Number[] givens = new Number[inputs.length];
				for (int i = 0; i < toSolve; ++i) {
					givens[i] = inputs[i].getValue();
				}
				givens[toSolve] = Double.NaN;
				for (int i = toSolve + 1; i < givens.length; ++i) {
					givens[i] = inputs[i].getValue();
				}

				// Compute and display a value.
				Number solution = solvers[toSolve].solve(givens);

				// Display the result for the user to see.
				EditWrapper output = (EditWrapper)inputs[toSolve];
				if (solution instanceof Double ||
						solution instanceof Float) {
					output.setText(fmtrs[toSolve].format(solution.doubleValue()));
				}
				else if (solution instanceof Long ||
						solution instanceof Integer ||
						solution instanceof Short ||
						solution instanceof Byte) {
					output.setText(fmtrs[toSolve].format(solution.longValue()));
				}
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			for (Input in : inputs) {
				in.clear();
			}
			inputs[0].requestFocus();
			toSolve = NONE;
		}
	}
}
