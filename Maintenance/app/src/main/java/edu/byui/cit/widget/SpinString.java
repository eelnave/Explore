package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.view.View;


public final class SpinString extends SpinWrapper {
	public SpinString(View parent, int spinID,
			String prefsKey, ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, listener);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		editor.putString(prefsKey, getSelectedItem());
	}

	public void restore(SharedPreferences prefs, int defltPos) {
		String deflt = spinner.getItemAtPosition(defltPos).toString();
		restore(prefs, deflt);
	}

	public void restore(SharedPreferences prefs, String deflt) {
		String preferred = prefs.getString(prefsKey, deflt);
		int pos = positionOf(preferred);
		if (pos != -1) {
			setSelection(pos);
		}
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
