package edu.byui.cit.calculators;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;

public class FourFunction extends CalcFragment{
	private final NumberFormat fmtrNum = NumberFormat.getNumberInstance();
	private EditInt intMulti1;
	private EditInt intMulti2;
	private TextWrapper intTotal;

	public FourFunction() {
		super();

	}

	//create view and initialize interface
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.four_function, container, false);

		// Initialize inputs
		intMulti1 = new EditInt(view, R.id.intMulti1, this);
		intMulti2 = new EditInt(view, R.id.intMulti2, this);
		intTotal = new TextWrapper(view, R.id.intTotal);

		EditWrapper[] inputs = { intMulti1, intMulti2 };
		ControlWrapper[] toClear = { intMulti1, intMulti2, intTotal };
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}

	@Override
	protected void compute() {
		if (intMulti1.notEmpty() && intMulti2.notEmpty()) {
			double first = intMulti1.getInt();
			double second = intMulti2.getInt();
			double total = first * second;
			intTotal.setText(fmtrNum.format(total));
		}
		else {
			intTotal.clear();
		}
	}



}