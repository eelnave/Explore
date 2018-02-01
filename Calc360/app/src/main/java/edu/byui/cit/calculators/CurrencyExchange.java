package edu.byui.cit.calculators;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.Converter;
import edu.byui.cit.calc360.R;
import edu.byui.cit.units.Money;


public final class CurrencyExchange extends Converter {
	public CurrencyExchange() {
		super("CurrExch", Money.getInstance(), R.array.money);
		this.fmtrDec.setMaximumFractionDigits(2);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = super.createView(inflater, container, savedInstState);
		TextView txtMessage = view.findViewById(R.id.txtMessage);
		txtMessage.setText(R.string.currencyCredit);
		return view;
	}

	@Override
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
