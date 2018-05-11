package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditCurrency;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;


public class LaborCost extends CalcFragment {
	private static final String
			KEY_PREFIX = "LaborCost",
			KEY_WAGE = KEY_PREFIX + ".wage",
			KEY_SALARY = KEY_PREFIX + ".salary";

	private final NumberFormat fmtrRate, fmtrCur, fmtrInt;
	private EditCurrency curPrice, curSalesTaxAmt, curWage, curSalary;
	private EditDecimal decSalesTaxRate;
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
			Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.labor_cost, container, false);

		curPrice = new EditCurrency(view, R.id.curPrice, this);
		decSalesTaxRate = new EditDecimal(view, R.id.decSalesTaxRate,
				SalesTax.KEY_SALES_TAX_RATE, this);
		curSalesTaxAmt = new EditCurrency(view, R.id.curSalesTaxAmt, this);
		curWage = new EditCurrency(view, R.id.curWage, KEY_WAGE, this);
		curSalary = new EditCurrency(view, R.id.curSalary, KEY_SALARY, this);
		txtOutput = new TextWrapper(view, R.id.output);

		EditWrapper[] inputs = {
				curPrice, decSalesTaxRate, curSalesTaxAmt, curWage, curSalary
		};
		EditWrapper[][] groups = {
				{ decSalesTaxRate, curSalesTaxAmt },
				{ curWage, curSalary }
		};
		WidgetWrapper[] toClear = { curPrice, curSalesTaxAmt, txtOutput };

		initialize(view, inputs, groups, R.id.btnClear, toClear);
		return view;
	}


	/**
	 * Restores the value for sales tax rate and wage or salary,
	 * if the user previously entered them.
	 */
	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		decSalesTaxRate.restore(prefs, fmtrRate);
		curWage.restore(prefs, fmtrCur);
		curSalary.restore(prefs, fmtrCur);
	}

	/** Saves the sales tax rate and wage or salary to preferences. */
	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		decSalesTaxRate.save(editor);
		curWage.save(editor);
		curSalary.save(editor);
	}

	@Override
	protected void compute(WidgetWrapper source) {
		if (curPrice.hasUserInput()) {
			double price = curPrice.getCur();

			// Compute the sales tax amount and the total.
			double salesTaxRate;
			double salesTaxAmt = 0;
			if (decSalesTaxRate.hasUserInput()) {
				salesTaxRate = decSalesTaxRate.getDec() / 100.0;
				salesTaxAmt = Consumer.Ratio.amount(salesTaxRate, price);
				curSalesTaxAmt.setText(fmtrCur.format(salesTaxAmt));
			}
			else if (curSalesTaxAmt.hasUserInput()) {
				salesTaxAmt = curSalesTaxAmt.getCur();
				salesTaxRate = Consumer.Ratio.rate(salesTaxAmt, price);
				decSalesTaxRate.setText(fmtrRate.format(salesTaxRate * 100.0));
			}

			if (curSalary.hasUserInput() || curWage.hasUserInput()) {
				double total = price + salesTaxAmt;
				laborCost(total);
			}
			else {
				txtOutput.clear();
			}
		}
		else {
			if (!curSalesTaxAmt.hasUserInput()) {
				curSalesTaxAmt.clear();
			}
			txtOutput.clear();
		}
	}


	private static final int workWeeksPerYear = 48;
	private static final int workDaysPerWeek = 5;
	private static final int workHoursPerDay = 8;
	private static final int workHoursPerYear =
			workWeeksPerYear * workDaysPerWeek * workHoursPerDay;
	private static final int minutesPerHour = 60;


	private void laborCost(double total) {
		double wage;
		if (curSalary.hasUserInput()) {
			wage = curSalary.getCur() / workHoursPerYear;
		}
		else {
			wage = curWage.getCur();
		}

		int hours = (int)Math.floor(total / wage);
		double remainTotal = total - hours * wage;

		final int years = hours / workHoursPerYear;
		int remainHours = hours - years * workHoursPerYear;
		final int days = remainHours / workHoursPerDay;
		hours = remainHours - days * workHoursPerDay;

		double wagePerMinute = wage / minutesPerHour;
		final int minutes = (int)Math.floor(remainTotal / wagePerMinute);

		final int maxFractDigits = fmtrCur.getMaximumFractionDigits();
		final int multiplier = (int)Math.pow(10, maxFractDigits);
		remainTotal = total -
			((years * workHoursPerYear + days * workHoursPerDay + hours) * wage +
				minutes * wagePerMinute);
		final int cents = (int)Math.ceil(remainTotal * multiplier);

		Resources res = getResources();
		String output = res.getString(R.string.mustWork);
		String sep = " ";
		if (years > 0) {
			output += sep + fmtrInt.format(years) + ' ' +
					res.getQuantityString(R.plurals.year, years);
			sep = ", ";
		}
		if (days > 0) {
			output += sep + fmtrInt.format(days) + ' ' +
					res.getQuantityString(R.plurals.day, days);
			sep = ", ";
		}
		if (hours > 0) {
			output += sep + fmtrInt.format(hours) + ' ' +
					res.getQuantityString(R.plurals.hour, hours);
			sep = ", ";
		}
		if (minutes > 0) {
			output += sep + fmtrInt.format(minutes) + ' ' +
					res.getQuantityString(R.plurals.minute, minutes);
		}
		if (cents > 0) {
			output += ", " + res.getString(R.string.mustGet) + ' ' +
					cents + ' ' + res.getQuantityString(R.plurals.cent, cents) +
					' ' + res.getString(R.string.somewhere);
		}
		output += ".";

		// Code to check the results.
//				double total = (years * workHoursPerYear +
//						days * workHoursPerDay + hours) * wage;
//				total += minutes * wagePerMinute;
//				total += (double)cents / multiplier;
//				output += "\n" + total;
		txtOutput.setText(output);
	}
}
