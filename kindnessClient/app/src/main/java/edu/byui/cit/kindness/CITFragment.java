package edu.byui.cit.kindness;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public abstract class CITFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		super.onCreateView(inflater, container, savedInstState);

		View view;
		try {
			view = createView(inflater, container, savedInstState);
			if (savedInstState == null) {
				SharedPreferences prefs =
						getActivity().getPreferences(Context.MODE_PRIVATE);
				restorePrefs(prefs);
			}
		}
		catch (Exception ex) {
			Log.e(KindnessActivity.TAG, "exception", ex);
			view = inflater.inflate(R.layout.mistake, container, false);
		}
		return view;
	}

	protected abstract View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState);

	protected final AppCompatActivity getCompatActivity() {
		return (AppCompatActivity)super.getActivity();
	}

	protected abstract String getTitle();

	protected void restorePrefs(SharedPreferences prefs) {
	}

	protected void savePrefs(SharedPreferences.Editor editor) {
	}


	@Override
	public void onStart() {
		super.onStart();
		try {
			AppCompatActivity act = getCompatActivity();
			act.setTitle(getTitle());

			InputMethodManager imm = (InputMethodManager)
					act.getSystemService(Context.INPUT_METHOD_SERVICE);
			View focused = act.getCurrentFocus();
			if (focused == null) {
				View view = act.findViewById(android.R.id.content);
				imm.hideSoftInputFromWindow(view.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
			else {
				if (focused instanceof EditText) {
					imm.showSoftInput(focused,
							InputMethodManager.SHOW_IMPLICIT);
				}
				else {
					imm.hideSoftInputFromWindow(focused.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		catch (Exception ex) {
			Log.e(KindnessActivity.TAG, "exception", ex);
		}
	}


	// When this fragment is stopped by the Android system, save
	// some things chosen by the user into the preferences file.
	@Override
	public void onStop() {
		try {
			AppCompatActivity act = getCompatActivity();
			act.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			getActivity().findViewById(R.id.fabAdd).setVisibility(View.VISIBLE);

			// Open the Android system preferences file for Calc360.
			SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);

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
			Log.e(KindnessActivity.TAG, "exception", ex);
		}
		finally {
			super.onStop();
		}
	}
}
