package edu.byui.cit.calc360;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;

import edu.byui.cit.text.ItemSelectedListener;


public abstract class CalcFragment extends OmniFragment implements TextWatcher,
		OnTouchListener, ItemSelectedListener, OnClickListener {

	/* progs, nextIsProgrammatic, and replacing onItemSelected with
	 * itemSelected are all necessary because the Android Spinner class
	 * does not distinguish between selection events generated by the
	 * app and those generated by the user. */
	private long progs = 0;

	@Override
	public final void nextIsProgrammatic() {
		++progs;
	}

	@Override
	public final void onItemSelected(
			AdapterView<?> parent, View view, int pos, long id) {
		if (progs > 0) {
			--progs;
		}
		else {
			itemSelected(parent, view, pos, id);
		}
	}

	public void itemSelected(
			AdapterView<?> parent, View view, int pos, long id) {
		callCompute();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return false;
	}

	@Override
	public void beforeTextChanged(
			CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(
			CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable edit) {
		callCompute();
	}

	@Override
	public void onClick(View button) {
		callCompute();
	}

	protected void callCompute() {
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


	// When this calculator is stopped by the Android system, save
	// the units chosen by the user into the preferences file.
	@Override
	public void onStop() {
		try {
			// Open the Android system preferences file for Calc360.
			SharedPreferences prefs = getActivity().getPreferences(
					Context.MODE_PRIVATE);

			// Get an editor for the preferences files
			// so that we can write values into that file.
			SharedPreferences.Editor editor = prefs.edit();

			// Call savePrefs which will be
			// overridden in the individual calculators.
			savePrefs(editor);

			// Make the changes permanent.
			editor.apply();
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
		finally {
			super.onStop();
		}
	}

	protected void savePrefs(SharedPreferences.Editor editor) {
	}
}
