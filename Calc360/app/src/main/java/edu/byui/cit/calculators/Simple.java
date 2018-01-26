package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.TextWrapper;


public final class Simple extends CalcFragment {
	private final NumberFormat fmtrDec;
	private EditDec decimal1, decimal2;
	private TextWrapper result;


	public Simple() {
		// Call the parent constructor.
		super();

		// Create a number formatter object that
		// will format numbers for the user to see.
		fmtrDec = NumberFormat.getNumberInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.simple, container, false);

		// Get a reference to each of the text fields in this calculator.
		decimal1 = new EditDec(view, R.id.decimal1, this);
		decimal2 = new EditDec(view, R.id.decimal2, this);

		// Get a reference to the text view
		// where the results will be displayed.
		result = new TextWrapper(view, R.id.result);

		// Set this calculator as the click listener for the clear button.
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());

		return view;
	}


	@Override
	protected void compute() {
		// If there is text in both text fields
		if (decimal1.notEmpty() && decimal2.notEmpty()) {
			// Get the numbers from the text fields.
			double num1 = decimal1.getDec();
			double num2 = decimal2.getDec();

			// Add the numbers together.
			double sum = num1 + num2;

			// Display the results for the user to see.
			result.setText(fmtrDec.format(sum));
		}
		else {
			result.clear();
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements ClickListener {
		/** Handles a click on the clear button. */
		@Override
		public void clicked(View button) {
			decimal1.clear();
			decimal2.clear();
			result.clear();
			decimal1.requestFocus();
		}
	}
}
