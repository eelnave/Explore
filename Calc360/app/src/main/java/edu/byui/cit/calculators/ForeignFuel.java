package edu.byui.cit.calculators;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import edu.byui.cit.units.Money;
import edu.byui.cit.units.Property;
import edu.byui.cit.units.Unit;
import edu.byui.cit.units.Volume;


public final class ForeignFuel extends CalcFragment {
	// Keys for getting user preferences from the preferences file.
	private static final String
			KEY_PREFIX = "ForeignFuel",
			KEY_UNITS_COST_FROM = KEY_PREFIX + ".unitsCostFrom",
			KEY_UNITS_VOL_FROM = KEY_PREFIX + ".unitsVolFrom",
			KEY_UNITS_COST_TO = KEY_PREFIX + ".unitsCostTo",
			KEY_UNITS_VOL_TO = KEY_PREFIX + ".unitsVolTo";

	private final NumberFormat fmtrCost, fmtrVol;
	private EditCurrency curCostFrom;
	private EditDecimal decVolFrom;
	private SpinUnit unitsCostFrom, unitsVolFrom, unitsCostTo, unitsVolTo;
	private TextWrapper decRatioFrom, unitsRatioFrom,
			curCostTo, decVolTo, decRatioTo, unitsRatioTo;

	public ForeignFuel() {
		super();
		fmtrCost = NumberFormat.getInstance();
		fmtrVol = NumberFormat.getInstance();
		fmtrCost.setMinimumFractionDigits(0);
		fmtrCost.setMaximumFractionDigits(2);
		fmtrVol.setMinimumFractionDigits(0);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.foreign_fuel, container, false);

		Activity act = getActivity();
		Money money = Money.getInstance();
		money.getRates(act);

		Volume volume = Volume.getInstance();
		unitsCostFrom = new SpinUnit(act, view, R.id.unitsCostFrom,
				money, KEY_UNITS_COST_FROM, this);
		unitsVolFrom = new SpinUnit(act, view, R.id.unitsVolFrom,
				volume, R.array.feVolUnits, KEY_UNITS_VOL_FROM, this);
		unitsCostTo = new SpinUnit(act, view, R.id.unitsCostTo,
				money, KEY_UNITS_COST_TO, this);
		unitsVolTo = new SpinUnit(act, view, R.id.unitsVolTo,
				volume, R.array.feVolUnits, KEY_UNITS_VOL_TO, this);

		// Create a wrapper object for each EditText
		// that appears in this calculator's layout.
		curCostFrom = new EditCurrency(view, R.id.curCostFrom, this);
		decVolFrom = new EditDecimal(view, R.id.decVolFrom, this);

		decRatioFrom = new TextWrapper(view, R.id.decRatioFrom);
		unitsRatioFrom = new TextWrapper(view, R.id.unitsRatioFrom);
		curCostTo = new TextWrapper(view, R.id.curCostTo);
		decVolTo = new TextWrapper(view, R.id.decVolTo);
		decRatioTo = new TextWrapper(view, R.id.decRatioTo);
		unitsRatioTo = new TextWrapper(view, R.id.unitsRatioTo);

		EditWrapper[] inputs = { curCostFrom, decVolFrom };
		ControlWrapper[] toClear = {
				curCostFrom, decVolFrom, decRatioFrom, unitsRatioFrom,
				curCostTo, decVolTo, decRatioTo, unitsRatioTo
		};

		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the user's preferred units from the system
		// preferences file and initialize each spinner.
		unitsCostFrom.restore(prefs, Money.CAD);
		unitsVolFrom.restore(prefs, Volume.liter);
		unitsCostTo.restore(prefs, Money.USD);
		unitsVolTo.restore(prefs, Volume.gallon);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the IDs of the units chosen by
		// the user into the preferences file.
		unitsCostFrom.save(editor);
		unitsVolFrom.save(editor);
		unitsCostTo.save(editor);
		unitsVolTo.save(editor);
	}


	@Override
	protected void compute() {
		clearOutput();

		double costFrom = 0;
		double costTo = 0;
		Unit unCostFrom = null;
		Unit unCostTo = null;
		if (curCostFrom.notEmpty()) {
			costFrom = curCostFrom.getCur();
			unCostFrom = unitsCostFrom.getSelectedItem();
			Property money = Money.getInstance();
			unCostTo = unitsCostTo.getSelectedItem();
			costTo = money.convert(unCostTo, costFrom, unCostFrom);
			curCostTo.setText(fmtrCost.format(costTo));
		}

		double volFrom = 0;
		double volTo = 0;
		Unit unVolFrom = null;
		Unit unVolTo = null;
		if (decVolFrom.notEmpty()) {
			volFrom = decVolFrom.getDec();
			unVolFrom = unitsVolFrom.getSelectedItem();
			Property volume = Volume.getInstance();
			unVolTo = unitsVolTo.getSelectedItem();
			volTo = volume.convert(unVolTo, volFrom, unVolFrom);
			decVolTo.setText(fmtrVol.format(volTo));
		}

		if (curCostFrom.notEmpty() && decVolFrom.notEmpty()
				&& volFrom > 0) {
			double ratioFrom = costFrom / volFrom;
			double ratioTo = costTo / volTo;

			// Display the results for the user to see.
			Resources res = getResources();
			String per = res.getString(R.string.per);
			String unitsFrom =
					res.getQuantityString(unCostFrom.getPluralsID(), Integer.MAX_VALUE)
					+ " " + per + " " +
					res.getQuantityString(unVolFrom.getPluralsID(), 1);
			String unitsTo =
				res.getQuantityString(unCostTo.getPluralsID(), Integer.MAX_VALUE)
					+ " " + per + " " +
					res.getQuantityString(unVolTo.getPluralsID(), 1);
			decRatioFrom.setText(fmtrCost.format(ratioFrom));
			unitsRatioFrom.setText(unitsFrom);
			decRatioTo.setText(fmtrCost.format(ratioTo));
			unitsRatioTo.setText(unitsTo);
		}
	}
}
