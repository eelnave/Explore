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
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;


public final class HabitCost extends CalcFragment {
	private final NumberFormat fmtrCur;
	private EditCurrency cost;
	private EditDecimal frequency;
	private RadioWrapper radDaily, radWeekly, radMonthly, radYearly;
	private TextWrapper dailyAmount, weeklyAmount, monthlyAmount, yearlyAmount;

	public HabitCost() {
		super();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.habit_cost, container,
				false);

		cost = new EditCurrency(view, R.id.costHabit, this);
		frequency = new EditDecimal(view, R.id.frequencyHabit, this);

		radDaily = new RadioWrapper(view, R.id.radDaily, this);
		radWeekly = new RadioWrapper(view, R.id.radWeekly, this);
		radMonthly = new RadioWrapper(view, R.id.radMonthly, this);
		radYearly = new RadioWrapper(view, R.id.radYearly, this);

		dailyAmount = new TextWrapper(view, R.id.dailyAmount);
		weeklyAmount = new TextWrapper(view, R.id.weeklyAmount);
		monthlyAmount = new TextWrapper(view, R.id.monthlyAmount);
		yearlyAmount = new TextWrapper(view, R.id.yearlyAmount);

		EditWrapper[] inputs = { cost, frequency };
		ControlWrapper[] toClear = {
				cost, frequency,
				dailyAmount, weeklyAmount, monthlyAmount, yearlyAmount
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(cost, frequency)) {
			double c = cost.getCur();
			double f = frequency.getDec();

			double d = 0;
			double w = 0;
			double m = 0;
			double y = 0;

			if (radDaily.isChecked()) {
				d = c * f;
				w = d * 7;
				m = d * 30;
				y = d * 365;
			}
			else if (radWeekly.isChecked()) {
				w = c * f;
				d = w / 7;
				m = w * 4;
				y = w * 52;
			}
			else if (radMonthly.isChecked()) {
				m = c * f;
				d = m / 30;
				w = m / 4;
				y = m * 12;
			}
			else if (radYearly.isChecked()) {
				y = c * f;
				d = y / 365;
				w = y / 52;
				m = y / 12;
			}

			dailyAmount.setText(fmtrCur.format(d));
			weeklyAmount.setText(fmtrCur.format(w));
			monthlyAmount.setText(fmtrCur.format(m));
			yearlyAmount.setText(fmtrCur.format(y));
		}
		else {
			dailyAmount.clear();
			weeklyAmount.clear();
			monthlyAmount.clear();
			yearlyAmount.clear();
		}
	}
}
