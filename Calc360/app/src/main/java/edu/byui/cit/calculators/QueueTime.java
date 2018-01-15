package edu.byui.cit.calculators;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.TextWrapper;


public final class QueueTime extends CalcFragment {
	private final DateFormat fmtrDate;
	private final NumberFormat fmtrInt;
	private EditInt intPeople;
	private TextWrapper timAvg, timRemain, timServed;
	private ButtonWrapper btnNext;
	private double sum;
	private long prevClick = 0;
	private int people, count = 0;

	public QueueTime() {
		super();
		fmtrDate = DateFormat.getTimeInstance(DateFormat.MEDIUM);
		fmtrInt = NumberFormat.getIntegerInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.queue_time, container, false);

		intPeople = new EditInt(view, R.id.intPeople, this);
		timAvg = new TextWrapper(view, R.id.timAvg);
		timRemain = new TextWrapper(view, R.id.timRemain);
		timServed = new TextWrapper(view, R.id.timServed);

		btnNext = new ButtonWrapper(view, R.id.btnNext, this);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	public void afterTextChanged(Editable s) {
		try {
			prevClick = System.currentTimeMillis();
			sum = 0;
			count = 0;
			people = 0;
			if (intPeople.notEmpty()) {
				people = intPeople.getInt();
				if (people > 0) {
					btnNext.setEnabled(true);
				}
			}
			if (people == 0) {
				clearResults();
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	@Override
	protected void compute() {
		long click = System.currentTimeMillis();
		if (intPeople.isEnabled()) {
			intPeople.setEnabled(false);
		}

		long elapsed = click - prevClick;
		prevClick = click;
		sum += elapsed;
		++count;
		int peopleRem = people - count;
		if (peopleRem == 0) {
			btnNext.setEnabled(false);
		}
		double mean = sum / count;
		double millisRem = peopleRem * mean;
		long ets = click + Math.round(millisRem);

		intPeople.setText(fmtrInt.format(peopleRem));
		timAvg.setText(formatTimeInterval(mean));
		timRemain.setText(formatTimeInterval(millisRem));
		timServed.setText(fmtrDate.format(new Date(ets)));
	}

	private static String formatTimeInterval(double millis) {
		long t = Math.round(millis / 1000.0);
		long secs = t % 60;
		t /= 60;
		long mins = t % 60;
		t /= 60;
		long hours = t;
		return String.format(Locale.getDefault(),
			"%02d:%02d:%02d", hours, mins, secs);
	}


	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			prevClick = 0;
			people = 0;
			count = 0;
			sum = 0;
			clearResults();
			intPeople.clear();
			intPeople.setEnabled(true);
			intPeople.requestFocus();
		}
	}

	private void clearResults() {
		btnNext.setEnabled(false);
		timAvg.clear();
		timRemain.clear();
		timServed.clear();
	}
}
