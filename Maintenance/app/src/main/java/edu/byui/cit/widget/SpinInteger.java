package edu.byui.cit.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;


public final class SpinInteger extends SpinWrapper {

	public SpinInteger(Context ctx, View parent, int spinID, int arrayID,
			String prefsKey, ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, listener);

		int[] array = ctx.getResources().getIntArray(arrayID);
		ArrayList<Integer> list = new ArrayList<>(array.length);
		for (int item : array) {
			list.add(item);
		}
		ArrayAdapter<Integer> adapter = new ArrayAdapter<>(ctx,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		editor.putInt(prefsKey, getInt());
	}

	public void restore(SharedPreferences prefs, int deflt) {
		int preferred = prefs.getInt(prefsKey, deflt);
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0, len = adapter.getCount();  i < len;  ++i) {
			int item = (Integer)adapter.getItem(i);
			if (item == preferred) {
				setSelection(i);
				break;
			}
		}
	}

	public int getInt(int deflt) {
		Object selected = spinner.getSelectedItem();
		if (selected != null) {
			deflt = (Integer)selected;
		}
		return deflt;
	}

	public int getInt() {
		return (Integer)spinner.getSelectedItem();
	}
}
