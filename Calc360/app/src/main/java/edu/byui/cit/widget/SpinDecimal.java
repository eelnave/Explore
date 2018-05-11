package edu.byui.cit.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;


public final class SpinDecimal extends SpinWrapper {
	public SpinDecimal(Context ctx, View parent, int spinID, int arrayID) {
		this(ctx, parent, spinID, arrayID, null, null);
	}

	public SpinDecimal(Context ctx, View parent, int spinID, int arrayID,
			String prefsKey) {
		this(ctx, parent, spinID, arrayID, prefsKey, null);
	}

	public SpinDecimal(Context ctx, View parent, int spinID, int arrayID,
			String prefsKey, ItemSelectedListener listener) {
		super(parent, spinID, prefsKey, listener);

		String[] array = ctx.getResources().getStringArray(arrayID);
		ArrayList<Float> list = new ArrayList<>(array.length);
		for (String item : array) {
			Float num = Float.parseFloat(item);
			list.add(num);
		}
		ArrayAdapter<Float> adapter = new ArrayAdapter<>(ctx,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void save(SharedPreferences.Editor editor) {
		editor.putFloat(prefsKey, getDec());
	}

	public void restore(SharedPreferences prefs, int deflt) {
		float preferred = prefs.getFloat(prefsKey, deflt);
		SpinnerAdapter adapter = spinner.getAdapter();
		float delta = Math.min(preferred, deflt) * 1e-3F;
		for (int i = 0, len = adapter.getCount();  i < len;  ++i) {
			float item = (Float)adapter.getItem(i);
			if (Math.abs(item - preferred) < delta) {
				setSelection(i);
				break;
			}
		}
	}

	public float getDec(float deflt) {
		Object selected = spinner.getSelectedItem();
		if (selected != null) {
			deflt = (Float)selected;
		}
		return deflt;
	}

	public float getDec() {
		return (Float)spinner.getSelectedItem();
	}
}
