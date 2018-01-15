package edu.byui.cit.calculators;

import android.util.Log;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Money;


public final class CurrencyExchange extends Converter {
	public CurrencyExchange() {
		super("CurrExch", Money.getInstance(), R.array.money);
		this.fmtrDec.setMaximumFractionDigits(2);
	}

	public void onResume() {
		super.onResume();
		try {
			Money.getInstance().getRates(getActivity());
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}
}
