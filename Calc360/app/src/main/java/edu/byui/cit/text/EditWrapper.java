package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;


/** A wrapper class that contains both an EditText and a corresponding
 * TextWatcher. Objects from this class remove the TextWatcher before
 * writing to or clearing the EditText. */
public abstract class EditWrapper extends InputWrapper implements TextWatcher {
	private final EditText edit;
	private final TextChangeListener listener;
	private boolean userInput;

	EditWrapper(View parent, int resID,
			String prefsKey, CalcFragment calculator) {
		this(parent, resID, prefsKey, calculator, null);
	}

	EditWrapper(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		this(parent, resID, prefsKey, null, listener);
	}

	private EditWrapper(View parent, int resID, String prefsKey,
			CalcFragment calculator, TextChangeListener listener) {
		super(parent, resID, prefsKey, calculator);
		this.edit = parent.findViewById(resID);
		this.listener = listener;
		this.userInput = false;
		edit.setSelectAllOnFocus(true);
		edit.addTextChangedListener(this);
	}

	public abstract void restore(SharedPreferences prefs, NumberFormat fmtr);

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
	public final void beforeTextChanged(
			CharSequence s, int start, int count, int after) {
		if (calculator != null) {
			calculator.clearGroup(this);
		}
	}

	@Override
	public final void onTextChanged(
			CharSequence s, int start, int before, int count) {
	}

	@Override
	public final void afterTextChanged(Editable edit) {
		userInput = notEmpty();
		if (calculator != null) {
			calculator.callCompute(this);
		}
		else {
			listener.afterChanged(edit);
		}
	}

	@Override
	public boolean hasUserInput() {
		return userInput;
	}

	public EditText getEdit() {
		return edit;
	}

	@Override
	public boolean isEmpty() {
		return edit.length() == 0;
	}

	public String getText() {
		return edit.getText().toString();
	}

	/** Sets the text in this EditText as if the user had entered it. In
	 * other words, sets the text in this EditText as it were user input. */
	public void setInput(CharSequence text) {
		edit.setText(text);
	}

	/** Sets the text in this EditText as output. */
	public void setText(CharSequence text) {
		edit.removeTextChangedListener(this);
		edit.setText(text);
		userInput = false;
		edit.addTextChangedListener(this);
	}

	/** Clears the text in this EditText as if the user had cleared it. */
//	public void clearInput() {
//		if (hasUserInput()) {
//			edit.getText().clear();
//		}
//		else {
//			clear();
//		}
//	}

	@Override
	public void clear() {
		if (notEmpty()) {
			edit.removeTextChangedListener(this);
			edit.getText().clear();
			userInput = false;
			edit.addTextChangedListener(this);
		}
	}
}
