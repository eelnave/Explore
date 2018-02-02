package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class MilesDriven extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCur rate; //Represents first input
	private EditDec milesDriven; //Represents second input
	private TextWrapper curTotal; // Represents output


	public MilesDriven() {
		// Call the constructor in the parent class.
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.miles_driven, container, false);

		// Get a reference to each of the text fields in this calculator.
		rate = new EditCur(view, R.id.rate, this); //The R.id.curPrice is what connects this to the xml document
		milesDriven = new EditDec(view, R.id.milesDriven,
				Calc360.KEY_SALES_TAX_RATE, this);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { rate, milesDriven };
		ControlWrapper[] toClear = { rate, milesDriven, curTotal };
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous sales tax rate entered by the user if it exits.
		milesDriven.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the tax rate entered by the user into the preferences file.
		milesDriven.save(editor);
	}


	@Override
	protected void compute() {
		if (rate.notEmpty() && milesDriven.notEmpty()) {
			double reRate = rate.getCur();
			double reMiles = milesDriven.getDec();
			double rateAmt = Consumer.Ratio.amount(reRate, reMiles);
			curTotal.setText(fmtrCur.format(rateAmt));
		}
		else {
			curTotal.clear();
		}
	}
}
