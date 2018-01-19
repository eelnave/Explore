package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.TextWrapper;

import static edu.byui.cit.model.Consumer.Ratio.*;


public final class SalesTax extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCur curPrice;
	private EditDec decTaxRate;
	private TextWrapper curTaxAmt, curTotal;

	public SalesTax() {
		// Call the constructor in the parent class.
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.sales_tax, container, false);

		// Get a reference to each of the text fields in this calculator.
		curPrice = new EditCur(view, R.id.curPrice, this);
		decTaxRate = new EditDec(view, R.id.decTaxRate,
				Calc360.KEY_SALES_TAX_RATE, this);
		curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous tax rate entered by the user if it exits.
		decTaxRate.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write into the preferences file
		// the tax rate entered by the user.
		decTaxRate.save(editor);
	}


	@Override
	protected void compute() {
		clearResults();
		if (curPrice.notEmpty() && decTaxRate.notEmpty()) {
			double price = curPrice.getCur();
			double taxRate = decTaxRate.getDec() / 100.0;
			double taxAmt = amount(taxRate, price);
			double total = total(taxRate, price);
			curTaxAmt.setText(fmtrCur.format(taxAmt));
			curTotal.setText(fmtrCur.format(total));
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			curPrice.clear();
			clearResults();
			curPrice.requestFocus();
		}
	}

	private void clearResults() {
		curTaxAmt.clear();
		curTotal.clear();
	}
}
