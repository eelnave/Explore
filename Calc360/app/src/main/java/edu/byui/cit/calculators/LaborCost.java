package edu.byui.cit.calculators;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.TextChangedHandler;
import edu.byui.cit.text.TextWrapper;


public class LaborCost extends CalcFragment {
	private static final String
			KEY_WAGE = "LaborCost.wage",
			KEY_SALARY = "LaborCost.salary";

	private final NumberFormat fmtrRate, fmtrCur, fmtrInt;
	private EditCur curPrice, curSalesTaxAmt, curWage, curSalary;
	private EditDec decSalesTaxRate;
	private TextWrapper txtOutput;

	public LaborCost() {
		super();
		fmtrRate = NumberFormat.getInstance();
		fmtrRate.setMaximumFractionDigits(1);
		fmtrCur = NumberFormat.getCurrencyInstance();
		fmtrInt = NumberFormat.getIntegerInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.labor_cost, container, false);

		curPrice = new EditCur(view, R.id.curPrice, this);
		decSalesTaxRate = new EditDec(view, R.id.decSalesTaxRate,
				new SalesTaxRateHandler());
		curSalesTaxAmt = new EditCur(view, R.id.curSalesTaxAmt,
				new SalesTaxAmountHandler());
		curWage = new EditCur(view, R.id.curWage, new WageHandler());
		curSalary = new EditCur(view, R.id.curSalary, new SalaryHandler());
		txtOutput = new TextWrapper(view, R.id.output);
		restorePrefs();

		// Set this calculator as the click listener for the clear button.
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());

		return view;
	}


	private final class SalesTaxRateHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable editable) {
			try {
				String strTaxAmt = "";
				if (curPrice.notEmpty() && decSalesTaxRate.notEmpty()) {
					double price = curPrice.getCur();
					double taxRate = decSalesTaxRate.getDec() / 100.0;
					double taxAmt = price * taxRate;
					strTaxAmt = fmtrCur.format(taxAmt);
				}
				curSalesTaxAmt.setText(strTaxAmt);
				compute();
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}


	private final class SalesTaxAmountHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable editable) {
			try {
				String strTaxRate = "";
				if (curPrice.notEmpty() && curSalesTaxAmt.notEmpty()) {
					double price = curPrice.getCur();
					double taxAmt = curSalesTaxAmt.getCur();
					double taxRate = taxAmt / price * 100.0;
					strTaxRate = fmtrRate.format(taxRate);
				}
				decSalesTaxRate.getEdit().setText(strTaxRate);
				compute();
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}


	private final class WageHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable editable) {
			try {
				if (curWage.notEmpty()) {
					curSalary.clear();
				}
				compute();
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}


	private final class SalaryHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable editable) {
			try {
				if (curSalary.notEmpty()) {
					curWage.clear();
				}
				compute();
			}
			catch (NumberFormatException ex) {
				// Do nothing
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	}


	@Override
	protected void compute() {
		double price = 0;
		if (curPrice.notEmpty()) {
			price = curPrice.getCur();
		}

		double taxAmt = 0;
		String output = "";
		if (curPrice.notEmpty() && decSalesTaxRate.notEmpty()) {
			double taxRate = decSalesTaxRate.getDec() / 100.0;
			taxAmt = price * taxRate;
			output = fmtrCur.format(taxAmt);
		}
		curSalesTaxAmt.setText(output);

		double total = price + taxAmt;
		output = "";
		if (curPrice.notEmpty() && (curWage.notEmpty() || curSalary.notEmpty()
		)) {
			final int maxFractDigits = 2;
			final int multiplier = (int)Math.pow(10, maxFractDigits);

			final int workWeeksPerYear = 48;
			final int workDaysPerWeek = 5;
			final int workHoursPerDay = 8;
			final int workHoursPerYear = workWeeksPerYear * workDaysPerWeek *
					workHoursPerDay;
			final int minutesPerHour = 60;

			double wage;
			if (curWage.notEmpty()) {
				wage = curWage.getCur();
			}
			else {
				wage = curSalary.getCur() / workHoursPerYear;
			}

			int hours = (int)Math.floor(total / wage);
			double remainTotal = total - hours * wage;

			final int years = hours / workHoursPerYear;
			int remainHours = hours - years * workHoursPerYear;
			final int days = remainHours / workHoursPerDay;
			hours = remainHours - days * workHoursPerDay;

			double wagePerMinute = wage / minutesPerHour;
			final int minutes = (int)Math.floor(remainTotal / wagePerMinute);

			remainTotal = total -
					((years * workHoursPerYear + days * workHoursPerDay +
							hours) * wage +
							minutes * wagePerMinute);
			final int cents = (int)Math.ceil(remainTotal * multiplier);

			output = "You must work for";
			String sep = " ";
			if (years > 0) {
				output += sep + fmtrInt.format(years) + " years";
				sep = ", ";
			}
			if (days > 0) {
				output += sep + fmtrInt.format(days) + " days";
				sep = ", ";
			}
			if (hours > 0) {
				output += sep + fmtrInt.format(hours) + " hours";
				sep = ", ";
			}
			if (minutes > 0) {
				output += sep + fmtrInt.format(minutes) + " minutes";
			}
			if (cents > 0) {
				output += ", and you must get " + cents + " cents from " +
						"somewhere";
			}
			output += ".";

			// Code to check the results.
//				double total = (years * workHoursPerYear + days *
// workHoursPerDay + hours) * wage;
//				total += minutes * wagePerMinute;
//				total += (double)cents / multiplier;
//				output += "\n" + total;

		}
		txtOutput.setText(output);
	}


	/** Saves the wage or salary to preferences. */
	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		if (decSalesTaxRate.notEmpty()) {
			float taxRate = (float)decSalesTaxRate.getDec();
			editor.putFloat(Calc360.KEY_SALES_TAX_RATE, taxRate);
		}

		if (curWage.notEmpty()) {
			float wage = (float)curWage.getCur();
			editor.putFloat(KEY_WAGE, wage);
			editor.remove(KEY_SALARY);
		}
		else if (curSalary.notEmpty()) {
			float salary = (float)curSalary.getCur();
			editor.putFloat(KEY_SALARY, salary);
			editor.remove(KEY_WAGE);
		}
		else {
			editor.remove(KEY_WAGE);
			editor.remove(KEY_SALARY);
		}
	}

	/**
	 * Restores the value for wage or salary
	 * if the user previously entered them.
	 */
	private void restorePrefs() {
		SharedPreferences prefs = getActivity().getPreferences(
				Context.MODE_PRIVATE);
		float taxRate = prefs.getFloat(Calc360.KEY_SALES_TAX_RATE, 0);
		decSalesTaxRate.setText(fmtrRate.format(taxRate));

		float wage = prefs.getFloat(KEY_WAGE, 0);
		if (wage > 0) {
			curWage.setText(fmtrCur.format(wage));
		}
		else {
			float salary = prefs.getFloat(KEY_SALARY, 0);
			if (salary > 0) {
				curSalary.setText(fmtrCur.format(salary));
			}
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			curPrice.clear();
			curSalesTaxAmt.clear();
			txtOutput.clear();
			curPrice.requestFocus();
		}
	}
}
