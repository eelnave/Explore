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
			Solver[] solvers, int btnClearID, ControlWrapper[] toClear) {
		this.initialize(view, inputs, null, solvers, btnClearID, toClear);
	}

	protected void initialize(View view, EditWrapper[] inputs,
			EditWrapper[][] groups,
			Solver[] solvers, int btnClearID, ControlWrapper[] toClear) {
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
	public void clearGroup(EditWrapper input) {
		if (groups != null) {
			// Find the group of the EditText that was changed.
			EditWrapper[] group = findGroup(groups, input);
			if (group != null) {
				int i;
				for (i = 0;  group[i] != input;  ++i) {
					clearGroupHelper(group[i]);
				}
				while (++i < group.length) {
					clearGroupHelper(group[i]);
				}
			}
		}
	}

	private void clearGroupHelper(EditWrapper other) {
		int index = indexOf(inputs, other);
		indicesOfGivens.remove(index);
		other.clear();
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
		long bitset = indicesOfGivens.bitset();
		Solver solver = solvers.get(bitset);
//		if (solver == null) {
			// There is no solver that is an exact match to the
			// inputs given by the user. Search for a partial match.
//			for (Long key : solvers.keySet()) {
//				long skey = key;
//				if ((bits & skey) == skey) {
//					solver = solvers.get(key);
//					break;
//				}
//			}
//		}

		if (solver != null) {
			solver.solve();
		}
		else {
			clearOutput();
		}
	}

//	private long makeBitset() {
//		long bitset = 0L;
//		long bit = 1L;
//		for (EditWrapper in : inputs) {
//			if (in.hasUserInput()) {
//				bitset |= bit;
//			}
//			bit <<= 1;
//		}
//		return bitset;
//	}
//
//	private int countInputs() {
//		int n = 0;
//		for (EditWrapper in : inputs) {
//			if (in.hasUserInput()) {
//				++n;
//			}
//		}
//		return n;
//	}
//
//	private EditWrapper getOldestInput() {
//		int min = Integer.MAX_VALUE;
//		EditWrapper oldest = null;
//		for (EditWrapper in : inputs) {
//			if (in.hasUserInput() && in.getInputOrder() < min) {
//				min = in.getInputOrder();
//				oldest = in;
//			}
//		}
//		return oldest;
//	}


//	@Override
//	void clearHelper(EditWrapper input) {
//		input.clear();
//		int index = indexOf(inputs, input);
//		indicesOfGivens.remove(index);
//	}

	private final class ClearHandler extends CalcFragment.ClearHandler {
		@Override
		public void clicked(View button) {
			super.clicked(button);
			indicesOfGivens.clear();
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
