package edu.byui.cit.text;

import android.text.Editable;
import android.text.TextWatcher;


public abstract class TextChangedHandler implements TextWatcher {
	@Override
	public void beforeTextChanged(
			CharSequence charSequence, int i, int i1, int i2) {
	}

	@Override
	public void onTextChanged(
			CharSequence charSequence, int i, int i1, int i2) {
	}

	@Override
	public void afterTextChanged(Editable edit) {
	}
}
