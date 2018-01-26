package edu.byui.cit.text;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import edu.byui.cit.calc360.Calc360;


public abstract class ItemSelectedHandler extends Handler
		implements OnItemSelectedListener, ItemSelectedListener {
	@Override
	public final void onItemSelected(
			AdapterView<?> parent, View view, int pos, long id) {
		if (!isProgrammatic()) {
			try {
				itemSelected(parent, view, pos, id);
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}

	@Override
	public final void onNothingSelected(AdapterView<?> parent) {
	}
}
