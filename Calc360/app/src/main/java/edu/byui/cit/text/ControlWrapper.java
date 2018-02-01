package edu.byui.cit.text;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;

import edu.byui.cit.calc360.CalcFragment;


public abstract class ControlWrapper implements OnFocusChangeListener {
	private final InputMethodManager imm;
	final CalcFragment calculator;

	ControlWrapper(View parent, int resID, CalcFragment calculator) {
		Activity act = findActivity(parent);
		imm = (InputMethodManager)act
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		parent.findViewById(resID).setOnFocusChangeListener(this);
		this.calculator = calculator;
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


	public static int indexOfFocus(ControlWrapper... controls) {
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