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
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class ROI extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCurrency startPrice;
	private EditCurrency totalMoney;
	private TextWrapper  curTotal; // took out curTaxAmt


	public ROI() {
		// Call the constructor in the parent class.
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.roi, container, false);

		// Get a reference to each of the text fields in this calculator.
		startPrice = new EditCurrency(view, R.id.startInvestment, this);
		totalMoney = new EditCurrency(view, R.id.allTheMoney, this);
		//curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { startPrice, totalMoney };
		ControlWrapper[] toClear = { startPrice, curTotal };  // took out curTaxAmt
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


//	@Override
//	protected void restorePrefs(SharedPreferences prefs) {
//		// Get the previous sales tax rate entered by the user if it exits.
//		totalMoney.restore(prefs, fmtrDec);
//	}

//	@Override
//	protected void savePrefs(SharedPreferences.Editor editor) {
//		// Write the tax rate entered by the user into the preferences file.
//		totalMoney.save(editor);
//	}


	@Override
	protected void compute() {
		if (startPrice.notEmpty() && totalMoney.notEmpty()) {
			double price = startPrice.getCur();
			double roi = (totalMoney.getCur() - startPrice.getCur())/ startPrice.getCur();
			//double taxAmt = Consumer.Ratio.amount(taxRate, price);
			double total = Consumer.Ratio.total(roi, price);
			//curTaxAmt.setText(fmtrCur.format(taxAmt));
			curTotal.setText(fmtrDec.format(roi));
		}
		else {
			//curTaxAmt.clear();
			curTotal.clear();
		}
	}
}
