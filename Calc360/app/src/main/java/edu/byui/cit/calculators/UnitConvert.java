package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.ItemSelectedHandler;
import edu.byui.cit.text.SpinProperty;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextChangeHandler;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;

import android.content.Context;
import android.widget.ArrayAdapter;


public final class UnitConvert extends CalcFragment {
	private static final String
			KEY_PREFIX = "UnitConv",
			KEY_PROP = KEY_PREFIX + ".prop",
			KEY_TOP = "top",
			KEY_BOTTOM = "bottom";

	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private SpinProperty spinProp;
	private Property propCurrent;
	private EditDecimal decTop, decBottom;
	private SpinUnit spinTop, spinBottom;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout
		View view = inflater.inflate(R.layout.unit_convert, container,
				false);

		decTop = new EditDecimal(view, R.id.decTop, new TextChangeHandler() {
			@Override
			public void afterChanged(Editable editable) {
				compute(decBottom, spinBottom, decTop, spinTop);
			}
		});
		decBottom = new EditDecimal(view, R.id.decBottom, new TextChangeHandler() {
			@Override
			public void afterChanged(Editable editable) {
				compute(decTop, spinTop, decBottom, spinBottom);
			}
		});

		new ButtonWrapper(view, R.id.btnSwap, new ClickListener() {
			@Override
			public void clicked(View button) {
				int posTop = spinTop.getSelectedItemPosition();
				String txtTop = decTop.getText();
				spinTop.setSelection(spinBottom.getSelectedItemPosition());
				decTop.setText(decBottom.getText());
				spinBottom.setSelection(posTop);
				decBottom.setText(txtTop);
			}
		});

		spinProp = new SpinProperty(getActivity(), view, R.id.spinProp,
				R.array.supportedProperties, KEY_PROP,
				new ChangeProperty());

		ItemSelectedHandler handler = new ItemSelectedHandler() {
			@Override
			public void itemSelected(AdapterView<?> parent,
					View view, int pos, long id) {
				compute(decBottom, spinBottom, decTop, spinTop);
			}
		};
		spinTop = new SpinUnit(view, R.id.spinTop, handler);
		spinBottom = new SpinUnit(view, R.id.spinBottom, handler);

		EditWrapper[] inputs = { decTop, decBottom };
		initialize(view, inputs, R.id.btnClear, inputs);
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
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the user selected property into the preferences file.
		spinProp.save(editor);

		// Write the user selected units into the preference file.
		saveUnits(editor);
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
			compute(decBottom, spinBottom, decTop, spinTop);
		}
	}


	private void saveUnits(SharedPreferences.Editor editor) {
		// Write into the preferences file the user selected
		// units in both the top and bottom unit spinners.
		String name = propCurrent.getName();
		String key = KEY_PREFIX + '.' + name + '.';
		editor.putInt(key + KEY_TOP, spinTop.getSelectedItem().getID());
		editor.putInt(key + KEY_BOTTOM, spinBottom.getSelectedItem().getID());
	}

	private void initUnits() {
		propCurrent = spinProp.getSelectedItem();
		Activity act = getActivity();
		ArrayAdapter<Unit> adapter = spinTop.makeAdapter(act, propCurrent);
		spinTop.setAdapter(adapter);
		spinBottom.setAdapter(adapter);
	}

	private void restoreUnits(SharedPreferences prefs) {
		String name = propCurrent.getName();
		String key = KEY_PREFIX + '.' + name + '.';
		int deflt = spinTop.getItemAtPosition(0).getID();
		int id = prefs.getInt(key + KEY_TOP, deflt);
		spinTop.setSelectedID(id);
		deflt = spinBottom.getItemAtPosition(1).getID();
		id = prefs.getInt(key + KEY_BOTTOM, deflt);
		spinBottom.setSelectedID(id);
	}


	// Overload not override so try, catch is necessary.
	private void compute(EditDecimal decTo, SpinUnit spinTo,
			EditDecimal decFrom, SpinUnit spinFrom) {
		try {
			if (decFrom.notEmpty()) {
				Property prop = propCurrent;
				Unit unitFrom = spinFrom.getSelectedItem();
				Unit unitTo = spinTo.getSelectedItem();
				double from = decFrom.getDec();
				double to = prop.convert(unitTo, from, unitFrom);
				decTo.setText(fmtrDec.format(to));
			}
			else {
				decTo.clear();
			}
		}
		catch (NumberFormatException ex) {
			// Do nothing
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}
}
