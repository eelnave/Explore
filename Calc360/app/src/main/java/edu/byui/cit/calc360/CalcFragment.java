package edu.byui.cit.calc360;

import android.view.View;

import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.DateChangeListener;
import edu.byui.cit.widget.DateWrapper;
import edu.byui.cit.widget.ItemSelectedListener;
import edu.byui.cit.widget.SpinWrapper;
import edu.byui.cit.widget.TextChangeListener;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditWrapper;


/** The parent (or grandparent) class of all calculators. */
public abstract class CalcFragment extends CITFragment
	implements ClickListener, DateChangeListener,
		ItemSelectedListener, TextChangeListener {

	EditWrapper[] inputs;
	EditWrapper[][] groups;
	WidgetWrapper[] toClear;

	protected void initialize(View view, EditWrapper[] inputs,
			int btnClearID, WidgetWrapper[] toClear) {
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
			EditWrapper[][] groups, int btnClearID, WidgetWrapper[] toClear) {
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


	@Override
	public void clicked(WidgetWrapper source) {
		compute(source);
	}

	@Override
	public void afterChanged(DateWrapper source,
			int year, int month, int dayOfMonth) {
		compute(source);
	}

	@Override
	public void itemSelected(SpinWrapper source, int pos, long id) {
		compute(source);
	}

	@Override
	public void textChanged(EditWrapper source) {
		compute(source);
	}


	protected void compute(WidgetWrapper source) {
	}


	/** Handles a click on the clear button. */
	class ClearHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			if (toClear != null) {
				clearAll(toClear);
			}
			if (inputs != null && inputs.length > 0) {
				inputs[0].requestFocus();
			}
		}
	}

	protected final void clearAll(WidgetWrapper[] widgets) {
		for (WidgetWrapper widget : widgets) {
			widget.clear();
		}
	}

	protected final void clearOutput() {
		if (toClear != null) {
			clearOutput(toClear);
		}
	}

	protected final void clearOutput(WidgetWrapper[] widgets) {
		for (WidgetWrapper widget : widgets) {
			if (! (widget instanceof EditWrapper &&
					((EditWrapper)widget).hasUserInput())) {
				widget.clear();
			}
		}
	}


	static int indexOf(WidgetWrapper[] controls, WidgetWrapper key) {
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
