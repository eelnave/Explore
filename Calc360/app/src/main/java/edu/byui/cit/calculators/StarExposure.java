package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public class StarExposure extends CalcFragment {

	private final int fiveHundredRuleNum = 500;

	// This is the formatter for any displayed decimals
	private final NumberFormat decFrmt;

	// These hold the views
	private TextWrapper starExpVal;

	// These hold the actual values
	private EditDecimal cropFacVal;
	private EditInteger focLengthVal;


	public StarExposure(){
		super();
		decFrmt = NumberFormat.getInstance();
	}

	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View ourView = inflater.inflate(R.layout.star_exposure, container, false);

		starExpVal = new TextWrapper(ourView, R.id.starExpValField);

		cropFacVal = new EditDecimal(ourView, R.id.focal_entry_id , this);
		focLengthVal = new EditInteger(ourView, R.id.crop_factor_entry, this);

		EditWrapper[] entries = {cropFacVal, focLengthVal};
		ControlWrapper[] toClear = { starExpVal, cropFacVal, focLengthVal };
		initialize(ourView, entries,R.id.clear_button, toClear);
		return ourView;
	}


	@Override
	protected void compute() {
		if (cropFacVal.notEmpty() && focLengthVal.notEmpty()) {
			starExpVal.setText(decFrmt.format(fiveHundredRuleNum / (cropFacVal.getDec() * focLengthVal.getInt())));
		}
		else {
			starExpVal.clear();
		}
	}
}
