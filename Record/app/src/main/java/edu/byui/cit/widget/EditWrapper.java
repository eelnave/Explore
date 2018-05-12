package edu.byui.cit.widget;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.NumberFormat;

import edu.byui.cit.record.MainActivity;


/** A wrapper class that contains both an EditText and a corresponding
 * TextWatcher. Objects from this class remove the TextWatcher before
 * writing to or clearing the EditText. */
public abstract class EditWrapper extends InputWrapper implements TextWatcher {
	private final EditText edit;
	private final TextChangeListener listener;
	private boolean userInput, meaningful;

	EditWrapper(View parent, int resID,
			String prefsKey, TextChangeListener listener) {
		super(parent, resID, prefsKey);
		this.edit = parent.findViewById(resID);
		this.listener = listener;
		this.userInput = false;
		edit.setSelectAllOnFocus(true);
		edit.addTextChangedListener(this);
	}


	public abstract void restore(SharedPreferences prefs, NumberFormat fmtr);

	@Override
	public final EditText getView() {
		return edit;
	}

	@Override
	public final boolean isEnabled() {
		return edit.isEnabled();
	}

	@Override
	public final void setEnabled(boolean enabled) {
		edit.setEnabled(enabled);
	}


	@Override
	public final boolean hasFocus() {
		return edit.hasFocus();
	}

	@Override
	public final void requestFocus() {
		if (edit.hasFocus()) {
			showKeyboard(edit);
		}
		else {
			edit.requestFocus();
		}
	}

	@Override
	public final void onFocusChange(View view, boolean hasFocus) {
		if (hasFocus) {
			showKeyboard(view);
		}
	}


	@Override
	public final void beforeTextChanged(
			CharSequence s, int start, int count, int after) {
		/* When an Android device is rotated 90 degrees, the Android
		 * library automatically copies all text in text fields that
		 * have IDs. After recreating the view, the Android library
		 * automatically restores those values. Strangely, the Android
		 * library calls beforeTextChanged, onTextChanged, and
		 * afterTextChanged even for text that is being "restored"
		 * from blank to blank. To detect this problem, we check that
		 * either count or after is not zero. */
//		if ((count != 0 || after != 0) &&
//				listener != null && listener instanceof CalcFragment) {
//			((CalcFragment)listener).clearGroup(this);
//		}
	}

	@Override
	public final void onTextChanged(
			CharSequence s, int start, int before, int count) {
		meaningful = before != 0 || count != 0;
	}

	@Override
	public final void afterTextChanged(Editable edit) {
		userInput = notEmpty();
		if (meaningful && listener != null) {
			try {
				listener.textChanged(this);
			}
			catch (NumberFormatException ex) {
				// Do nothing.
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG, "exception", ex);
			}
		}
	}

	public boolean hasUserInput() {
		return userInput;
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
	void setInput(CharSequence text) {
		edit.setText(text);
	}

	/** Sets the text in this EditText as output. */
	@Override
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

	@Override
	public String toString() {
		return "input: " + hasUserInput() + "  empty: " + isEmpty() + "  text: " + getText();
	}


	public static boolean anyHaveFocus(EditWrapper... inputs) {
		boolean any = false;
		for (EditWrapper in : inputs) {
			any = in.hasFocus();
			if (any) {
				break;
			}
		}
		return any;
	}


	public static int countEmpty(EditWrapper... inputs) {
		int n = 0;
		for (EditWrapper in : inputs) {
			if (in.isEmpty()) {
				++n;
			}
		}
		return n;
	}

	public static int indexOfEmpty(EditWrapper... inputs) {
		int empty = -1;
		for (int i = 0; i < inputs.length; ++i) {
			if (inputs[i].isEmpty()) {
				empty = i;
				break;
			}
		}
		return empty;
	}

	public static boolean anyNotEmpty(EditWrapper... inputs) {
		boolean any = false;
		for (EditWrapper in : inputs) {
			any = in.notEmpty();
			if (any) {
				break;
			}
		}
		return any;
	}

	public static boolean allNotEmpty(EditWrapper... inputs) {
		boolean all = true;
		for (EditWrapper in : inputs) {
			all = in.notEmpty();
			if (!all) {
				break;
			}
		}
		return all;
	}


	public static boolean anyHaveInput(EditWrapper... inputs) {
		boolean any = false;
		for (EditWrapper in : inputs) {
			any = in.hasUserInput();
			if (any) {
				break;
			}
		}
		return any;
	}

	public static boolean allHaveInput(EditWrapper... inputs) {
		boolean all = true;
		for (EditWrapper in : inputs) {
			all = in.hasUserInput();
			if (!all) {
				break;
			}
		}
		return all;
	}
}
