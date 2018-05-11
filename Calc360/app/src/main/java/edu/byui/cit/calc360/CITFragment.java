package edu.byui.cit.calc360;

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

import org.jetbrains.annotations.NotNull;


public abstract class CITFragment extends Fragment {
	private static final String PAGE_ID_KEY = "pageID";

	private Descriptor descriptor;


	void setDescriptor(int descripID) {
		descriptor = Descriptors.getDescriptor(descripID);
	}

	Descriptor getDescriptor() {
		return descriptor;
	}

	final String getTitle() {
		return descriptor.getTitle(getResources());
	}

	final String getExplanation() {
		return descriptor.getExplanation(getResources());
	}

	final String getPrefsPrefix() {
		return descriptor.getPrefsPrefix(getResources());
	}

//	@Override
//	public void onAttach(Context ctx) {
//		super.onAttach(ctx);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onAttach()");
//	}

	@Override
	public void onCreate(Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onCreate(" +
//			(savedInstState == null ? "null" : savedInstState.size()) + ")");
		super.onCreate(savedInstState);
		if (savedInstState != null) {
			setDescriptor(savedInstState.getInt(PAGE_ID_KEY));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onCreateView(" +
//			(savedInstState == null ? "null" : savedInstState.size()) + ")");
		super.onCreateView(inflater, container, savedInstState);
		View view;
		try {
			view = createView(inflater, container, savedInstState);

			// If this calculator is being opened, restore its user preferences.
			if (savedInstState == null) {
				SharedPreferences prefs =
						getActivity().getPreferences(Context.MODE_PRIVATE);
				restorePrefs(prefs);
			}
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
			view = inflater.inflate(R.layout.mistake, container, false);
		}
		return view;
	}

	protected abstract View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState);


	protected void restorePrefs(SharedPreferences prefs) {
	}

	protected void savePrefs(SharedPreferences.Editor editor) {
	}


//	@Override
//	public void onActivityCreated(Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName()
//				+ ".onActivityCreated("
//				+ (savedInstState == null ? "null" : savedInstState.size())
//				+ ")");
//		super.onActivityCreated(savedInstState);
//	}
//
//	@Override
//	public void onViewStateRestored(Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName()
//				+ ".onViewStateRestored("
//				+ (savedInstState == null ? "null" : savedInstState.size())
//				+ ")");
//		super.onViewStateRestored(savedInstState);
//	}
//
//	@Override
//	public void onStart() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onStart()");
//		super.onStart();
//	}

	@Override
	public void onResume() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onResume()");
		super.onResume();
		try {
			Calc360 act = (Calc360)getActivity();
			act.setTitle(descriptor.getTitle(getResources()));

			// If this calculator contains an explanation and this is the
			// first time the user has opened this calculator or the user
			// prefers to see the explanation, then show the explanation
			// to the user.
			String explain = getExplanation();
			if (explain != null) {
				SharedPreferences prefs =
						act.getPreferences(Context.MODE_PRIVATE);
				String key = getPrefsPrefix() + Calc360.KEY_SHOW_HELP;
				if (prefs.contains(key) && !prefs.getBoolean(key, false)) {
					explain = null;
				}
			}
			if (explain != null) {
				act.showExplanation(explain);
			}
			else {
				act.hideExplanation();
			}

			InputMethodManager imm = (InputMethodManager)act
					.getSystemService(Context.INPUT_METHOD_SERVICE);
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
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


//	@Override
//	public void onPause() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onPause()");
//		super.onPause();
//	}

	@Override
	public void onSaveInstanceState(@NotNull Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName()
//				+ ".onSaveInstanceState(" + savedInstState.size() + ")");
		super.onSaveInstanceState(savedInstState);
		savedInstState.putInt(PAGE_ID_KEY, descriptor.getID());
	}


	// When this fragment is stopped by the Android system, save
	// the units chosen by the user into the preferences file.
	@Override
	public void onStop() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onStop()");
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


//	@Override
//	public void onDestroyView() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDestroyView()");
//		super.onDestroyView();
//	}
//
//	@Override
//	public void onDestroy() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDestroy()");
//		super.onDestroy();
//	}
//
//	@Override
//	public void onDetach() {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDetach()");
//		super.onDetach();
//	}
}
