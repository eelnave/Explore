package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditCurrency;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;


public final class Tithing extends CalcFragment {
	private static final String KEY_TITHING_RATE = "Tithing.rate";
	private final NumberFormat fmtrDec, fmtrCur;

	private EditCurrency curIncome;
	private EditDecimal decRate;
	private TextWrapper curTithing;
	private EditCurrency curCharity;
	private EditCurrency curHumanAid;
	private EditCurrency curOther;
	private TextWrapper curTotal;


	public Tithing() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tithing, container, false);

		curIncome = new EditCurrency(view, R.id.incomeID, this);
		decRate = new EditDecimal(view, R.id.rateID, KEY_TITHING_RATE, this);
		curTithing = new TextWrapper(view, R.id.tithingID);
		curCharity = new EditCurrency(view, R.id.charityID, this);
		curHumanAid = new EditCurrency(view, R.id.aidID, this);
		curOther = new EditCurrency(view, R.id.otherID, this);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = {
				curIncome, decRate, curCharity, curHumanAid, curOther
		};
		WidgetWrapper[] toClear = {
				curIncome, curTithing,
				curCharity, curHumanAid, curOther, curTotal
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous sales tax rate entered by the user if it exits.
		decRate.restore(prefs, fmtrDec, 10);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the tax rate entered by the user into the preferences file.
		decRate.save(editor);
	}


	@Override
	protected void compute(WidgetWrapper source) {
		EditWrapper[] offerings = { curCharity, curHumanAid, curOther };
		if ((curIncome.notEmpty() && decRate.notEmpty()) ||
				EditWrapper.anyNotEmpty(offerings)) {
			double i = curIncome.getCur();
			double t = 0;
			double c = 0;
			double a = 0;
			double o = 0;

			if (decRate.notEmpty()) {
				double rate = decRate.getDec() / 100.0;
				t = i * rate;
				curTithing.setText(fmtrCur.format(t));
			}
			else {
				curTithing.clear();
			}

			if (curCharity.notEmpty()) {
				c = curCharity.getCur();
			}
			if (curHumanAid.notEmpty()) {
				a = curHumanAid.getCur();
			}
			if (curOther.notEmpty()) {
				o = curOther.getCur();
			}

			double total = t + c + a + o;
			curTotal.setText(fmtrCur.format(total));
		}
		else {
			curTithing.clear();
			curTotal.clear();
		}
	}
}
