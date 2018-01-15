package edu.byui.cit.calc360;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.HashMap;

import edu.byui.cit.model.IndexArray;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.Control;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.Input;


public abstract class SolveSome extends CalcFragment
		implements TextWatcher, OnItemSelectedListener, OnClickListener {
	private Control[] toClear;
	private HashMap<Long, Solver> solvers;
	private IndexArray indicesOfGivens;


	public void initialize(View view, int btnClearID,
			Control[] toClear, Solver[] solvers) {
		new ButtonWrapper(view, btnClearID, new ClearHandler());

		this.toClear = toClear;

		int maxInputs = -1;
		this.solvers = new HashMap<>((int)Math.ceil(solvers.length * 1.4));
		for (Solver solver : solvers) {
			maxInputs = Math.max(maxInputs, solver.inputs.length);
			long bits = 0;
			for (Input input : solver.inputs) {
				int index = indexOf(toClear, input);
				bits |= IndexArray.bitFromIndex(index);
			}
			this.solvers.put(bits, solver);
		}

		indicesOfGivens = new IndexArray(maxInputs);
	}


	public static int indexOf(Control[] controls, Control key) {
		int index = -1;
		for (int i = 0;  i < controls.length;  ++i) {
			if (controls[i] == key) {
				index = i;
				break;
			}
		}
		return index;
	}

	public static int indexOf(Control[] controls, Editable key) {
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


	@Override
	public void afterTextChanged(Editable edit) {
		int index = indexOf(toClear, edit);
		if (edit.toString().isEmpty()) {
			if (indicesOfGivens.contains(index)) {
				indicesOfGivens.remove(index);
				compute();
			}
		}
		else {
			indicesOfGivens.add(index);
			compute();
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
				clearOutputs();
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	private void clearOutputs() {
		for (int i = 0;  i < toClear.length;  ++i) {
			if (! indicesOfGivens.contains(i)) {
				toClear[i].clear();
			}
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			for (Control c : toClear) {
				c.clear();
			}
			indicesOfGivens.clear();
			toClear[0].requestFocus();
		}
	}


	protected static abstract class Solver {
		protected Input[] inputs;

		protected Solver(Input[] inputs) {
			this.inputs = inputs;
		}

		void init(Input[] inputs) {
			this.inputs = inputs;
		}

		public abstract void solve();
	}
}
