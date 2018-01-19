package edu.byui.cit.calc360;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.HashMap;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.Control;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.Input;


public abstract class SolveSome extends CalcFragment
		implements TextWatcher, OnItemSelectedListener, OnClickListener {
	private Input[] inputs;
	private Input[][] groups;
	private Control[] toClear;
	private HashMap<Long, Solver> solvers;
	private OrderedIndexArray indicesOfGivens;


	public void initialize(View view, Input[] inputs,
			Solver[] solvers, int btnClearID, Control[] toClear) {
		initialize(view, inputs, null, solvers, btnClearID, toClear);
	}

	public void initialize(View view, Input[] inputs, Input[][] groups,
			Solver[] solvers, int btnClearID, Control[] toClear) {
		this.inputs = inputs;
		this.groups = groups;
		this.toClear = toClear;

		int maxInputs = -1;
		int len = inputs.length;
		OrderedIndexArray indexArray = new OrderedIndexArray(len, len);
		this.solvers = new HashMap<>((int)Math.ceil(solvers.length * 1.4));
		for (Solver solver : solvers) {
			maxInputs = Math.max(maxInputs, solver.inputs.length);
			indexArray.clear();
			for (Input input : solver.inputs) {
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
	public void afterTextChanged(Editable edit) {
		int index = indexOf(inputs, edit);
		if (edit.toString().isEmpty()) {
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
				Input input = inputs[index];
				Input[] group = findGroup(groups, input);
				if (group != null) {
					clearExcept(group, input);
				}
			}
			indicesOfGivens.add(index);
			compute();
		}
	}

	private static Input[] findGroup(Input[][] groups, Input key) {
		Input[] found = null;
		for (Input[] group : groups) {
			if (indexOf(group, key) != -1) {
				found = group;
				break;
			}
		}
		return found;
	}

	private void clearExcept(Input[] group, Input key) {
		Input input;
		int i;
		for (i = 0;  group[i] != key;  ++i) {
			input = group[i];
			input.clear();
			int index = indexOf(inputs, input);
			indicesOfGivens.remove(index);
		}
		for (++i;  i < group.length;  ++i) {
			input = group[i];
			input.clear();
			int index = indexOf(inputs, input);
			indicesOfGivens.remove(index);
		}
	}


	@Override
	protected void compute() {
		try {
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
				clearNonInputs();
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	protected boolean userInput(Input input) {
		int index = indexOf(inputs, input);
		return indicesOfGivens.contains(index);
	}


	private void clearNonInputs() {
		for (Control ctrl : toClear) {
			int index = indexOf(inputs, ctrl);
			if (! indicesOfGivens.contains(index)) {
				ctrl.clear();
			}
		}
	}

	private static int indexOf(Control[] controls, Editable key) {
		int index = -1;
		for (int i = 0;  i < controls.length;  ++i) {
			Control ctrl = controls[i];
			if (ctrl instanceof EditWrapper &&
					((EditWrapper)ctrl).getEdit().getEditableText() == key) {
				index = i;
				break;
			}
		}
		return index;
	}

	private static int indexOf(Control[] controls, Control key) {
		int index = -1;
		for (int i = 0;  i < controls.length;  ++i) {
			if (controls[i] == key) {
				index = i;
				break;
			}
		}
		return index;
	}


	protected static abstract class Solver {
		protected Input[] inputs;
		private Control[] outputs;

		protected Solver(Input[] inputs, Control[] outputs) {
			init(inputs, outputs);
		}

		void init(Input[] inputs, Control[] outputs) {
			checkInputs(inputs, outputs);
			this.inputs = inputs;
			this.outputs = outputs;
		}

		private static void checkInputs(Input[] inputs, Control[] outputs) {
			if (inputs != null && outputs != null) {
				for (int i = 0;  i < inputs.length;  ++i) {
					Input in = inputs[i];
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
				for (Control ctrl : outputs) {
					ctrl.clear();
				}
			}
		}
	}
}
