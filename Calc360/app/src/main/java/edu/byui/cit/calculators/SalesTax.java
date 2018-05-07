package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class SalesTax extends CalcFragment {
	public static final String KEY_SALES_TAX_RATE = "SalesTax.rate";

	// NumberFormat is a Java class that is part of the standard Java library.
	// A NumberFormat object will convert a number (double, float, long, int,
	// etc) into a String, and it will convert it appropriately for the locale
	// where the app is running. For example, on a device running in the U.S.
	// NumberFormat will convert the double 4017.36 into "4,017.36", but on a
	// device running in Brazil, it will convert that same number to "4.017,36"
	// because Brazilians use period (.) as the thousands separator and
	// comma (,) as the whole/fraction separator.
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCurrency curPrice;
	private EditDecimal decTaxRate;

	// Each of these variables is a reference to one of the
	// TextViews where this calculator will display output.
	private TextWrapper curTaxAmt, curTotal;


	public SalesTax() {
		// Call the constructor in the parent class CalcFragment.
		super();

		// Create the two NumberFormat objects that this calculator will
		// use to format numbers before they are displayed to the user.
		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.sales_tax, container, false);

		// Get a reference to each of the text fields in this calculator. The
		// id's inside the R class connect this Java code to the elements in
		// the xml layout file. For example R.id.curPrice connects the new
		// EditCurrency object to the xml element that has an id of "curPrice".
		curPrice = new EditCurrency(view, R.id.curPrice, this);
		decTaxRate = new EditDecimal(view, R.id.decTaxRate,
				KEY_SALES_TAX_RATE, this);

		// Get a reference to each of the TextViews
		// where this calculator will display output.
		curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { curPrice, decTaxRate };
		ControlWrapper[] toClear = { curPrice, curTaxAmt, curTotal };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous sales tax rate entered by the user if it exits.
		decTaxRate.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the tax rate entered by the user into the preferences file.
		decTaxRate.save(editor);
	}


	@Override
	protected void compute() {
		if (curPrice.notEmpty() && decTaxRate.notEmpty()) {
			double price = curPrice.getCur();
			double taxRate = decTaxRate.getDec() / 100.0;
			double taxAmt = Consumer.Ratio.amount(taxRate, price);
			double total = Consumer.Ratio.total(taxRate, price);
			curTaxAmt.setText(fmtrCur.format(taxAmt));
			curTotal.setText(fmtrCur.format(total));
		}
		else {
			// If one or both of the inputs are empty, clear the outputs.
			curTaxAmt.clear();
			curTotal.clear();
		}
	}
}
