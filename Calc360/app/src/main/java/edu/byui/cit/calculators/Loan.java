package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.model.Finance;
import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditCurrency;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.SpinInteger;
import edu.byui.cit.widget.TextWrapper;


public final class Loan extends SolveSeries {
	private static final String KEY_PPY = "Loan.ppy";

	private final NumberFormat fmtrCur, fmtrRate, fmtrYears, fmtrInt, fmtrDec;
	private EditCurrency curAmt;
	private EditDecimal decAR;
	private EditDecimal decYears;
	private SpinInteger spinPPY;
	private EditCurrency curPay;
	private EditInteger intPTD;
	private TextWrapper curBal;
	private TextWrapper txtPeriod, txtInter, txtPrinc, txtBal;

	public Loan() {
		super();
		fmtrCur = NumberFormat.getCurrencyInstance();

		fmtrRate = NumberFormat.getInstance();
		fmtrRate.setMinimumFractionDigits(1);
		fmtrRate.setMaximumFractionDigits(3);

		fmtrYears = NumberFormat.getInstance();
		fmtrYears.setMinimumFractionDigits(0);
		fmtrYears.setMaximumFractionDigits(2);

		fmtrInt = NumberFormat.getIntegerInstance();

		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMinimumFractionDigits(fmtrCur.getMinimumFractionDigits());
		fmtrDec.setMaximumFractionDigits(fmtrCur.getMaximumFractionDigits());
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.loan, container, false);

		curAmt = new EditCurrency(view, R.id.curAmt, this);
		decAR = new EditDecimal(view, R.id.decAR, this);
		decYears = new EditDecimal(view, R.id.decYears, this);

		Activity act = getActivity();
		spinPPY = new SpinInteger(act, view, R.id.spinPPY,
				R.array.possiblePPY, KEY_PPY, this);

		curPay = new EditCurrency(view, R.id.curPay, this);
		intPTD = new EditInteger(view, R.id.intPTD, this);
		curBal = new TextWrapper(view, R.id.curBal);
		txtPeriod = new TextWrapper(view, R.id.txtPeriod);
		txtInter = new TextWrapper(view, R.id.txtInter);
		txtPrinc = new TextWrapper(view, R.id.txtPrinc);
		txtBal = new TextWrapper(view, R.id.txtBal);
		EditWrapper[] inputs = { curAmt, decAR, decYears, curPay, intPTD };
		WidgetWrapper[] toClear = {
				curAmt, decAR, decYears, curPay, intPTD,
				curBal, txtPeriod, txtInter, txtPrinc, txtBal
		};

