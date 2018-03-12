package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.RadioWrapper;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Mass;


public final class BasalMetabolicRate extends CalcFragment {
	private RadioWrapper radFemale;
	private EditDecimal decWeight, decHeight, decAge;
	private TextWrapper txtHeightUnit, txtWeightUnit, txtAge, txtBMR;
	private int[] ranges;
	private String[] categories;
	private final NumberFormat fmtrDec;

	public BasalMetabolicRate() {
		// Call the constructor in the parent class.
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMinimumFractionDigits(0);
		fmtrDec.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.basal_metabolic_rate, container,
				false);

		decHeight = new EditDecimal(view, R.id.decHeight, this);
		decWeight = new EditDecimal(view, R.id.decWeight, this);
		txtHeightUnit = new TextWrapper(view, R.id.heightUnit);
		txtWeightUnit = new TextWrapper(view, R.id.weightUnit);
		txtAge = new TextWrapper(view, R.id.decAge);
		txtBMR = new TextWrapper(view, R.id.decBMR);

		ranges = getResources().getIntArray(R.array.bmiRanges);
		categories = getResources().getStringArray(R.array.bmiCategories);

		RadioClick listener = new RadioClick();
		radFemale = new RadioWrapper(view, R.id.radImperial, listener);
		new RadioWrapper(view, R.id.radMetric, listener);
		radFemale.performClick();

		EditWrapper[] inputs = { decHeight, decWeight };
		ControlWrapper[] toClear = {
				decHeight, decWeight, txtAge, txtBMR
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	private final class RadioClick implements ClickListener {
		@Override
		public void clicked(View button) {
			Property length = Length.getInstance();
			Property mass = Mass.getInstance();
			boolean selected = ((RadioButton)button).isChecked();
			if (selected) {
				Unit unitLength, unitMass;
				switch (button.getId()) {
					case R.id.radImperial:
						unitLength = length.get(Length.inch);
						unitMass = mass.get(Mass.pound);
						break;
					default:  // R.id.radMetric:
						unitLength = length.get(Length.m);
						unitMass = mass.get(Mass.kilogram);
						break;
				}
				txtHeightUnit.setText(unitLength.getLocalName());
				txtWeightUnit.setText(unitMass.getLocalName());
			}
			callCompute();
		}
	}


	@Override
	protected void compute() {
		if (decHeight.notEmpty() && decWeight.notEmpty()) {
			double height = decHeight.getDec();
			double weight = decWeight.getDec();
			if (radFemale.isChecked()) {
				// Convert inches to meters and pounds to kilograms
				// before calculating the BMI.
				height = Length.getInstance().convert(
						Length.m, height, Length.inch);
				weight = Mass.getInstance().convert(
						Mass.kilogram, weight, Mass.pound);
			}

			// Calculate the BMR.
			double bmr = weight / (height * height);

			// Display the results to the user.
			txtBMR.setText(fmtrDec.format(bmr));
		}
		else {
			txtBMR.clear();
		}
	}
}
