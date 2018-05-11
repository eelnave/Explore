package edu.byui.cit.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;


/** The CIT Widgets automatically handle the following
 * 1. Show and hide the virtual keyboard when focus changes.
 * 2. Save and restore user preferences.
 * 3. Differentiate between user input and values that are restored
 * 	from the preferences file and when the device is rotated.
 */
public abstract class WidgetWrapper implements OnFocusChangeListener {
	private final InputMethodManager imm;

	WidgetWrapper(View parent, int resID) {
		Activity act = findActivity(parent);
		imm = (InputMethodManager)
				act.getSystemService(Context.INPUT_METHOD_SERVICE);
		parent.findViewById(resID).setOnFocusChangeListener(this);
	}

	private Activity findActivity(View view) {
		Context context = view.getContext();
		while (context instanceof ContextWrapper) {
			if (context instanceof Activity) {
				return (Activity)context;
			}
			context = ((ContextWrapper)context).getBaseContext();
		}
		return null;
	}

	public abstract View getView();

	public abstract boolean isEnabled();
	public abstract void setEnabled(boolean enabled);

	public abstract boolean hasFocus();
	public boolean notFocus() {
		return !hasFocus();
	}
	public abstract void requestFocus();

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			hideKeyboard(view);
		}
	}

	void showKeyboard(View view) {
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	void hideKeyboard(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void clear() {
	}

	public void setText(CharSequence str) {
	}


	public static int indexOfFocus(WidgetWrapper... controls) {
		int focus = -1;
		for (int i = 0; i < controls.length; ++i) {
			if (controls[i].hasFocus()) {
				focus = i;
				break;
			}
		}
		return focus;
	}
}
