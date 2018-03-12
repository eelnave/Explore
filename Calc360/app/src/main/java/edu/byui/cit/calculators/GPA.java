package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinString;


public final class GPA extends CalcFragment {

	private EditDecimal decCurrGpa;
	private EditDecimal decCurrCred;
	private SpinString spinGrade;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gpa_calc, container,
				false);

		EditWrapper[] inputs = { };

		return view;
	}
}
