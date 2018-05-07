package edu.byui.cit.calc360;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditWrapper;


/** The parent (or grandparent) class of all calculators. */
public abstract class CalcFragment extends InfoFragment {
	EditWrapper[] inputs;
	EditWrapper[][] groups;
	ControlWrapper[] toClear;

	@Override
	void setDescrip(String descripID) {
		if (! getClass().getSimpleName().equals(descripID)) {
			throw new IllegalArgumentException(
					"mismatched descriptor ID " + descripID);
		}
		super.setDescrip(descripID);
	}

	protected void initialize(View view, EditWrapper[] inputs,
			int btnClearID, ControlWrapper[] toClear) {
		this.initialize(view, inputs, null, btnClearID, toClear);
	}

	/**
	 * Initializes the internal parts of this cacluator that keep track of user
	 * input.
	 * @param view       The view for this calculator that was inflated from an
	 *                   xml file.
	 * @param inputs     An array of all the EditTexts in this calculator.
	 * @param groups     An array of arrays where each array is a group of
	 *                   mutually exclusive EditTexts.
	 * @param btnClearID The ID from R.id for the clear button of this
	 *                   calculator.
	 * @param toClear    An array of all EditTexts and TextViews that should be
	 *                   cleared when the user clicks the clear button on this
	 *                   calculator.
	 */
	protected void initialize(View view, EditWrapper[] inputs,
			EditWrapper[][] groups, int btnClearID, ControlWrapper[] toClear) {
		this.inputs = inputs;
		this.groups = groups;
		this.toClear = toClear;

		// Add a button click listener to the Clear button.
		new ButtonWrapper(view, btnClearID, new ClearHandler());
	}


	public void clearGroup(EditWrapper input) {
		if (groups != null) {
			// Find the group of the EditText that was changed.
			EditWrapper[] group = findGroup(groups, input);
			if (group != null) {
				int i;
				for (i = 0;  group[i] != input;  ++i) {
					group[i].clear();
				}
				while (++i < group.length) {
					group[i].clear();
				}
			}
		}
	}


	/**
	 * Contains try and catch so that individual calculators are not
	 * required to include try and catch. Called from Controls that do
	 * not extend EditWrapper.
	 */
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


	/**
	 * Contains try and catch so that individual calculators are not
	 * required to include try and catch. Called from Controls that
	 * extend EditWrapper.
	 */
	public void callCompute(EditWrapper input) {
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

	protected void compute() {
	}


	/** Handles a click on the clear button. */
	class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			if (toClear != null) {
				clearAll(toClear);
			}
			if (inputs != null && inputs.length > 0) {
				inputs[0].requestFocus();
			}
		}
	}

	protected final void clearAll(ControlWrapper[] controls) {
		for (ControlWrapper ctrl : controls) {
			ctrl.clear();
		}
	}

	protected final void clearOutput() {
		if (toClear != null) {
			clearOutput(toClear);
		}
	}

	protected final void clearOutput(ControlWrapper[] controls) {
		for (ControlWrapper ctrl : controls) {
			if (! (ctrl instanceof EditWrapper &&
					((EditWrapper)ctrl).hasUserInput())) {
				ctrl.clear();
			}
		}
	}


	static int indexOf(ControlWrapper[] controls, ControlWrapper key) {
		int index = -1;
		for (int i = 0; i < controls.length; ++i) {
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
