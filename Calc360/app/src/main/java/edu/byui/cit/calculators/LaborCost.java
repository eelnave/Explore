package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public class LaborCost extends SolveSeries {
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
				Calc360.KEY_SALES_TAX_RATE,  this);
		curSalesTaxAmt = new EditCur(view, R.id.curSalesTaxAmt, this);
		curWage = new EditCur(view, R.id.curWage, KEY_WAGE, this);
		curSalary = new EditCur(view, R.id.curSalary, KEY_SALARY, this);
		txtOutput = new TextWrapper(view, R.id.output);

		EditWrapper[] inputs = {
			curPrice, decSalesTaxRate, curSalesTaxAmt, curWage, curSalary
		};
		EditWrapper[][] groups = {
				{ decSalesTaxRate, curSalesTaxAmt },
				{ curWage, curSalary }
		};
		ControlWrapper[] outputs = { curPrice,
				decSalesTaxRate, curSalesTaxAmt, curWage, curSalary, txtOutput
		};

		Solver[] solvers = new Solver[]{
			new Solver(new EditWrapper[]{ curPrice, decSalesTaxRate },
					new ControlWrapper[]{ curSalesTaxAmt }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double total = totalWithTaxRate(price);
					laborCost(total);
				}
			},
			new Solver(new EditWrapper[]{ curPrice, curSalesTaxAmt },
					new ControlWrapper[]{ decSalesTaxRate }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double total = totalWithTaxAmt(price);
					laborCost(total);
				}
			},

			new Solver(new EditWrapper[]{ curPrice, curSalary },
					new ControlWrapper[]{ txtOutput }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					laborCostWithSalary(price);
				}
			},
			new Solver(new EditWrapper[]{ curPrice, curWage },
					new ControlWrapper[]{ txtOutput }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double wage = curWage.getCur();
					laborCostWithWage(price, wage);
				}
			},

			new Solver(new EditWrapper[]{ curPrice, decSalesTaxRate, curSalary },
					new ControlWrapper[]{ curSalesTaxAmt, txtOutput }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double total = totalWithTaxRate(price);
					laborCostWithSalary(total);
				}
			},
			new Solver(new EditWrapper[]{ curPrice, decSalesTaxRate, curWage },
					new ControlWrapper[]{ curSalesTaxAmt, txtOutput }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double total = totalWithTaxRate(price);
					double wage = curWage.getCur();
					laborCostWithWage(total, wage);
				}
			},

			new Solver(new EditWrapper[]{ curPrice, curSalesTaxAmt, curSalary },
					new ControlWrapper[]{ decSalesTaxRate, txtOutput }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double total = totalWithTaxAmt(price);
					laborCostWithSalary(total);
				}
			},
			new Solver(new EditWrapper[]{ curPrice, curSalesTaxAmt, curWage },
					new ControlWrapper[]{ decSalesTaxRate, txtOutput }) {
				@Override
				public void solve() {
					double price = curPrice.getCur();
					double total = totalWithTaxAmt(price);
					double wage = curWage.getCur();
					laborCostWithWage(total, wage);
				}
			}
		};

		initialize(view, inputs, groups, solvers, R.id.btnClear, outputs);
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


	private double totalWithTaxRate(double price) {
		double taxAmt = 0;
		double taxRate = decSalesTaxRate.getDec() / 100.0;
		if (taxRate > 0) {
			taxAmt = price * taxRate;
			curSalesTaxAmt.setText(fmtrCur.format(taxAmt));
		}
		return price + taxAmt;
	}

	private double totalWithTaxAmt(double price) {
		double taxAmt = curSalesTaxAmt.getCur();
		double taxRate = taxAmt / price * 100.0;
		decSalesTaxRate.setText(fmtrRate.format(taxRate));
		return price + taxAmt;
	}


	private static final int workWeeksPerYear = 48;
	private static final int workDaysPerWeek = 5;
	private static final int workHoursPerDay = 8;
	private static final int workHoursPerYear =
			workWeeksPerYear * workDaysPerWeek * workHoursPerDay;
	private static final int minutesPerHour = 60;


	private void laborCost(double total) {
		if (curSalary.notEmpty()) {
			laborCostWithSalary(total);
		}
		else if (curWage.notEmpty()) {
			double wage = curWage.getCur();
			laborCostWithWage(total, wage);
		}
	}

	private void laborCostWithSalary(double total) {
		double wage = curSalary.getCur() / workHoursPerYear;
		laborCostWithWage(total, wage);
	}


	private void laborCostWithWage(double total, double wage) {
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

		String output = "You must work for";
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
		txtOutput.setText(output);
	}
}
