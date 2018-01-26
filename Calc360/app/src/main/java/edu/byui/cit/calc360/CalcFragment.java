package edu.byui.cit.calc360;

import android.util.Log;
import android.view.View;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditWrapper;


public abstract class CalcFragment extends OmniFragment {
	EditWrapper[] inputs;
	EditWrapper[][] groups;
	ControlWrapper[] toClear;


	protected void initialize(View view, EditWrapper[] inputs,
			ControlWrapper[] toClear, int btnClearID) {
		this.initialize(view, inputs, null, toClear, btnClearID);
	}

	protected void initialize(View view, EditWrapper[] inputs,
			EditWrapper[][] groups, ControlWrapper[] toClear, int btnClearID) {
		this.inputs = inputs;
		this.groups = groups;
		this.toClear = toClear;

		// Add a button click listener to the Clear button.
		new ButtonWrapper(view, btnClearID, new ClearHandler());
	}


	/** Contains try and catch so that individual calculators are not
	 * required to include try and catch. Called from Controls that do
	 * not extend EditWrapper. */
	public void callCompute() {
		try {
			compute();
		}
		catch (NumberFormatException ex) {
			// Do nothing.
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	/** Contains try and catch so that individual calculators are not
	 * required to include try and catch. Called from Controls that
	 * extend EditWrapper. */
	public void callCompute(EditWrapper input) {
		try {
//			if (input.isEmpty()) {
//				clearUnusedInputs();
//				clearOutputs();
//			}
//			else {
//				if (groups != null) {
//					// Find the group of the EditText that was changed.
//					EditWrapper[] group = findGroup(groups, input);
//					if (group != null) {
//						clearExcept(group, input);
//					}
//				}
//				compute();
//			}
			if (groups != null) {
				// Find the group of the EditText that was changed.
				EditWrapper[] group = findGroup(groups, input);
				if (group != null) {
					clearExcept(group, input);
				}
			}
			compute();
		}
		catch (NumberFormatException ex) {
			// Do nothing.
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}

	protected void compute() {
	}


	final void clearExcept(EditWrapper[] group, EditWrapper key) {
		int i;
		for (i = 0;  group[i] != key;  ++i) {
			clearExceptHelper(group[i]);
		}
		for (++i;  i < group.length;  ++i) {
			clearExceptHelper(group[i]);
		}
	}

	void clearExceptHelper(EditWrapper input) {
		input.clear();
	}


	/** Handles a click on the clear button. */
	class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			for (ControlWrapper ctrl : toClear) {
				ctrl.clear();
			}
			inputs[0].requestFocus();
		}
	}

//	protected final void clearInputs() {
//		if (inputs != null) {
//			for (ControlWrapper ctrl : inputs) {
//				ctrl.clear();
//			}
//		}
//	}

	protected final void clearUnusedInputs() {
		if (inputs != null) {
			for (EditWrapper in : inputs) {
				if (!in.hasUserInput()) {
					in.clear();
				}
			}
		}
	}

//	protected final void clearOutputs() {
//		if (toClear != null) {
//			for (ControlWrapper ctrl : toClear) {
//				ctrl.clear();
//			}
//		}
//	}


	static int indexOf(ControlWrapper[] controls, ControlWrapper key) {
		int index = -1;
		for (int i = 0;  i < controls.length;  ++i) {
			if (controls[i] == key) {
				index = i;
				break;
			}
		}
		return index;
	}

	static EditWrapper[] findGroup(EditWrapper[][] groups, EditWrapper key) {
		EditWrapper[] found = null;
		for (EditWrapper[] group : groups) {
			if (indexOf(group, key) != -1) {
				found = group;
				break;
			}
		}
		return found;
	}
}
