package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class Netflix extends CalcFragment{
	private final NumberFormat fmtNum, fmtCur;
	private EditDecimal ssp, hours1, hours2, hours3, minutes1, minutes2;
	private TextWrapper ttw, spentPer;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.netflix, container, false);

//		this layout of stuff : decimal1 = new EditDecimal(view, R.id.decimal1, this);
		ssp = new EditDecimal(view, R.id.ssp, this);
		hours1 = new EditDecimal(view, R.id.hours1, this);
		hours2 = new EditDecimal(view, R.id.hours2, this);
		hours3 = new EditDecimal(view, R.id.hours3, this);
		minutes1 = new EditDecimal(view, R.id.minutes1, this);
		minutes2 = new EditDecimal(view, R.id.minutes2, this);

//		result
		spentPer = new TextWrapper(view, R.id.spentPer);
		ttw = new TextWrapper(view, R.id.ttw);

//		initialize the calculator
		EditWrapper[] inputs = { ssp, hours1, hours2, hours3, minutes1, minutes2 };

//		clears the button
		ControlWrapper[] toClear = { ssp, hours1, hours2, hours3, minutes1, minutes2, ttw, spentPer };
		initialize(view, inputs, R.id.btnClear, toClear);

		return view;
	}

	public Netflix(){
		super();
//		get instance
		fmtNum = NumberFormat.getInstance();
		fmtCur = NumberFormat.getCurrencyInstance();
	}

	@Override
	protected void compute(){
		double price = 0;
		double firstHour = 0;
		double secondHour = 0;
		double thirdHour = 0;
		double firstMin = 0;
		double secondMin = 0;

		if (ssp.notEmpty() || hours1.notEmpty() || hours3.notEmpty() || minutes1.isEmpty() || minutes2.isEmpty()) {
			if (ssp.notEmpty()) {
				price = ssp.getDec();
			}
			if (hours1.notEmpty()) {
				firstHour = hours1.getDec() * 2.5;
			}
			if (hours2.notEmpty()) {
				secondHour = hours2.getDec() * 2;
			}
			if (hours3.notEmpty()) {
				thirdHour = hours3.getDec() * 1.5;
			}
			if (minutes1.notEmpty()) {
				firstMin = minutes1.getDec() * .75;
			}
			if (minutes2.notEmpty()) {
				secondMin = minutes2.getDec() * .37;
			}

//			get the total
			double time = firstHour + secondHour + thirdHour + firstMin + secondMin;
			double sum = time / price;

//			result
			ttw.setText(fmtNum.format(time));
			spentPer.setText(fmtCur.format(sum));
		}else{
			spentPer.clear();
			ttw.clear();
		}
	}
}
