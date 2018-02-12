package edu.byui.cit.calc360;

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

import org.jetbrains.annotations.NotNull;


public abstract class InfoFragment extends Fragment {
	private static final String descripIDKey = "calcID";

	Descriptor descriptor;

	void setDescripID(int descripID) {
		descriptor = Descriptors.getDescrip(descripID);
	}

//	@Override
//	public void onAttach(Context ctx) {
//		super.onAttach(ctx);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onAttach()");
//	}

	@Override
	public void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onCreate(" +
//			(savedInstState == null ? "null" : savedInstState.size()) + ")");
		if (savedInstState != null) {
			setDescripID(savedInstState.getInt(descripIDKey));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		super.onCreateView(inflater, container, savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onCreateView(" +
//			(savedInstState == null ? "null" : savedInstState.size()) + ")");
		View view;
		try {
			view = createView(inflater, container, savedInstState);
			if (savedInstState == null) {
				SharedPreferences prefs = getActivity()
						.getPreferences(Context.MODE_PRIVATE);
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

//	@Override
//	public void onActivityCreated(Bundle savedInstState) {
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onActivityCreated("
// + (savedInstState == null ? "null" : savedInstState.size()) + ")");
//		super.onActivityCreated(savedInstState);
//	}
//
//	@Override
//	public void onViewStateRestored(Bundle savedInstState) {
//		super.onViewStateRestored(savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onViewStateRestored
// (" + (savedInstState == null ? "null" : savedInstState.size()) + ")");
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onStart()");
//	}

	@Override
	public void onResume() {
		super.onResume();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onResume()");
		try {
			Activity act = getActivity();
			act.setTitle(descriptor.getTitle(getResources()));
			View focused = act.getCurrentFocus();
			if (focused instanceof EditText) {
				InputMethodManager imm = (InputMethodManager)act
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(focused, InputMethodManager.SHOW_IMPLICIT);
			}
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


//	@Override
//	public void onPause() {
//		super.onPause();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onPause()");
//	}

	@Override
	public void onSaveInstanceState(@NotNull Bundle savedInstState) {
		super.onSaveInstanceState(savedInstState);
//		Log.v(Calc360.TAG, getClass().getSimpleName() +
//				".onSaveInstanceState(" + savedInstState.size() + ")");
		savedInstState.putInt(descripIDKey, descriptor.getID());
	}

//	void logBundle(Bundle savedInstState) {
//		Log.v(Calc360.TAG, savedInstState == null ? "null" : savedInstState.toString());
//	}


	// When this calculator is stopped by the Android system, save
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

	protected void savePrefs(SharedPreferences.Editor editor) {
	}


//	@Override
//	public void onDestroyView() {
//		super.onDestroyView();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDestroyView()");
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDestroy()");
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		Log.v(Calc360.TAG, getClass().getSimpleName() + ".onDetach()");
//	}
}