		Solver[] solvers = new Solver[]{
				// Solve for the loan amount.
				new Solver(new EditWrapper[]{ decAR, decYears, curPay },
						new WidgetWrapper[]{ curAmt, txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computeAmount();
						amortTable();
					}
				},

				// Solve for the annual rate.
				new Solver(new EditWrapper[]{ curAmt, decYears, curPay },
						new WidgetWrapper[]{ decAR, txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computeAnnualRate();
						amortTable();
					}
				},

				// Solve for the number of years.
				new Solver(new EditWrapper[]{ curAmt, decAR, curPay },
						new WidgetWrapper[]{ decYears, txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computeYears();
						amortTable();
					}
				},

				// Solve for the payment amount.
				new Solver(new EditWrapper[]{ curAmt, decAR, decYears },
						new WidgetWrapper[]{ curPay, txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computePayment();
						amortTable();
					}
				},

				// Solve for the remaining balance.
				new Solver(new EditWrapper[]{ decAR, decYears, curPay, intPTD },
						new WidgetWrapper[]{ curAmt, curBal,
								txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computeAmount();
						computeBalance();
						amortTable();
					}
				},
				new Solver(new EditWrapper[]{ curAmt, decYears, curPay, intPTD },
						new WidgetWrapper[]{ decAR, curBal,
								txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computeAnnualRate();
						computeBalance();
						amortTable();
					}
				},
				new Solver(new EditWrapper[]{ curAmt, decAR, curPay, intPTD },
						new WidgetWrapper[]{ decYears, curBal,
								txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computeYears();
						computeBalance();
						amortTable();
					}
				},
				new Solver(new EditWrapper[]{ curAmt, decAR, decYears, intPTD },
						new WidgetWrapper[]{ curPay, curBal,
								txtPeriod, txtInter, txtPrinc, txtBal }) {
					@Override
					public void solve() {
						computePayment();
						computeBalance();
						amortTable();
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, toClear);
		return view;
	}

	private void computeAmount() {
		double ar = decAR.getDec() / 100.0;
		double y = decYears.getDec();
		int ppy = spinPPY.getInt();
		double p = curPay.getCur();
		double amt = Finance.loanAmount(ar, y, ppy, p);
		curAmt.setText(fmtrCur.format(amt));
	}

	private void computeAnnualRate() {
		double amt = curAmt.getCur();
		double y = decYears.getDec();
		int ppy = spinPPY.getInt();
		double p = curPay.getCur();
		double ar = Finance.loanAnnualRate(amt, y, ppy, p);
		decAR.setText(fmtrRate.format(ar * 100.0));
	}

	private void computeYears() {
		double amt = curAmt.getCur();
		double ar = decAR.getDec() / 100.0;
		int ppy = spinPPY.getInt();
		double p = curPay.getCur();
		double y = Finance.loanYears(amt, ar, ppy, p);
		decYears.setText(fmtrYears.format(y));
	}

	private void computePayment() {
		double amt = curAmt.getCur();
		double ar = decAR.getDec() / 100.0;
		double y = decYears.getDec();
		int ppy = spinPPY.getInt();
		int fract = fmtrCur.getMaximumFractionDigits();
		double p = Finance.loanPayment(fract, amt, ar, y, ppy);
		curPay.setText(fmtrCur.format(p));
	}

	private void computeBalance() {
		double amt = curAmt.getCur();
		double ar = decAR.getDec() / 100.0;
		double y = decYears.getDec();
		int ppy = spinPPY.getInt();
		int ptd = intPTD.getInt();
		int fract = fmtrCur.getMaximumFractionDigits();
		double b = Finance.loanBalance(fract, amt, ar, y, ppy, ptd);
		curBal.setText(fmtrCur.format(b));
	}

	private void amortTable() {
		double amt = curAmt.getCur();
		double ar = decAR.getDec() / 100.0;
		double y = decYears.getDec();
		int ppy = spinPPY.getInt();
		double pay = curPay.getCur();
		int ptd = intPTD.isEmpty() ? 0 : intPTD.getInt();
		int fract = fmtrCur.getMaximumFractionDigits();
		double bal = Finance.loanBalance(fract, amt, ar, y, ppy, ptd);
		StringBuilder sbPer = new StringBuilder(getString(R.string.Period)).append("\n\n");
		StringBuilder sbInter = new StringBuilder(getString(R.string.Interest)).append("\n\n");
		StringBuilder sbPrinc = new StringBuilder(getString(R.string.Principal)).append("\n\n");
		StringBuilder sbBal = new StringBuilder(getString(R.string.Balance)).append("\n");
		sbBal.append(fmtrDec.format(bal)).append('\n');
		double rate = ar / ppy;
		int nper = (int)Math.ceil(y * ppy);
		while (ptd < nper) {
			++ptd;
			double inter = bal * rate;
			double princ = pay - inter;
			if (princ > bal) {
				princ = bal;
			}
			bal -= princ;
			sbPer.append(fmtrInt.format(ptd)).append('\n');
			sbInter.append(fmtrDec.format(inter)).append('\n');
			sbPrinc.append(fmtrDec.format(princ)).append('\n');
			sbBal.append(fmtrDec.format(bal)).append('\n');
		}
		txtPeriod.setText(sbPer.toString());
		txtInter.setText(sbInter.toString());
		txtPrinc.setText(sbPrinc.toString());
		txtBal.setText(sbBal.toString());
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
