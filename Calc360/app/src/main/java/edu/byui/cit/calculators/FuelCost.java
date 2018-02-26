package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCurrency;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.FuelEcon;
import edu.byui.cit.units.Length;
import edu.byui.cit.units.Volume;


public final class FuelCost extends CalcFragment {
	private static final String
			KEY_DIST_UNITS = "FuelCost.distUnits",
			KEY_EFFIC_UNITS = "FuelCost.efficUnits",
			KEY_VOL_UNITS = "FuelCost.volUnits";

	private final NumberFormat fmtrCur = NumberFormat.getCurrencyInstance();
	private EditDecimal decDist;
	private EditDecimal decEffic;
	private EditCurrency curPrice;
	private SpinUnit spinDistUnits;
	private SpinUnit spinEfficUnits;
	private SpinUnit spinVolUnits;
	private TextWrapper curFuelCost;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fuel_cost, container, false);

		// Initialize inputs
		decDist = new EditDecimal(view, R.id.decDist, this);
		decEffic = new EditDecimal(view, R.id.decEcon, this);
		curPrice = new EditCurrency(view, R.id.curPrice, this);

		Activity act = getActivity();
		spinDistUnits = new SpinUnit(act, view, R.id.spinDistUnits,
				Length.getInstance(), R.array.feDistUnits,
				KEY_DIST_UNITS, this);
		spinEfficUnits = new SpinUnit(act, view, R.id.spinEconUnits,
				FuelEcon.getInstance(), R.array.feEconUnits,
				KEY_EFFIC_UNITS, this);
		spinVolUnits = new SpinUnit(act, view, R.id.spinVolUnits,
				Volume.getInstance(), R.array.feVolUnits,
				KEY_VOL_UNITS, this);

		curFuelCost = new TextWrapper(view, R.id.curFuelCost);

		EditWrapper[] inputs = { decDist, decEffic, curPrice };
		ControlWrapper[] toClear = {
				decDist, decEffic, curPrice, curFuelCost
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Restore the user selected units from the preferences file.
		spinDistUnits.restore(
				prefs, spinDistUnits.getItemAtPosition(0).getID());
		spinEfficUnits.restore(
				prefs, spinEfficUnits.getItemAtPosition(0).getID());
		spinVolUnits.restore(prefs, spinVolUnits.getItemAtPosition(0).getID());
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the user selected units into the preferences file.
		spinDistUnits.save(editor);
		spinEfficUnits.save(editor);
		spinVolUnits.save(editor);
	}


	@Override
	protected void compute() {
		String output = null;
		if (EditWrapper.allNotEmpty(decDist, decEffic, curPrice)) {
			double dist = decDist.getDec();
			double effic = decEffic.getDec();
			double price = 1.0 / curPrice.getCur();
			dist = Length.getInstance().convert(
					Length.km, dist, spinDistUnits.getSelectedItem());
			effic = FuelEcon.getInstance().convert(
					FuelEcon.kpl, effic, spinEfficUnits.getSelectedItem());
			price = Volume.getInstance().convert(
					Volume.liter, price, spinVolUnits.getSelectedItem());
			double cost = dist / (effic * price);
			output = fmtrCur.format(cost);
		}
		curFuelCost.setText(output);
	}
}
