package edu.byui.cit.text;

import android.content.SharedPreferences;
import android.view.View;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;


public abstract class InputWrapper extends ControlWrapper {
	// These formatters are used for parsing numbers.
	static final NumberFormat
			intFmtr = NumberFormat.getIntegerInstance(),
			decFmtr = NumberFormat.getInstance(),
			perFmtr = NumberFormat.getPercentInstance(),
			curFmtr = NumberFormat.getCurrencyInstance();
	final String prefsKey;

	InputWrapper(View parent, int resID, String prefsKey, CalcFragment calculator) {
		super(parent, resID, calculator);
		this.prefsKey = prefsKey;
	}

	public abstract void save(SharedPreferences.Editor editor);

	public abstract boolean isEmpty();

	public final boolean notEmpty() {
		return !isEmpty();
	}

	public abstract boolean hasUserInput();


	public static int countEmpty(InputWrapper... inputs) {
		int n = 0;
		for (InputWrapper i : inputs) {
			if (i.isEmpty()) {
				++n;
			}
		}
		return n;
	}

	public static int indexOfEmpty(InputWrapper... inputs) {
		int empty = -1;
		for (int i = 0; i < inputs.length; ++i) {
			if (inputs[i].isEmpty()) {
				empty = i;
				break;
			}
		}
		return empty;
	}

	public static boolean anyNotEmpty(InputWrapper... inputs) {
		boolean any = false;
		for (InputWrapper in : inputs) {
			any = in.notEmpty();
			if (any) {
				break;
			}
		}
		return any;
	}

	public static boolean allNotEmpty(InputWrapper... inputs) {
		boolean all = true;
		for (InputWrapper in : inputs) {
			all = in.notEmpty();
			if (!all) {
				break;
			}
		}
		return all;
	}
}
