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
import edu.byui.cit.widget.RadioWrapper;
import edu.byui.cit.widget.TextWrapper;

public final class BasalMetabolicRate extends CalcFragment {
	private RadioWrapper radFemale;
	private EditDecimal decWeight, decHeight, decAge;
	private TextWrapper txtBMR;
	private final NumberFormat fmtrDec;

	public BasalMetabolicRate() {
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMinimumFractionDigits(0);
		fmtrDec.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.basal_metabolic_rate, container,
				false);

		decHeight = new EditDecimal(view, R.id.decHeight, this);
		decWeight = new EditDecimal(view, R.id.decWeight, this);
		decAge = new EditDecimal(view, R.id.decAge, this);
		txtBMR = new TextWrapper(view, R.id.txtBMR);

		radFemale = new RadioWrapper(view, R.id.radFemale, this);
		new RadioWrapper(view, R.id.radMale, this);
		radFemale.performClick();

		EditWrapper[] inputs = { decHeight, decWeight, decAge };
		WidgetWrapper[] toClear = {
				decHeight, decWeight, decAge, txtBMR
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	protected void compute(WidgetWrapper source) {
		if (decHeight.notEmpty() && decWeight.notEmpty() && decAge.notEmpty()) {
			double height = decHeight.getDec();
			double weight = decWeight.getDec();
			double age = decAge.getDec();
			if (radFemale.isChecked()) {
				double BMR = 10 * (weight / 2.2) + 6.25 * (height * 2.54) - 5 * (age) - 162;
				txtBMR.setText(fmtrDec.format(BMR));
			}else {
				double BMR = 10 * (weight / 2.2) + 6.25 * (height * 2.54) - 5 * (age) + 4;
				txtBMR.setText(fmtrDec.format(BMR));
			}
		}
		else{
			txtBMR.clear();
		}
	}
}
