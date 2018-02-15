package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class VideoStorage extends CalcFragment {

	private final NumberFormat fmtrDec;


	private EditDecimal space;
	private TextWrapper fourKSixty;
	private TextWrapper fourKThirty;
	private TextWrapper tenEighty;
	private TextWrapper sevenTwenty;


	private TextWrapper curTaxAmt, curTotal;


	public VideoStorage() {
		super();
		fmtrDec = NumberFormat.getInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {

		View view = inflater.inflate(R.layout.video_storage, container, false);

		space = new EditDecimal(view, R.id.space, this);


		fourKSixty = new TextWrapper(view, R.id.fourKSixty);
		fourKThirty = new TextWrapper(view, R.id.fourKThirty);
		tenEighty = new TextWrapper(view, R.id.tenEighty);
		sevenTwenty = new TextWrapper(view, R.id.sevenTwenty);

		EditWrapper[] inputs = { space };
		ControlWrapper[] toClear = { space, fourKSixty, fourKThirty, tenEighty, sevenTwenty };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		space.restore(prefs, fmtrDec);
	}


	@Override
	protected void compute() {
		if (space.notEmpty()) {
			double reSpace = space.getDec();
			double asMb = reSpace * 1024;
			double reFourKSixty = (asMb / 7.5) / 60;
			double reFourKThirty = (asMb / 5.83) / 60;
			double reTenEighty = (asMb / 2.92) / 60;
			double reSevenTwenty = (asMb / 1.0) / 60;


			double taxAmt = Consumer.Ratio.amount(taxRate, price);
			double total = Consumer.Ratio.total(taxRate, price);
			curTaxAmt.setText(fmtrCur.format(taxAmt));
			curTotal.setText(fmtrCur.format(total));
		}
		else {
			// If one or both of the inputs are empty, clear the outputs.
			curTaxAmt.clear();
			curTotal.clear();
		}
	}
}
