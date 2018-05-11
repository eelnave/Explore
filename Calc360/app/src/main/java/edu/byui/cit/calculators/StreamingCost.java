package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;

import static edu.byui.cit.model.Consumer.StreamingCost.*;


public final class StreamingCost extends CalcFragment {
	private final NumberFormat fmtrNum, fmtrCur;
	private EditDecimal curPayment, decHours2_5, decHours2, decHours1_5, decMins45, decMins22;
	private EditWrapper[] timeInputs;
	private TextWrapper ttw, spentPer;

	public StreamingCost() {
		super();
		fmtrNum = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.streaming_cost, container, false);

		curPayment = new EditDecimal(view, R.id.ssp, this);
		decHours2_5 = new EditDecimal(view, R.id.hours2_5, this);
		decHours2 = new EditDecimal(view, R.id.hours2, this);
		decHours1_5 = new EditDecimal(view, R.id.hours1_5, this);
		decMins45 = new EditDecimal(view, R.id.minutes45, this);
		decMins22 = new EditDecimal(view, R.id.minutes22, this);

		// result
		ttw = new TextWrapper(view, R.id.ttw);
		spentPer = new TextWrapper(view, R.id.spentPer);

		// initialize the calculator
		EditWrapper[] inputs = {
				curPayment, decHours2_5, decHours2, decHours1_5, decMins45, decMins22
		};
		timeInputs = new EditWrapper[]{
				decHours2_5, decHours2, decHours1_5, decMins45, decMins22
		};
		WidgetWrapper[] toClear = {
				curPayment, decHours2_5, decHours2, decHours1_5, decMins45, decMins22, ttw, spentPer
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		if (curPayment.notEmpty() && EditWrapper.anyNotEmpty(timeInputs)) {
			double payment = 0;
			double hours2_5 = 0;
			double hours2 = 0;
			double hours1_5 = 0;
			double mins45 = 0;
			double mins22 = 0;

			if (curPayment.notEmpty()) {
				payment = curPayment.getDec();
			}
			if (decHours2_5.notEmpty()) {
				hours2_5 = durationMovie2_5(decHours2_5.getDec());
			}
			if (decHours2.notEmpty()) {
				hours2 = durationMovie2(decHours2.getDec());
			}
			if (decHours1_5.notEmpty()) {
				hours1_5 = durationMovie1_5(decHours1_5.getDec());
			}
			if (decMins45.notEmpty()) {
				mins45 = durationEpisode45(decMins45.getDec());
			}
			if (decMins22.notEmpty()) {
				mins22 = durationEpisode22(decMins22.getDec());
			}

			// get the total time in hours
			double total = hours2_5 + hours2 + hours1_5 + mins45 + mins22;

			// compute the cost per hour
			double perHour = payment / total;

			// display the result
			ttw.setText(fmtrNum.format(total));
			spentPer.setText(fmtrCur.format(perHour));
		}
		else {
			spentPer.clear();
			ttw.clear();
		}
	}
}
