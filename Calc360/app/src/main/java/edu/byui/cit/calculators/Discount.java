package edu.byui.cit.calculators;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import static edu.byui.cit.model.Consumer.Discount.*;
import static edu.byui.cit.model.Consumer.Rate.*;
import static edu.byui.cit.text.EditWrapper.anyNotEmpty;


public final class Discount extends CalcFragment {
	private final NumberFormat fmtrCur, fmtrDec;
	private EditCur curPrice;
	private EditDec decDiscRate;
	private EditCur curDiscAmt;
	private TextWrapper curDiscPrice;
	private EditDec decTaxRate;
	private TextWrapper curTaxAmt, curTotal, curSaved;


	public Discount() {
		// Call the parent constructor and pass this calculator's title
		// to the parent so that the title will be displayed when this
		// calculator is active.
		super();
		fmtrCur = NumberFormat.getCurrencyInstance();
		fmtrDec = NumberFormat.getNumberInstance();
		fmtrDec.setMaximumFractionDigits(2);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.discount, container, false);

		//Attach all the fields to an EditDec or EditCur object
		curPrice = new EditCur(view, R.id.curPrice, this);
		decDiscRate = new EditDec(view, R.id.decDiscRate, this);
		curDiscAmt = new EditCur(view, R.id.curDiscAmt, this);
		curDiscPrice = new TextWrapper(view, R.id.curDiscPrice);
		decTaxRate = new EditDec(view, R.id.decTaxRate, this);
		curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);
		curSaved = new TextWrapper(view, R.id.curSaved);

		// Restore the user enter sales tax rate if it exists.
		SharedPreferences prefs = getActivity().getPreferences(
				Context.MODE_PRIVATE);
		float taxRate = prefs.getFloat(Calc360.KEY_SALES_TAX_RATE, 0);
		decTaxRate.setText(fmtrDec.format(taxRate));

		// Add a button click listener to the Clear button.
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());

		return view;
	}


	@Override
	protected void compute() {
		try {
			clearResults();

			if (curPrice.notEmpty() &&
					anyNotEmpty(decDiscRate, curDiscAmt, decTaxRate)) {
				double price = curPrice.getCur();

				double discRate, discPrice;
				if (decDiscRate.notEmpty() && curDiscAmt.notFocus()) {
					discRate = decDiscRate.getDec() / 100.0;
					double discAmt = discountAmount(price, discRate);
					discPrice = discountedPrice(price, discRate);
					curDiscAmt.setText(fmtrCur.format(discAmt));
					curDiscPrice.setText(fmtrCur.format(discPrice));
				}
				else if (decDiscRate.notFocus() && curDiscAmt.notEmpty()) {
					double discAmt = curDiscAmt.getCur();
					discRate = rate(discAmt, price);
					discPrice = price - discAmt;
					decDiscRate.setText(fmtrDec.format(discRate * 100.0));
					curDiscPrice.setText(fmtrCur.format(discPrice));
				}
				else {
					discRate = 0;
					discPrice = price;
					decDiscRate.clear();
					curDiscAmt.clear();
				}

				double taxRate;
				if (decTaxRate.notEmpty()) {
					taxRate = decTaxRate.getDec() / 100.0;
					double taxAmt = amount(discPrice, taxRate);
					curTaxAmt.setText(fmtrCur.format(taxAmt));
					double total = total(discPrice, taxRate);
					curTotal.setText(fmtrCur.format(total));
				}
				else {
					taxRate = 0;
				}

				if (discRate > 0) {
					double saved = amountSaved(price, discRate, taxRate);
					curSaved.setText(fmtrCur.format(saved));
				}
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		float rate = (float)decTaxRate.getDec();
		editor.putFloat(Calc360.KEY_SALES_TAX_RATE, rate);
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			curPrice.clear();
			decDiscRate.clear();
			curDiscAmt.clear();
			clearResults();
			curPrice.requestFocus();
		}
	}

	private void clearResults() {
		curDiscPrice.clear();
		curTaxAmt.clear();
		curTotal.clear();
		curSaved.clear();
	}
}
