package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.ItemSelectedHandler;
import edu.byui.cit.text.SpinProperty;
import edu.byui.cit.text.SpinString;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public class RecipeMultiplier extends CalcFragment {
	private static final String
			KEY_PREFIX = "RecipeMult",
			KEY_MULT = KEY_PREFIX + ".mult",
			KEY_PROP = KEY_PREFIX + ".prop",
			KEY_START = "start",
			KEY_END = "end";

	private static final int[] unitArrayIDs = {
			R.array.kitchenLength, R.array.kitchenArea,
			R.array.kitchenVolume, R.array.kitchenMass
	};
	private static final float[] ratios = { 0.25F, 0.5F, 2, 3, 4 };

	private final NumberFormat fmtrDec;
	private SpinProperty spinProp;
	private Property propCurrent;
	private SpinUnit spinStart, spinEnd;
	private EditDec decOrigAmt;
	private SpinString spinMult;
	private TextWrapper decResult;

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

		spinProp = new SpinProperty(getActivity(), view, R.id.spinProp,
				R.array.kitchenProperties, KEY_PROP, new ChangeProperty());
		spinStart = new SpinUnit(view, R.id.spinStart, this);
		spinEnd = new SpinUnit(view, R.id.spinEnd, this);

		decOrigAmt = new EditDec(view, R.id.decOrigAmt, this);
		spinMult = new SpinString(view, R.id.spinMult, KEY_MULT, this);
		decResult = new TextWrapper(view, R.id.decResult);

		EditWrapper[] inputs = { decOrigAmt };
		ControlWrapper[] toClear = { decOrigAmt, decResult };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Restore the property that the user was
		// using during the most recent session.
		int deflt = spinProp.getItemAtPosition(0).getID();
		spinProp.restore(prefs, deflt);
		initUnits();
		restoreUnits(prefs);

		spinMult.restore(prefs, 2);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the user selected property into the preferences file.
		spinProp.save(editor);

		// Write the user selected units into the preference file.
		saveUnits(editor);

		spinMult.save(editor);
	}


	private final class ChangeProperty extends ItemSelectedHandler {
		@Override
		public void itemSelected(
				AdapterView<?> parent, View view, int pos, long id) {
			Activity act = getActivity();
			SharedPreferences prefs = act.getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			saveUnits(editor);
			editor.apply();
			initUnits();
			restoreUnits(prefs);
			callCompute();
		}
	}


	private void saveUnits(SharedPreferences.Editor editor) {
		// Write into the preferences file the user selected
		// units in both the top and bottom unit spinners.
		String name = propCurrent.getName();
		String key = KEY_PREFIX + '.' + name + '.';
		editor.putInt(key + KEY_START, spinStart.getSelectedItem().getID());
		editor.putInt(key + KEY_END, spinEnd.getSelectedItem().getID());
	}

	private void initUnits() {
		Activity act = getActivity();
		propCurrent = spinProp.getSelectedItem();
		int index = spinProp.getSelectedItemPosition();
		ArrayAdapter<Unit> adapter =
				spinStart.makeAdapter(act, propCurrent, unitArrayIDs[index]);
		spinStart.setAdapter(adapter);
		spinEnd.setAdapter(adapter);
	}

	private void restoreUnits(SharedPreferences prefs) {
		String name = propCurrent.getName();
		String key = KEY_PREFIX + '.' + name + '.';
		int deflt = spinStart.getItemAtPosition(0).getID();
		int id = prefs.getInt(key + KEY_START, deflt);
		spinStart.setSelectedID(id);
		deflt = spinEnd.getItemAtPosition(1).getID();
		id = prefs.getInt(key + KEY_END, deflt);
		spinEnd.setSelectedID(id);
	}


	@Override
	protected void compute() {
		double origAmt = decOrigAmt.getDec();
		int index = spinMult.getSelectedItemPosition();
		double ratio = ratios[index];
		double result = origAmt * ratio;

		Unit unitStart = spinStart.getSelectedItem();
		Unit unitEnd = spinEnd.getSelectedItem();
		result = propCurrent.convert(unitEnd, result, unitStart);

		decResult.setText(fmtrDec.format(result));
	}
}
