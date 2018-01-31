package edu.byui.cit.calculators;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.TextWrapper;


/**
 * Created by GavinrRook on 1/30/18.
 */

public class FourFunction extends CalcFragment{
	private NumberFormat;
	private EditInt intMulti1;
	private EditInt intMulti2;

	//create view and initialize interface
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.four_function, container, false);

		// Initialize inputs
		intMulti1 = new EditInt(view, R.id.intMulti1, this);
		intMulti2 = new EditInt(view, R.id.intMulti2, this);

		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinEfficUnits = new SpinUnit(act, view, R.id.spinEfficUnits,
				FuelEffic.getInstance(), R.array.feEfficUnits,
				KEY_EFFIC_UNITS, this);
		spinVolUnits = new SpinUnit(act, view, R.id.spinVolUnits,
				Volume.getInstance(), R.array.feVolUnits,
				KEY_VOL_UNITS, this);

		curFuelCost = new TextWrapper(view, R.id.curFuelCost);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}

}
