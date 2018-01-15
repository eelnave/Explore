package edu.byui.cit.text;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;


public final class RadioWrapper extends Control {
	private final RadioButton radio;

	public RadioWrapper(View parent, int resID, OnClickListener listener) {
		super(parent, resID);
		radio = parent.findViewById(resID);
		radio.setOnClickListener(listener);
	}

	public boolean isChecked() {
		return radio.isChecked();
	}

	public boolean performClick() {
		return radio.performClick();
	}

	@Override
	public boolean isEnabled() {
		return radio.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		radio.setEnabled(enabled);
	}

	@Override
	public boolean hasFocus() {
		return radio.hasFocus();
	}

	@Override
	public void requestFocus() {
		radio.requestFocus();
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
