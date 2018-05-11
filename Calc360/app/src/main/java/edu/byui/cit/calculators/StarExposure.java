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
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;

import static edu.byui.cit.model.Art.StarExposure.calculateStarExposureLength;


public class StarExposure extends CalcFragment {
	// This is the formatter for any displayed decimals
	private final NumberFormat fmtrInt;

	// These hold the actual values
	private EditDecimal cropFacVal;
	private EditInteger focLengthVal;

	// These hold the views
	private TextWrapper starExpVal;


	public StarExposure() {
		super();
		fmtrInt = NumberFormat.getIntegerInstance();
	}

	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View ourView = inflater.inflate(R.layout.star_exposure, container,
				false);

		cropFacVal = new EditDecimal(ourView, R.id.crop_factor_entry, this);
		focLengthVal = new EditInteger(ourView, R.id.focal_length_entry, this);

		starExpVal = new TextWrapper(ourView, R.id.starExpValField);

		EditWrapper[] entries = { cropFacVal, focLengthVal };
		WidgetWrapper[] toClear = { starExpVal, cropFacVal, focLengthVal };
		initialize(ourView, entries, R.id.clear_button, toClear);
		return ourView;
	}

	@Override
	protected void compute(WidgetWrapper source) {
		if (cropFacVal.notEmpty() && focLengthVal.notEmpty()) {
			int focal = focLengthVal.getInt();
			double crop = cropFacVal.getDec();
			starExpVal.setText(fmtrInt.format(calculateStarExposureLength(focal,crop)));
		}
		else {
			starExpVal.clear();
		}
	}
}
