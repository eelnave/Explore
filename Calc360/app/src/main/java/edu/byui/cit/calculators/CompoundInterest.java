package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.model.Finance;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditCurrency;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.RadioWrapper;
import edu.byui.cit.widget.TextWrapper;


public final class CompoundInterest extends CalcFragment {
	private final NumberFormat fmtrCur;
	private EditCurrency deposit;
	private EditDecimal interestRate;
	private EditInteger numberOfYears;
	private RadioWrapper radMonthly, radQuarterly;
	private TextWrapper totalAmount;

	public CompoundInterest() {
		super();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.compound_interest, container,
				false);

		deposit = new EditCurrency(view, R.id.deposit, this);
		interestRate = new EditDecimal(view, R.id.interestRate, this);
		numberOfYears = new EditInteger(view, R.id.numberOfYears, this);

		radMonthly = new RadioWrapper(view, R.id.radMonthly, this);
		radQuarterly = new RadioWrapper(view, R.id.radQuarterly, this);
		new RadioWrapper(view, R.id.radAnnually, this);

		totalAmount = new TextWrapper(view, R.id.totalAmount);

		EditWrapper[] inputs = { deposit, interestRate, numberOfYears };
		WidgetWrapper[] toClear = {
				deposit, interestRate, numberOfYears, totalAmount
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		if (EditWrapper.allNotEmpty(deposit, interestRate, numberOfYears)) {
			double a = deposit.getCur();
			double ir = interestRate.getDec();
			int y = numberOfYears.getInt();
			int ppy;
			if (radMonthly.isChecked()) {
				ppy = 12;
			}
			else if (radQuarterly.isChecked()) {
				ppy = 4;
			}
			else {
				ppy = 1;
			}
			double fv = Finance.compoundInterest(a, ir, ppy, y);
			totalAmount.setText(fmtrCur.format(fv));
		}
		else {
			totalAmount.clear();
		}
	}
}
