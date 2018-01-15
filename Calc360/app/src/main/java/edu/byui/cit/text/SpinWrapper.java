package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


abstract class SpinWrapper extends Input {
	final Spinner spinner;
	final String prefsKey;
	private final ItemSelectedListener listener;

	SpinWrapper(View parent, int spinID, String prefsKey,
			ItemSelectedListener listener) {
		super(parent, spinID);
		this.spinner = parent.findViewById(spinID);
		this.prefsKey = prefsKey;
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

	@Override
	public boolean isEmpty() {
		return spinner.getSelectedItem() == null;
	}

	public abstract void save(SharedPreferences.Editor editor);

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
		Object select = spinner.getSelectedItem();
		int index = -1;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0, len = adapter.getCount(); i < len; ++i) {
			Object item = adapter.getItem(i);
			if (item == select) {
				index = i;
				break;
			}
		}
		return index;
	}

	public Spinner getSpinner() {
		return spinner;
	}

	public void setAdapter(SpinnerAdapter adapter) {
		spinner.setAdapter(adapter);
	}

	public void setSelection(int index) {
		spinner.setOnItemSelectedListener(null);
		listener.nextIsProgrammatic();
		spinner.setSelection(index);
		spinner.setOnItemSelectedListener(listener);
	}

	@Override
	public void clear() {
	}
}
