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
		decAge = new EditDecimal(view, R.id.decAge, this);
		txtAge = new TextWrapper(view, R.id.txtAge);
		txtBMR = new TextWrapper(view, R.id.txtBMR);

		RadioClick listener = new RadioClick();
		radFemale = new RadioWrapper(view, R.id.radFemale, listener);
		new RadioWrapper(view, R.id.radMetric, listener);
		radFemale.performClick();

		EditWrapper[] inputs = { decHeight, decWeight, decAge };
		ControlWrapper[] toClear = {
				decHeight, decWeight, decAge, txtBMR
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
		if (decHeight.notEmpty() && decWeight.notEmpty() && decAge.notEmpty()) {
			double height = decHeight.getDec();
			double weight = decWeight.getDec();
			double age = decAge.getDec();
			if (radFemale.isChecked()) {
				//Female BMR calculation
				double BMR = 10 * (weight / 2.2) + 6.25 * (height * 2.54) - 5 * (age) - 162;
			}

			// Male BMR calculation
			double BMR = 10 * (weight / 2.2) + 6.25 * (height * 2.54) - 5 * (age) + 4;
		}
	}
}
