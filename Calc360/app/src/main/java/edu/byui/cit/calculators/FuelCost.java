package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.FuelEffic;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Volume;

import static edu.byui.cit.text.Input.allNotEmpty;


public final class FuelCost extends CalcFragment {
	private static final String
			KEY_DIST_UNITS = "FuelCost.distUnits",
			KEY_EFFIC_UNITS = "FuelCost.efficUnits",
			KEY_VOL_UNITS = "FuelCost.volUnits";

	private final NumberFormat fmtrCur = NumberFormat.getCurrencyInstance();
	private EditDec decDist;
	private EditDec decEffic;
	private EditCur curPrice;
	private SpinUnit spinDistUnits;
	private SpinUnit spinEfficUnits;
	private SpinUnit spinVolUnits;
	private TextWrapper curFuelCost;


	//create view and initialize interface
	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fuel_cost, container, false);

		// Initialize inputs
		decDist = new EditDec(view, R.id.decDist, this);
		decEffic = new EditDec(view, R.id.decEffic, this);
		curPrice = new EditCur(view, R.id.curPrice, this);

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

		// Restore the user selected units from the preferences file.
		SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
		spinDistUnits.restore(prefs, spinDistUnits.getItemAtPosition(0).getID());
		spinEfficUnits.restore(prefs, spinEfficUnits.getItemAtPosition(0).getID());
		spinVolUnits.restore(prefs, spinVolUnits.getItemAtPosition(0).getID());

		curFuelCost = new TextWrapper(view, R.id.curFuelCost);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	protected void compute() {
		String output = null;
		if (allNotEmpty(decDist, decEffic, curPrice)) {
			double dist = decDist.getDec();
			double effic = decEffic.getDec();
			double price = 1.0 / curPrice.getCur();
			dist = Length.getInstance().convert(
					Length.km, dist, spinDistUnits.getSelectedItem());
			effic = FuelEffic.getInstance().convert(
					FuelEffic.kpl, effic, spinEfficUnits.getSelectedItem());
			price = Volume.getInstance().convert(
					Volume.liter, price, spinVolUnits.getSelectedItem());
			double cost = dist / (effic * price);
			output = fmtrCur.format(cost);
		}
		curFuelCost.setText(output);
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the user selected units into the preferences file.
		spinDistUnits.save(editor);
		spinEfficUnits.save(editor);
		spinVolUnits.save(editor);
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			decDist.clear();
			decEffic.clear();
			curPrice.clear();
			curFuelCost.clear();
			decDist.requestFocus();
		}
	}
}
