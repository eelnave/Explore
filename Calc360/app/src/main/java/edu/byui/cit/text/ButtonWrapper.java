package edu.byui.cit.text;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public final class ButtonWrapper extends Control {
	private final Button button;

	public ButtonWrapper(View parent, int resID, OnClickListener listener) {
		super(parent, resID);
		button = parent.findViewById(resID);
		button.setOnClickListener(listener);
	}

	@Override
	public boolean isEnabled() {
		return button.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}

	@Override
	public boolean hasFocus() {
		return button.hasFocus();
	}

	@Override
	public void requestFocus() {
		button.requestFocus();
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			hideKeyboard(view);
		}
	}

	@Override
	public void clear() {
	}
}
