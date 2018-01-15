package edu.byui.cit.calculators;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Mass;


public final class BodyMassIndex extends CalcFragment {
	private EditDec decWeight, decHeight;
	private TextWrapper textResult, textAdvice, textHeightUnit, textWeightUnit;
	private RadioWrapper radImperial;
	private final NumberFormat fmtrBMI;

	public BodyMassIndex() {
		// Call the parent constructor
		super();

		fmtrBMI = NumberFormat.getInstance();
		fmtrBMI.setMinimumFractionDigits(0);
		fmtrBMI.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.body_mass_index, container,
				false);

		decHeight = new EditDec(view, R.id.decHeight, this);
		decWeight = new EditDec(view, R.id.decWeight, this);
		textResult = new TextWrapper(view, R.id.result);
		textAdvice = new TextWrapper(view, R.id.advice);
		textHeightUnit = new TextWrapper(view, R.id.heightUnit);
		textWeightUnit = new TextWrapper(view, R.id.weightUnit);

		RadioClick listener = new RadioClick();
		radImperial = new RadioWrapper(view, R.id.radImperial, listener);
		new RadioWrapper(view, R.id.radMetric, listener);
		radImperial.performClick();

		// Set this calculator as the click listener for the clear button.
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	private final class RadioClick implements OnClickListener {
		//handles radio button clicks
		public void onClick(View button) {
			Property length = Length.getInstance();
			Property mass = Mass.getInstance();
			boolean selected = ((RadioButton)button).isChecked();
			if (selected) {
				Unit unitLength, unitMass;
				switch (button.getId()) {
					//Radio Button Check Logic
					case R.id.radImperial:
						unitLength = length.get(Length.inch);
						unitMass = mass.get(Mass.pound);
						break;
					default:  // R.id.radMetric:
						unitLength = length.get(Length.m);
						unitMass = mass.get(Mass.kilogram);
						break;
				}
				textHeightUnit.setText(unitLength.getName());
				textWeightUnit.setText(unitMass.getName());
			}
			compute();
		}
	}


	@Override
	protected void compute() {
		try {
			if (decHeight.notEmpty() && decWeight.notEmpty()) {
				double height, weight;
				if (radImperial.isChecked()) {
					Property length = Length.getInstance();
					Property mass = Mass.getInstance();

					//covert inches to meters for the needed math
					height = length.convert(Length.m, decHeight.getDec(),
							Length.inch);
					weight = mass.convert(Mass.kilogram, decWeight
									.getDec(),
							Mass.pound);
				}
				else {
					height = decHeight.getDec();
					weight = decWeight.getDec();
				}

				//Do the math to calculate the bmi
				double bmi = weight / Math.pow(height, 2);
				String strBMI = fmtrBMI.format(bmi);

				//display the results to the user
				String bmiLabel = getString(
						R.string.yourBmi) + " " + strBMI;
				textResult.setText(bmiLabel);
				// This if statement will show you if
				// your BMI is low, normal, or high
				int advice;
				if (bmi < 18) {
					advice = R.string.bmiLow;
				}
				else if (bmi < 24) {
					advice = R.string.bmiNormal;
				}
				else {
					advice = R.string.bmiHigh;
				}
				textAdvice.setText(getString(advice));
			}
			else {
				textResult.clear();
				textAdvice.clear();
			}
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception");
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			decHeight.clear();
			decWeight.clear();
			textResult.clear();
			textAdvice.clear();
			decHeight.requestFocus();
		}
	}
}
