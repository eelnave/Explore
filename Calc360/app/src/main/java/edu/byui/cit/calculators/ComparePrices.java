package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.ItemSelectedHandler;
import edu.byui.cit.text.SpinProperty;
import edu.byui.cit.text.SpinUnit;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;


public final class ComparePrices extends CalcFragment {
	private static final String
			KEY_PREFIX = "CmpPrices",
			KEY_PROP = KEY_PREFIX + ".prop";

	private SpinProperty spinProp;
	private Property propCurrent;
	private final Product[] products;
	private final NumberFormat fmtrCur;


	public ComparePrices() {
		super();
		products = new Product[3];
		for (int i = 0; i < products.length; ++i) {
			products[i] = new Product();
		}
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.compare_prices, container,
				false);

		Activity act = getActivity();
		spinProp = new SpinProperty(act, view, R.id.spinProp,
				R.array.cmpPricesProps, KEY_PROP, new ChangeProperty());

		// Get references to the user interface components for each product.
		try {
			for (int i = 0; i < products.length; ++i) {
				products[i].init(view, i + 1);
			}
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
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
		Property prop = spinProp.getSelectedItem();
		editor.putInt(KEY_PROP, prop.getID());

		// Write the user selected units into the preference file.
		saveUnits(editor);
	}


	private final class Product {
		EditCur curPrice;
		EditDec decQuant;
		SpinUnit spinUnits;
		TextWrapper curPer;

		void init(View view, int which)
				throws NoSuchFieldException, IllegalAccessException {
			Class clss = R.id.class;
			ComparePrices parent = ComparePrices.this;
			curPrice = new EditCur(view,
					Calc360.getID(clss, "curPrice" + which), parent);
			decQuant = new EditDec(view,
					Calc360.getID(clss, "decQuant" + which), parent);
			spinUnits = new SpinUnit(view,
					Calc360.getID(clss, "spinUnits" + which), parent);
			curPer = new TextWrapper(view,
					Calc360.getID(clss, "curPer" + which));
		}

		/** Writes the user selected units for
		 * this product into the preferences file. */
		void saveUnits(SharedPreferences.Editor editor, String key) {
			editor.putInt(key, spinUnits.getSelectedItem().getID());
		}

		void restoreUnits(SharedPreferences prefs, String key) {
			int deflt = spinUnits.getItemAtPosition(0).getID();
			int id = prefs.getInt(key, deflt);
			spinUnits.setSelectedID(id);
		}
	}


	private final class ChangeProperty extends ItemSelectedHandler {
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			for (Product prod : products) {
				prod.curPer.getView().setText(R.string.noSelection);
			}
		}

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
		String name = propCurrent.getName();
		String key = KEY_PREFIX + '.' + name;
		for (int i = 0;  i < products.length;  ++i) {
			String which = Integer.toString(i + 1);
			products[i].saveUnits(editor, key + which);
		}
	}

	private void initUnits() {
		propCurrent = spinProp.getSelectedItem();
		ArrayAdapter<Unit> adapter = null;
		if (! propCurrent.getName().equals("")) {
			Activity act = getActivity();
			adapter = products[0].spinUnits.makeAdapter(act, propCurrent);
		}
		for (Product prod : products) {
			// Apply the adapter to the spinner
			prod.spinUnits.setAdapter(adapter);
		}
	}

	private void restoreUnits(SharedPreferences prefs) {
		String name = propCurrent.getName();
		String key = KEY_PREFIX + '.' + name;
		for (int i = 0;  i < products.length;  ++i) {
			String which = Integer.toString(i + 1);
			products[i].restoreUnits(prefs, key + which);
		}
	}


	@Override
	protected void compute() {
		clearResults();

		Unit general = null;
		Property physProp = spinProp.getSelectedItem();
		String propName = physProp.getName();
		if (!propName.equals("")) {
			// If the user has selected a physical property and
			// units, then choose the units to use for comparison.
			ArrayList<Unit> unitsList = new ArrayList<>(
					products.length);
			for (Product prod : products) {
				if (prod.curPrice.notEmpty() &&
						prod.decQuant.notEmpty()) {
					Unit units = prod.spinUnits.getSelectedItem();
					if (!unitsList.contains(units)) {
						unitsList.add(units);
					}
				}
			}

			if (unitsList.size() > 0) {
				// Select the units to use for comparison.
				Collections.sort(unitsList);
				int index = (unitsList.size() - 1) / 2;
				general = unitsList.get(index);
			}
		}

		Product best = null;
		double min = Double.MAX_VALUE;
		String per = getResources().getString(R.string.per);
		for (Product prod : products) {
			if (prod.curPrice.notEmpty() && prod.decQuant.notEmpty()) {
				double price = prod.curPrice.getCur();
				double quant = prod.decQuant.getDec();
				if (general != null) {
					Unit units = prod.spinUnits.getSelectedItem();
					quant = physProp.convert(general, quant, units);
				}
				double ratio = price / quant;
				if (ratio < min) {
					min = ratio;
					best = prod;
				}
				String output = fmtrCur.format(ratio);
				if (general != null) {
					output += " " + per + " " + general;
				}
				prod.curPer.setText(output);
			}
		}

		if (best != null) {
			// Resources.Theme theme = getActivity().getTheme();
			// TypedValue val = new TypedValue();
			// theme.resolveAttribute(android.R.attr.colorAccent, val,
			// true);
			// @ColorInt int color = val.data;

			@ColorInt int color = ContextCompat.getColor(getActivity(),
					R.color.best);

			// For use with API 23 and beyond
			// @ColorInt int color = getResources().getColor(R.color
			// .best, null);

			best.curPer.getView().setBackgroundColor(color);
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			String physName = spinProp.getSelectedItem().toString();
			boolean hasUnits = !physName.equals("");
			for (Product prod : products) {
				prod.curPrice.clear();
				prod.decQuant.clear();
				if (hasUnits) {
					prod.spinUnits.setSelection(0);
				}
			}
			clearResults();
			products[0].curPrice.requestFocus();
		}
	}

	private void clearResults() {
		for (Product prod : products) {
			prod.curPer.clear();
			prod.curPer.getView().setBackground(null);
		}
	}
}
