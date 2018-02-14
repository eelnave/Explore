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
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class ROI extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	private EditCurrency startPrice;
	private EditCurrency totalMoney;
	private TextWrapper curTaxAmt, curTotal;


	public ROI() {
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.roi, container, false);

		startPrice = new EditCurrency(view, R.id.startInvestment, this);
		totalMoney = new EditCurrency(view, R.id.allTheMoney, this);
		curTaxAmt = new TextWrapper(view, R.id.curTaxAmt);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { startPrice, totalMoney };
		ControlWrapper[] toClear = { startPrice, curTaxAmt, curTotal };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		totalMoney.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		totalMoney.save(editor);
	}


	@Override
	protected void compute() {
		if (startPrice.notEmpty() && totalMoney.notEmpty()) {
			double price = startPrice.getCur();
			double taxRate = totalMoney.getCur() / 100.0;
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
