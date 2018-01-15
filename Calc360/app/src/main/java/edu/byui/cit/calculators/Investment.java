package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveAll;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;
import edu.byui.cit.text.SpinInt;

import static edu.byui.cit.model.Finance.investPresentValue;
import static edu.byui.cit.model.Finance.investPayment;
import static edu.byui.cit.model.Finance.investAnnualRate;
import static edu.byui.cit.model.Finance.investYears;
import static edu.byui.cit.model.Finance.investFutureValue;


public final class Investment extends SolveAll {
	private static final String KEY_PPY = "Invest.ppy";
	private final NumberFormat fmtrCur, fmtrRate, fmtrYears;
	private EditCur curPV, curPay;
	private EditDec decAR, decYears;
	private SpinInt spinPPY;
	private EditCur curFV;

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

		curPV = new EditCur(view, R.id.curPV, this);
		curPay = new EditCur(view, R.id.curPay, this);
		decAR = new EditDec(view, R.id.decAR, this);
		decYears = new EditDec(view, R.id.decYears, this);

		Activity act = getActivity();
		spinPPY = new SpinInt(act, view, R.id.spinPPY,
				R.array.possiblePPY, KEY_PPY, this);
		SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
		int deflt = (Integer)spinPPY.getItemAtPosition(spinPPY.getCount() - 1);
		spinPPY.restore(prefs, deflt);

		curFV = new EditCur(view, R.id.curFV, this);
		Input[] inputs = { curPV, curPay, decAR, decYears, curFV };

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
//				null,

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

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		spinPPY.save(editor);
	}
}
