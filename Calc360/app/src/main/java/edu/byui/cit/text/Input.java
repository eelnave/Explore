package edu.byui.cit.text;

import android.view.View;

import java.text.NumberFormat;


public abstract class Input extends Control {
	// These formatters are used for parsing numbers.
	static final NumberFormat
			intFmtr = NumberFormat.getIntegerInstance(),
			decFmtr = NumberFormat.getInstance(),
			perFmtr = NumberFormat.getPercentInstance(),
			curFmtr = NumberFormat.getCurrencyInstance();

	protected Input(View parent, int resID) {
		super(parent, resID);
	}

	public abstract boolean isEmpty();

	public boolean notEmpty() {
		return !isEmpty();
	}

	public abstract Number getValue();


	public static int countEmpty(Input... inputs) {
		int n = 0;
		for (Input i : inputs) {
			if (i.isEmpty()) {
				++n;
			}
		}
		return n;
	}

	public static int indexOfEmpty(Input... inputs) {
		int empty = -1;
		for (int i = 0; i < inputs.length; ++i) {
			if (inputs[i].isEmpty()) {
				empty = i;
				break;
			}
		}
		return empty;
	}

	public static boolean anyNotEmpty(Input... inputs) {
		boolean any = false;
		for (Input in : inputs) {
			any = in.notEmpty();
			if (any) {
				break;
			}
		}
		return any;
	}

	public static boolean allNotEmpty(Input... inputs) {
		boolean all = true;
		for (Input in : inputs) {
			all = in.notEmpty();
			if (!all) {
				break;
			}
		}
		return all;
	}
}
