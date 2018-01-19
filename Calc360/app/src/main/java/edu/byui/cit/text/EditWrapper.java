package edu.byui.cit.text;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import edu.byui.cit.calc360.CalcFragment;


/** A wrapper class that contains both an EditText and a corresponding
 * TextWatcher. Objects from this class remove the TextWatcher before
 * writing to or clearing the EditText. */
public abstract class EditWrapper extends Input implements TextWatcher {
	private final EditText edit;
	final String prefsKey;
	private final TextWatcher watcher;
	private final CalcFragment calculator;
	private boolean userInput;

	EditWrapper(View parent, int resID, String prefsKey, TextWatcher watcher) {
		super(parent, resID);
		this.edit = parent.findViewById(resID);
		this.prefsKey = prefsKey;
		this.watcher = watcher;
		this.calculator = null;
		this.userInput = false;
		edit.setSelectAllOnFocus(true);
		edit.addTextChangedListener(watcher);
	}

	EditWrapper(View parent, int resID, String prefsKey, boolean place, CalcFragment calculator) {
		super(parent, resID, calculator);
		this.edit = parent.findViewById(resID);
		this.prefsKey = prefsKey;
		this.watcher = null;
		this.calculator = calculator;
		this.userInput = false;
		edit.setSelectAllOnFocus(true);
		edit.addTextChangedListener(this);
	}

	public EditText getEdit() {
		return edit;
	}


	@Override
	public void beforeTextChanged(
			CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(
			CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable edit) {
		userInput = true;
		calculator.callCompute(this);
	}

	// These functions should be moved to a EditString
	// class if we ever need to make one.
//	public void save(SharedPreferences.Editor editor) {
//		if (isEmpty()) {
//			editor.remove(prefsKey);
//		}
//		else {
//			editor.putString(prefsKey, getText());
//		}
//	}
//
//	public void restore(SharedPreferences prefs) {
//		if (prefs.contains(prefsKey)) {
//			String val = prefs.getString(prefsKey, null);
//			setInput(val);
//		}
//	}

	@Override
	public boolean isEnabled() {
		return edit.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		edit.setEnabled(enabled);
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
	public boolean isEmpty() {
		return edit.length() == 0;
	}

	public boolean hasUserInput() {
		return userInput;
	}

	public void setUserInput(boolean user) {
		this.userInput = user;
	}

	/** Sets the text in this EditText as if the user had entered it. In
	 * other words, sets the text in this EditText as it were user input. */
	public void setInput(CharSequence text) {
		edit.setText(text);
	}

	/** Clears the text in this EditText as if the user had cleared it. */
	public void clearInput() {
		edit.getText().clear();
	}

	public String getText() {
		return edit.getText().toString();
	}

	/** Sets the text in this EditText as output. */
	public void setText(CharSequence text) {
		edit.removeTextChangedListener(watcher);
		edit.setText(text);
		edit.addTextChangedListener(watcher);
		userInput = false;
	}

	@Override
	public void clear() {
		edit.removeTextChangedListener(watcher);
		edit.getText().clear();
		edit.addTextChangedListener(watcher);
		userInput = false;
	}
}
