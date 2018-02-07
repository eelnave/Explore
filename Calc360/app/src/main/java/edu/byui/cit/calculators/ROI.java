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
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class ROI extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCurrency startPrice;
	private EditDecimal decTaxRate;
	private TextWrapper curTaxAmt, curTotal;


	public ROI() {
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
		startPrice = new EditCurrency(view, R.id.startInvestment, this);
		decTaxRate = new EditDecimal(view, R.id.decTaxRate,
				Calc360.KEY_SALES_TAX_RATE, this);
		curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { startPrice, decTaxRate };
		ControlWrapper[] toClear = { startPrice, curTaxAmt, curTotal };
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
		if (startPrice.notEmpty() && decTaxRate.notEmpty()) {
			double price = startPrice.getCur();
			double taxRate = decTaxRate.getDec() / 100.0;
			double taxAmt = Consumer.Ratio.amount(taxRate, price);
			double total = Consumer.Ratio.total(taxRate, price);
			curTaxAmt.setText(fmtrCur.format(taxAmt));
			curTotal.setText(fmtrCur.format(total));
		}
		else {
			curTaxAmt.clear();
			curTotal.clear();
		}
	}
}
