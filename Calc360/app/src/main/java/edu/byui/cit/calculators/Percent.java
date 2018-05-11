package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.widget.WidgetWrapper;

import static edu.byui.cit.model.Consumer.Ratio.*;


public final class Percent extends CalcFragment {
	private final NumberFormat fmtrDec = NumberFormat.getNumberInstance();
	private EditDecimal decPerc1, decPerc2, decPerc3, decPerc4, decPerc5, decPerc6;
	private TextWrapper percResult1, percResult2, percResult3;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.percent, container, false);

		// Get a reference to each of the text fields in this calculator.
		decPerc1 = new EditDecimal(view, R.id.decPerc1, this);
		decPerc2 = new EditDecimal(view, R.id.decPerc2, this);
		decPerc3 = new EditDecimal(view, R.id.decPerc3, this);
		decPerc4 = new EditDecimal(view, R.id.decPerc4, this);
		decPerc5 = new EditDecimal(view, R.id.decPerc5, this);
		decPerc6 = new EditDecimal(view, R.id.decPerc6, this);

		// Get a reference to the text views
		// where the results will be displayed.
		percResult1 = new TextWrapper(view, R.id.percResult1);
		percResult2 = new TextWrapper(view, R.id.percResult2);
		percResult3 = new TextWrapper(view, R.id.percResult3);

		// Set the click listener for each clear button.
		new ButtonWrapper(view, R.id.btnClear1, new ClearHandler1());
		new ButtonWrapper(view, R.id.btnClear2, new ClearHandler2());
		new ButtonWrapper(view, R.id.btnClear3, new ClearHandler3());

		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		// Check which text field was edited and
		// perform the calculations accordingly.
		if (decPerc1.hasFocus() || decPerc2.hasFocus()) {
			if (decPerc1.notEmpty() && decPerc2.notEmpty()) {
				// Calculates the percentage of a given number.
				// Ex: 20% of 100 is 20
				double x = decPerc1.getDec() / 100.0;
				double y = decPerc2.getDec();
				double result = x * y;
				percResult1.setText(fmtrDec.format(result));
			}
			else {
				percResult1.clear();
			}
		}
		else if (decPerc3.hasFocus() || decPerc4.hasFocus()) {
			if (decPerc3.notEmpty() && decPerc4.notEmpty()) {
				// Calculates what percent a given number is
				// of another number. Ex: 20 of 100 is 20%
				double x = decPerc3.getDec();
				double y = decPerc4.getDec();
				double perc = x / y;
				String result = fmtrDec.format(perc * 100.0) + "%";
				percResult2.setText(result);
			}
			else {
				percResult2.clear();
			}
		}
		else if (decPerc5.hasFocus() || decPerc6.hasFocus()) {
			if (decPerc5.notEmpty() && decPerc6.notEmpty()) {
				// Calculates the percentage change between two numbers.
				// Ex: from 20 to 100, there is an increase of 400%.
				double x = decPerc5.getDec();
				double y = decPerc6.getDec();
				double perc = rateChange(x, y);
				String result = fmtrDec.format(perc * 100.0) + "%";
				percResult3.setText(result);
			}
			else {
				percResult3.clear();
			}
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler1 implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			decPerc1.clear();
			decPerc2.clear();
			percResult1.clear();
			decPerc1.requestFocus();
		}
	}

	private final class ClearHandler2 implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			decPerc3.clear();
			decPerc4.clear();
			percResult2.clear();
			decPerc3.requestFocus();
		}
	}

	private final class ClearHandler3 implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			decPerc5.clear();
			decPerc6.clear();
			percResult3.clear();
			decPerc5.requestFocus();
		}
	}
}
