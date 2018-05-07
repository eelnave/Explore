package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class ReturnOnInvest extends CalcFragment {
	private final NumberFormat fmtrPerc;
	private EditCurrency curIncome, curExpenses, curInvest;
	private EditWrapper[] inputs;
	private TextWrapper percROI;


	public ReturnOnInvest() {
		super();
		fmtrPerc = NumberFormat.getPercentInstance();
		fmtrPerc.setMaximumFractionDigits(1);
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.return_on_invest, container, false);

		curIncome = new EditCurrency(view, R.id.curIncome, this);
		curExpenses = new EditCurrency(view, R.id.curExpenses, this);
		curInvest = new EditCurrency(view, R.id.curInvest, this);
		percROI = new TextWrapper(view, R.id.percROI);

		inputs = new EditWrapper[]{ curIncome, curExpenses, curInvest };
		ControlWrapper[] toClear = {
				curIncome, curExpenses, curInvest, percROI
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {
			double income = curIncome.getCur();
			double expenses = curExpenses.getCur();
			double invest = curInvest.getCur();
			double roi = (income - expenses) / invest;
			percROI.setText(fmtrPerc.format(roi));
		}
		else {
			percROI.clear();
		}
	}
}
