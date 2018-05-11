package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import java.util.Calendar;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.DateWrapper;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;


public final class Age extends CalcFragment {
	private final NumberFormat fmtrInt;

	private DateWrapper pickBirth;
	private TextWrapper exactYears;
	private TextWrapper exactMonths;
	private TextWrapper exactDays;


	public Age() {
		// Call the constructor in the parent class.
		super();

		fmtrInt = NumberFormat.getIntegerInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.age, container, false);

		Calendar calendar = Calendar.getInstance();
		pickBirth = new DateWrapper(view, R.id.birthDate, calendar, this);
		exactYears = new TextWrapper(view, R.id.exactYears);
		exactMonths = new TextWrapper(view, R.id.exactMonths);
		exactDays = new TextWrapper(view, R.id.exactDays);

		EditWrapper[] inputs = {};
		WidgetWrapper[] toClear = { exactYears, exactMonths, exactDays };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		Calendar now = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.set(pickBirth.getYear(), pickBirth.getMonth(),
				pickBirth.getDayOfMonth());

		if (now.before(birth)) {
			Calendar swap = now;
			now = birth;
			birth = swap;
		}

		int years = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		int months = now.get(Calendar.MONTH) - birth.get(Calendar.MONTH);
		int days = now.get(Calendar.DAY_OF_MONTH) - birth.get(Calendar.DAY_OF_MONTH);

		Calendar prev;
		if (months < 0) {
			prev = Calendar.getInstance();
			prev.add(Calendar.YEAR, -1);
			months = prev.getActualMaximum(Calendar.MONTH) + 1 + months;
			years--;
		}

		if (days < 0) {
			prev = Calendar.getInstance();
			prev.add(Calendar.MONTH, -1);

			days = prev.getActualMaximum(Calendar.DAY_OF_MONTH) + days;
			if (--months < 0) {
				prev = Calendar.getInstance();
				prev.add(Calendar.YEAR, -1);
				months = prev.getActualMaximum(Calendar.MONTH) + 1 + months;
				years--;
			}
		}

		exactYears.setText(fmtrInt.format(years));
		exactMonths.setText(fmtrInt.format(months));
		exactDays.setText(fmtrInt.format(days));
	}
}
