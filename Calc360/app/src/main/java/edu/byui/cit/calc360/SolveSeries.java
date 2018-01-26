package edu.byui.cit.calc360;

import android.util.Log;
import android.view.View;

import java.util.HashMap;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditWrapper;


public abstract class SolveSeries extends CalcFragment {
	private HashMap<Long, Solver> solvers;
	OrderedIndexArray indicesOfGivens;


	protected void initialize(View view, EditWrapper[] inputs,
			Solver[] solvers, int btnClearID) {
		this.initialize(view, inputs, null, inputs, solvers, btnClearID);
	}

	protected void initialize(View view, EditWrapper[] inputs,
			ControlWrapper[] toClear, Solver[] solvers, int btnClearID) {
		this.initialize(view, inputs, null, toClear, solvers, btnClearID);
	}

	protected void initialize(View view, EditWrapper[] inputs,
			EditWrapper[][] groups,
			ControlWrapper[] toClear, Solver[] solvers, int btnClearID) {
		super.initialize(view, inputs, groups, toClear, btnClearID);
		int maxInputs = -1;
		int len = inputs.length;
		OrderedIndexArray indexArray = new OrderedIndexArray(len, len);
		this.solvers = new HashMap<>((int)Math.ceil(solvers.length * 1.4));
		for (Solver solver : solvers) {
			maxInputs = Math.max(maxInputs, solver.inputs.length);
			indexArray.clear();
			for (EditWrapper input : solver.inputs) {
				int index = indexOf(inputs, input);
				indexArray.add(index);
			}
			Long bitset = indexArray.bitset();
			if (this.solvers.containsKey(bitset)) {
				throw new IllegalArgumentException("duplicate solver inputs");
			}
			this.solvers.put(bitset, solver);
		}

		indicesOfGivens = new OrderedIndexArray(maxInputs, len);
		new ButtonWrapper(view, btnClearID, new ClearHandler());
	}


	@Override
	public void callCompute(EditWrapper input) {
		try {
			int index = indexOf(inputs, input);
			if (input.isEmpty()) {
				if (indicesOfGivens.contains(index)) {
					long bits = indicesOfGivens.bitset();
					Solver solver = solvers.get(bits);
					if (solver != null) {
						solver.clearOutputs();
					}
					indicesOfGivens.remove(index);
					compute();
				}
			}
			else {
				if (groups != null) {
					// Find the group of the EditText that was changed.
					EditWrapper[] group = findGroup(groups, input);
					if (group != null) {
						clearExcept(group, input);
					}
				}
				indicesOfGivens.add(index);
				compute();
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing.
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	@Override
	protected void compute() {
		// Todo: allow user to choose number of decimal places.

		long bits = indicesOfGivens.bitset();
		Solver solver = solvers.get(bits);
		if (solver == null) {
			// There is no solver that is an exact match to the
			// inputs given by the user. Search for a partial match.
			for (Long key : solvers.keySet()) {
				long skey = key;
				if ((bits & skey) == skey) {
					solver = solvers.get(key);
					break;
				}
			}
		}

		if (solver != null) {
			solver.solve();
		}
		else {
			clearUnusedInputs();
		}
	}

	@Override
	void clearExceptHelper(EditWrapper input) {
		input.clear();
		int index = indexOf(inputs, input);
		indicesOfGivens.remove(index);
	}

	private final class ClearHandler extends CalcFragment.ClearHandler {
		@Override
		public void clicked(View button) {
			indicesOfGivens.clear();
			super.clicked(button);
		}
	}


	protected static abstract class Solver {
		protected EditWrapper[] inputs;
		private ControlWrapper[] outputs;

		protected Solver(EditWrapper[] inputs, ControlWrapper[] outputs) {
			init(inputs, outputs);
		}

		void init(EditWrapper[] inputs, ControlWrapper[] outputs) {
			checkInputs(inputs, outputs);
			this.inputs = inputs;
			this.outputs = outputs;
		}

		private static void checkInputs(EditWrapper[] inputs,
				ControlWrapper[] outputs) {
			if (inputs != null && outputs != null) {
				for (int i = 0; i < inputs.length; ++i) {
					EditWrapper in = inputs[i];
					if (indexOf(outputs, in) != -1) {
						throw new IllegalArgumentException(
								"input " + i + " overlaps the outputs");
					}
				}
			}
		}

		public abstract void solve();

		private void clearOutputs() {
			if (outputs != null) {
				for (ControlWrapper ctrl : outputs) {
					ctrl.clear();
				}
			}
		}
	}
}
