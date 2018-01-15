package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.model.Finance;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;


public final class CompoundInterest extends CalcFragment {
	private final NumberFormat fmtrCur;
	private EditCur deposit;
	private EditDec interestRate;
	private EditInt numberOfYears;
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

		deposit = new EditCur(view, R.id.deposit, this);
		interestRate = new EditDec(view, R.id.interestRate, this);
		numberOfYears = new EditInt(view, R.id.numberOfYears, this);

		totalAmount = new TextWrapper(view, R.id.totalAmount);

		radMonthly = new RadioWrapper(view, R.id.radMonthly, this);
		radQuarterly = new RadioWrapper(view, R.id.radQuarterly, this);
		new RadioWrapper(view, R.id.radAnnually, this);

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	protected void compute() {
		if (deposit.notEmpty() && interestRate.notEmpty() &&
				numberOfYears.notEmpty()) {
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


	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			deposit.clear();
			interestRate.clear();
			numberOfYears.clear();
			totalAmount.clear();
			deposit.requestFocus();
		}
	}
}
