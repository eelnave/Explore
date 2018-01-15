package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.SpinnerAdapter;


public final class SpinString extends SpinWrapper {
	public SpinString(View parent, int spinID, String prefsKey,
			ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, listener);
		spinner.setOnItemSelectedListener(listener);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		editor.putString(prefsKey, getSelectedItem());
	}

	public void restore(SharedPreferences prefs, String deflt) {
		String preferred = prefs.getString(prefsKey, deflt);
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0, len = adapter.getCount();  i < len;  ++i) {
			String item = adapter.getItem(i).toString();
			if (item.equals(preferred)) {
				setSelection(i);
				break;
			}
		}
	}

	@Override
	public Number getValue() {
		return null;
	}

	@Override
	public String getItemAtPosition(int pos) {
		return spinner.getItemAtPosition(pos).toString();
	}

	@Override
	public String getSelectedItem() {
		return spinner.getSelectedItem().toString();
	}
}
