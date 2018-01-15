package edu.byui.cit.text;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;


public abstract class Control implements OnFocusChangeListener {
	private final InputMethodManager imm;

	protected Control(View parent, int resID) {
		Activity act = findActivity(parent);
		imm = (InputMethodManager)act
				.getSystemService(Context.INPUT_METHOD_SERVICE);
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

	public abstract boolean isEnabled();
	public abstract void setEnabled(boolean enabled);

	public abstract boolean hasFocus();
	public boolean notFocus() {
		return !hasFocus();
	}
	public abstract void requestFocus();

	void showKeyboard(View view) {
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	void hideKeyboard(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public abstract void clear();


	public static int indexOfFocus(Control... controls) {
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
