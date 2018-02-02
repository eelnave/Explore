package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinInteger;

import static edu.byui.cit.model.Finance.investPresentValue;
import static edu.byui.cit.model.Finance.investPayment;
import static edu.byui.cit.model.Finance.investAnnualRate;
import static edu.byui.cit.model.Finance.investYears;
import static edu.byui.cit.model.Finance.investFutureValue;


public final class Investment extends SolveEquation {
	private static final String KEY_PPY = "Invest.ppy";
	private final NumberFormat fmtrCur, fmtrRate, fmtrYears;
	private EditCurrency curPV, curPay;
	private EditDecimal decAR, decYears;
	private SpinInteger spinPPY;
	private EditCurrency curFV;

	public Investment() {
		super();
		fmtrCur = NumberFormat.getCurrencyInstance();
		fmtrRate = NumberFormat.getInstance();
		fmtrYears = NumberFormat.getInstance();
		fmtrRate.setMinimumFractionDigits(1);
		fmtrRate.setMaximumFractionDigits(3);
		fmtrYears.setMinimumFractionDigits(0);
		fmtrYears.setMaximumFractionDigits(2);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.investment, container, false);

		curPV = new EditCurrency(view, R.id.curPV, this);
		curPay = new EditCurrency(view, R.id.curPay, this);
		decAR = new EditDecimal(view, R.id.decAR, this);
		decYears = new EditDecimal(view, R.id.decYears, this);

		Activity act = getActivity();
		spinPPY = new SpinInteger(act, view, R.id.spinPPY,
				R.array.possiblePPY, KEY_PPY, this);

		curFV = new EditCurrency(view, R.id.curFV, this);
		EditWrapper[] inputs = { curPV, curPay, decAR, decYears, curFV };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double pay = curPay.getCur();
						double ar = decAR.getDec();
						double y = decYears.getDec();
						int ppy = spinPPY.getInt();
						double fv = curFV.getCur();
						double pv = investPresentValue(pay, ppy, ar, y, fv);
						curPV.setText(fmtrCur.format(pv));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double pv = curPV.getCur();
						double ar = decAR.getDec();
						double y = decYears.getDec();
						int ppy = spinPPY.getInt();
						double fv = curFV.getCur();
						double pay = investPayment(pv, ppy, ar, y, fv);
						curPay.setText(fmtrCur.format(pay));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double pv = curPV.getCur();
						double pay = curPay.getCur();
						double y = decYears.getDec();
						int ppy = spinPPY.getInt();
						double fv = curFV.getCur();
						double ar = investAnnualRate(pv, pay, ppy, y, fv);
						decAR.setText(fmtrRate.format(ar));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double pv = curPV.getCur();
						double pay = curPay.getCur();
						double ar = decAR.getDec();
						int ppy = spinPPY.getInt();
						double fv = curFV.getCur();
						double y = investYears(pv, pay, ppy, ar, fv);
						decYears.setText(fmtrYears.format(y));
					}
				},

				// Never compute periods per year because
				// the user must always select it.

				new Solver() {
					@Override
					public void solve() {
						double pv = curPV.getCur();
						double pay = curPay.getCur();
						double ar = decAR.getDec();
						double y = decYears.getDec();
						int ppy = spinPPY.getInt();
						double fv = investFutureValue(pv, pay, ppy, ar, y);
						curFV.setText(fmtrCur.format(fv));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		int last = spinPPY.getCount() - 1;
		int deflt = (Integer)spinPPY.getItemAtPosition(last);
		spinPPY.restore(prefs, deflt);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		spinPPY.save(editor);
	}
}
