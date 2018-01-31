package edu.byui.cit.text;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import edu.byui.cit.calc360.CalcFragment;


abstract class SpinWrapper extends InputWrapper
		implements OnItemSelectedListener {
	private final Handler handler;
	final Spinner spinner;
	private final ItemSelectedListener listener;

	SpinWrapper(View parent, int spinID,
			String prefsKey, CalcFragment calculator) {
		this(parent, spinID, prefsKey, calculator, null);
	}

	SpinWrapper(View parent, int spinID,
			String prefsKey, ItemSelectedListener listener) {
		this(parent, spinID, prefsKey, null, listener);
	}


	private SpinWrapper(View parent, int spinID, String prefsKey,
			CalcFragment calculator, ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, calculator);
		this.handler = new Handler();
		this.spinner = parent.findViewById(spinID);
		this.listener = listener;
	}

	@Override
	public boolean isEnabled() {
		return spinner.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		spinner.setEnabled(enabled);
	}

	@Override
	public boolean hasFocus() {
		return spinner.hasFocus();
	}

	@Override
	public void requestFocus() {
		spinner.requestFocus();
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			hideKeyboard(view);
		}
	}

	private void nextIsProgrammatic() {
		handler.nextIsProgrammatic();
	}

	private boolean isProgrammatic() {
		return handler.isProgrammatic();
	}


	@Override
	public final void onItemSelected(
			AdapterView<?> parent, View view, int pos, long id) {
		if (! isProgrammatic()) {
			if (calculator != null) {
				calculator.callCompute();
			}
			else {
				listener.itemSelected(parent, view, pos, id);
			}
		}
	}

	@Override
	public final void onNothingSelected(AdapterView<?> adapterView) {
	}


	public Spinner getSpinner() {
		return spinner;
	}

	@Override
	public boolean isEmpty() {
		return spinner.getSelectedItem() == null;
	}

	@Override
	public boolean hasUserInput() {
		return spinner.getSelectedItem() == null;
	}

	public int getCount() {
		return spinner.getCount();
	}

	public Object getItemAtPosition(int pos) {
		return spinner.getItemAtPosition(pos);
	}

	public Object getSelectedItem() {
		return spinner.getSelectedItem();
	}

	/**
	 * Returns the position of the selected item. It is almost
	 * unbelievable to me that Google did not write this method
	 * in the Spinner class, but they didn't.
	 */
	public int getSelectedItemPosition() {
		return positionOf(spinner.getSelectedItem());
	}

	public int positionOf(Object key) {
		int pos = -1;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0, len = adapter.getCount();  i < len;  ++i) {
			Object item = adapter.getItem(i);
			if (item.equals(key)) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	public void setAdapter(SpinnerAdapter adapter) {
		spinner.setAdapter(adapter);
	}

	public void setSelection(int index) {
		nextIsProgrammatic();
		spinner.setSelection(index);
	}

	@Override
	public void clear() {
	}
}
