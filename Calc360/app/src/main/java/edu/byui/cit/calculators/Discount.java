package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer.Ratio;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditCurrency;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;


public final class Discount extends CalcFragment {
	private final NumberFormat fmtrCur, fmtrDec;
	private EditCurrency curPrice;
	private EditDecimal decDiscRate;
	private EditCurrency curDiscAmt;
	private TextWrapper curDiscPrice;
	private EditDecimal decTaxRate;
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

		// Attach all the fields to an EditDecimal or EditCurrency object
		curPrice = new EditCurrency(view, R.id.curPrice, this);
		decDiscRate = new EditDecimal(view, R.id.decDiscRate, this);
		curDiscAmt = new EditCurrency(view, R.id.curDiscAmt, this);
		curDiscPrice = new TextWrapper(view, R.id.curDiscPrice);
		decTaxRate = new EditDecimal(view, R.id.decTaxRate,
				SalesTax.KEY_SALES_TAX_RATE, this);
		curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);
		curSaved = new TextWrapper(view, R.id.curSaved);

		EditWrapper[] inputs = { curPrice, decDiscRate, curDiscAmt, decTaxRate };
		EditWrapper[][] groups = {{ decDiscRate, curDiscAmt }};
		WidgetWrapper[] toClear = {
				curPrice, decDiscRate, curDiscAmt,
				curDiscPrice, curTaxAmt, curTotal, curSaved
		};
		initialize(view, inputs, groups, R.id.btnClear, toClear);
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
	protected void compute(WidgetWrapper source) {
		if (curPrice.hasUserInput()) {
			double price = curPrice.getCur();

			// Compute the discount amount or discount rate.
			// Also, compute the discounted price.
			double discAmt = 0;
			if (decDiscRate.hasUserInput()) {
				double discRate = decDiscRate.getDec() / 100.0;
				discAmt = Ratio.amount(discRate, price);
				curDiscAmt.setText(fmtrCur.format(discAmt));
			}
			else if (curDiscAmt.hasUserInput()) {
				discAmt = curDiscAmt.getCur();
				double discRate = Ratio.rate(discAmt, price);
				decDiscRate.setText(fmtrDec.format(discRate * 100.0));
			}
			double discPrice = price - discAmt;
			curDiscPrice.setText(fmtrCur.format(discPrice));

			// Compute the sales tax amount and the total.
			double taxRate = 0;
			double taxAmt = 0;
			if (decTaxRate.hasUserInput()) {
				taxRate = decTaxRate.getDec() / 100.0;
				taxAmt = Ratio.amount(taxRate, discPrice);
				curTaxAmt.setText(fmtrCur.format(taxAmt));
			}
			double discTotal = discPrice + taxAmt;
			curTotal.setText(fmtrCur.format(discTotal));

			// Compute the amount saved.
			double origTotal = Ratio.total(taxRate, price);
			double saved = origTotal - discTotal;
			curSaved.setText(fmtrCur.format(saved));
		}
		else {
			clearOutput();
		}
	}
}
