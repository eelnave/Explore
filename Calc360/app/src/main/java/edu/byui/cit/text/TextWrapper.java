package edu.byui.cit.text;

import android.view.View;
import android.widget.TextView;


public final class TextWrapper extends Control {
	private final TextView view;

	public TextWrapper(View parent, int resID) {
		super(parent, resID);
		this.view = parent.findViewById(resID);
	}

	public TextView getView() {
		return view;
	}

	@Override
	public boolean isEnabled() {
		return view.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		view.setEnabled(enabled);
	}

	@Override
	public boolean hasFocus() {
		return view.hasFocus();
	}

	@Override
	public void requestFocus() {
		view.requestFocus();
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			hideKeyboard(view);
		}
	}

	public void setText(CharSequence str) {
		view.setText(str);
	}

	@Override
	public void clear() {
		view.setText(null);
	}
}
