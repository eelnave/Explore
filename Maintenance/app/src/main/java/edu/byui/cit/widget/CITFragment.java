package edu.byui.cit.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import edu.byui.cit.maintenance.MainActivity;
import edu.byui.cit.maintenance.R;


public abstract class CITFragment extends Fragment {
	private static final String PAGE_ID_KEY = "pageID";

	@Override
	public void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);
		if (savedInstState != null) {
			// TODO: This currently does nothing. Do we need to keep it?
			savedInstState.getString(PAGE_ID_KEY);
		}
	}


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
			Log.e(MainActivity.TAG, "exception", ex);
			view = inflater.inflate(R.layout.mistake, container, false);
		}
		return view;
	}

	protected abstract View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState);

	protected abstract String getTitle();

	final String getPrefsPrefix() {
		return getClass().getSimpleName();
	}

	protected void restorePrefs(SharedPreferences prefs) {
	}

	protected void savePrefs(SharedPreferences.Editor editor) {
	}


	@Override
	public void onResume() {
		super.onResume();
		try {
			Activity act = getActivity();
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
			Log.e(MainActivity.TAG, "exception", ex);
		}
	}


	@Override
	public void onSaveInstanceState(Bundle savedInstState) {
		super.onSaveInstanceState(savedInstState);
		savedInstState.putString(PAGE_ID_KEY, getPrefsPrefix());
	}


	// When this fragment is stopped by the Android system, save
	// some things chosen by the user into the preferences file.
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
			Log.e(MainActivity.TAG, "exception", ex);
		}
		finally {
			super.onStop();
		}
	}
}
