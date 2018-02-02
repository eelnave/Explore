package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class Modulo extends CalcFragment {
	// Create a number formatter object that
	// will format numbers for the user to see.
	private final NumberFormat fmtrDec = NumberFormat.getInstance();

	private EditDecimal decX;
	private EditDecimal decY;
	private TextWrapper decRemain;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.modulo, container, false);

		// Get a reference to each of the text fields in this calculator.
		decX = new EditDecimal(view, R.id.decX, this);
		decY = new EditDecimal(view, R.id.decY, this);

		// Get a reference to the text view
		// where the results will be displayed.
		decRemain = new TextWrapper(view, R.id.decRemain);

		EditWrapper[] inputs = { decX, decY };
		initialize(view, inputs, R.id.btnClear, inputs);
		return view;
	}


	@Override
	protected void compute() {
		if (decX.notEmpty() && decY.notEmpty()) {
			// Get the numbers from the text fields.
			double num1 = decX.getDec();
			double num2 = decY.getDec();

			double mod = num1 % num2;

			// Display the results for the user to see.
			decRemain.setText(fmtrDec.format(mod));
		}
		else {
			decRemain.clear();
		}
	}
}
