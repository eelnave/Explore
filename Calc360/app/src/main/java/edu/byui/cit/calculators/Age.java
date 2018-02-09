package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.text.NumberFormat;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;

public final class Age extends CalcFragment {
	private final NumberFormat fmtrDec;

	private EditDec numberOne;
	private EditDec numberTwo;
	private EditDec numberThree;
	private TextWrapper subtractedTotal;


	public Age() {
		// Call constructor
		super();

		fmtrDec = NumberFormat.getInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.age, container, false);

		// Reference each of the text fields
		numberOne = new EditDec(view, R.id.numberOne, this);
		numberTwo = new EditDec(view, R.id.numberTwo, this);
		subtractedTotal = new TextWrapper(view, R.id.subtractedTotal);

		EditWrapper[] inputs = { numberOne, numberTwo };
		ControlWrapper[] toClear = { numberOne, numberTwo, subtractedTotal };
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}

	@Override
	protected void compute() {
		if (numberOne.notEmpty() && numberTwo.notEmpty()){
			double numerialOne = numberOne.getDec();
			double numerialTwo = numberTwo.getDec();

			double total = numerialOne - numerialTwo;

			subtractedTotal.setText(fmtrDec.format(total));
		}
		else {
			numberOne.clear();
			numberTwo.clear();
			subtractedTotal.clear();
		}
	}
}

