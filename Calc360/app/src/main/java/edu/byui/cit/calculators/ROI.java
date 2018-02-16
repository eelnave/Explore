package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class ROI extends CalcFragment {


	private EditCurrency startPrice;
	private EditCurrency totalMoney;
	private TextWrapper  curTotal;


	public ROI() {
		super();

	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.roi, container, false);

		startPrice = new EditCurrency(view, R.id.startInvestment, this);
		totalMoney = new EditCurrency(view, R.id.allTheMoney, this);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { startPrice, totalMoney };
		ControlWrapper[] toClear = { startPrice, curTotal, totalMoney};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}



	@Override
	protected void compute() {
		if (startPrice.notEmpty() && totalMoney.notEmpty()) {
			double roi = ((totalMoney.getCur() - startPrice.getCur())/startPrice.getCur()) * 100;
			NumberFormat formatter = new DecimalFormat("#0");
			curTotal.setText(formatter.format(roi) + "%");
		}
		else {
			curTotal.clear();

		}
	}
}
