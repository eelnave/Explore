package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Volume;


public class RecipeMultiplier extends CalcFragment {
	private static final String
			KEY_MULT = "RecipeMult.mult",
			KEY_VOL_UNITS_FROM = "RecipeMult.volUnitsFrom",
			KEY_VOL_UNITS_TO = "RecipeMult.volUnitsTo";

	private final Volume volume = Volume.getInstance();
	private final NumberFormat fmtrDec;
	private EditDec multIn, startAmount;
	private SpinString multType;
	private TextWrapper multOut, endAmount;
	private SpinUnit startUnits, endUnits;


	public RecipeMultiplier() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMaximumFractionDigits(2);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.recipe_multiplier, container,
				false);

		multIn = new EditDec(view, R.id.multIn, this);
		multType = new SpinString(view, R.id.multType, KEY_MULT, this);
		multOut = new TextWrapper(view, R.id.multOut);

		Activity act = getActivity();
		startUnits = new SpinUnit(act, view, R.id.startUnit, volume,
				R.array.kitchenVolume, KEY_VOL_UNITS_FROM, this);
		endUnits = new SpinUnit(act, view, R.id.endUnit, volume,
				R.array.kitchenVolume, KEY_VOL_UNITS_TO, this);
		startAmount = new EditDec(view, R.id.startAmount, this);
		endAmount = new TextWrapper(view, R.id.endAmount);

		// Restore units that were previously selected by the user.
		SharedPreferences prefs = getActivity().getPreferences(
				Context.MODE_PRIVATE);
		multType.restore(prefs, multType.getItemAtPosition(0));
		startUnits.restore(prefs, Volume.tsp);
		endUnits.restore(prefs, Volume.tbsp);

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}

	@Override
	public void afterTextChanged(Editable editable) {
		if (multIn.hasFocus() && multIn.notEmpty()) {
			callCompute();
			startAmount.clear();
			endAmount.clear();
		}
		else if (startAmount.hasFocus() && startAmount.notEmpty()) {
			compute2();
			multIn.clear();
			multOut.clear();
		}
	}

	@Override
	public void itemSelected(
			AdapterView<?> adapterView, View view, int i, long l) {
		if (multIn.notEmpty()) {
			callCompute();
			startAmount.clear();
			endAmount.clear();
		}
		else if (startAmount.notEmpty()) {
			compute2();
			multIn.clear();
			multOut.clear();
		}
	}

	@Override
	protected void compute() {
		double output;
		if (multType.getSelectedItem().equals("1/4")) {
			output = multIn.getDec() * 0.25;
		}
		else if (multType.getSelectedItem().equals("1/2")) {
			output = multIn.getDec() * 0.5;
		}
		else if (multType.getSelectedItem().equals("2X")) {
			output = multIn.getDec() * 2;
		}
		else {
			output = multIn.getDec() * 3;
		}
		multOut.setText(fmtrDec.format(output));
	}

	private void compute2() {
		try {
			int fromUnit = startUnits.getSelectedItem().getID();
			int toUnit = endUnits.getSelectedItem().getID();
			double orig = startAmount.getDec();
			double output = volume.convert(toUnit, orig, fromUnit);
			endAmount.setText(fmtrDec.format(output));
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			multIn.clear();
			multOut.clear();
			multIn.requestFocus();
			multType.setSelection(2);
			startAmount.clear();
			startUnits.setSelection(0);
			endUnits.setSelection(0);
			endAmount.clear();
			multIn.requestFocus();
		}
	}


	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Save the ID of the units chosen by
		// the user into the preferences file.
		multType.save(editor);
		startUnits.save(editor);
		endUnits.save(editor);
	}
}
