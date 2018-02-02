package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.InfoFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.TextWrapper;


public final class Counter extends InfoFragment {
	private final NumberFormat fmtrInt = NumberFormat.getIntegerInstance();
	private int count;
	private TextWrapper intCount;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.counter, container, false);

		intCount = new TextWrapper(view, R.id.intCount);
		intCount.setText(fmtrInt.format(count));

		new ButtonWrapper(view, R.id.btnPlusOne, new Plus(1));
		new ButtonWrapper(view, R.id.btnPlusTwo, new Plus(2));
		new ButtonWrapper(view, R.id.btnPlusThree, new Plus(3));
		new ButtonWrapper(view, R.id.btnPlusFive, new Plus(5));

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	private final class Plus implements ClickListener {
		private final int quant;

		Plus(int quant) {
			this.quant = quant;
		}

		@Override
		public void clicked(View button) {
			count += quant;
			intCount.setText(fmtrInt.format(count));
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			count = 0;
			intCount.setText(fmtrInt.format(count));
		}
	}
}
