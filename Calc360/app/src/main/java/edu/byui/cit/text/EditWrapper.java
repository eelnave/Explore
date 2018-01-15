package edu.byui.cit.text;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


/** A wrapper class that contains both an EditText and a corresponding
 * TextWatcher. Objects from this class remove the TextWatcher before
 * writing to or clearing the EditText. */
public abstract class EditWrapper extends Input {
	private final EditText edit;
	private final TextWatcher watcher;

	EditWrapper(View parent, int resID, TextWatcher watcher) {
		super(parent, resID);
		this.edit = parent.findViewById(resID);
		this.watcher = watcher;
		edit.setSelectAllOnFocus(true);
		edit.addTextChangedListener(watcher);
	}

	public EditText getEdit() {
		return edit;
	}

	@Override
	public boolean hasFocus() {
		return edit.hasFocus();
	}

	@Override
	public void requestFocus() {
		if (edit.hasFocus()) {
			showKeyboard(edit);
		}
		else {
			edit.requestFocus();
		}
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			showKeyboard(view);
		}
	}

	@Override
	public boolean isEnabled() {
		return edit.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		edit.setEnabled(enabled);
	}

	@Override
	public boolean isEmpty() {
		return edit.length() == 0;
	}

	public String getText() {
		return edit.getText().toString();
	}

	public void setText(CharSequence text) {
		edit.removeTextChangedListener(watcher);
		edit.setText(text);
		edit.addTextChangedListener(watcher);
	}

	@Override
	public void clear() {
		edit.removeTextChangedListener(watcher);
		edit.getText().clear();
		edit.addTextChangedListener(watcher);
	}
}
