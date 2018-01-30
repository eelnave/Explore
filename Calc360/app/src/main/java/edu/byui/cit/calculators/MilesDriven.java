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


/**
 * Created by michaelvance on 1/29/18.
 */

public final class MilesDriven extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCur milesDriven; //Represents first input
	private EditDec rate; //Represents second input
	private TextWrapper curRate, curTotal; // Represents output


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
		milesDriven = new EditCur(view, R.id.milesDriven, this); //The R.id.curPrice is what connects this to the xml document
		rate = new EditDec(view, R.id.rate,
				Calc360.KEY_SALES_TAX_RATE, this);
		curRate = new TextWrapper(view, R.id.curRate);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { milesDriven, rate };
		ControlWrapper[] toClear = { milesDriven, curRate, curTotal };
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous sales tax rate entered by the user if it exits.
		rate.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the tax rate entered by the user into the preferences file.
		rate.save(editor);
	}


	@Override
	protected void compute() {
		if (milesDriven.notEmpty() && rate.notEmpty()) {
			double miles = milesDriven.getCur();
			double taxRate = rate.getDec() / 100.0;
			double taxAmt = Consumer.Ratio.amount(taxRate, miles);
			double total = Consumer.Ratio.total(taxRate, miles);
			curRate.setText(fmtrCur.format(taxAmt));
			curTotal.setText(fmtrCur.format(total));
		}
		else {
			curRate.clear();
			curTotal.clear();
		}
	}
}
