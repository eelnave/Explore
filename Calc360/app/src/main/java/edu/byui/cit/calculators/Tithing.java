package edu.byui.cit.calculators;

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
import edu.byui.cit.text.TextWrapper;

public final class Tithing extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	private EditCurrency income;
	private EditDecimal tithing;
	private EditCurrency charity;
	private EditCurrency aid;
	private EditCurrency other;
	private TextWrapper  curTotal;


	public Tithing() {
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();

	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tithing, container, false);

		income = new EditCurrency(view, R.id.incomeID, this);
		tithing = new EditDecimal(view, R.id.decTithing, this);
		charity = new EditCurrency(view, R.id.charityID, this);
		other = new EditCurrency(view, R.id.otherMoneyID, this);
		aid = new EditCurrency(view, R.id.aidID, this);
		curTotal = new TextWrapper(view, R.id.curTotal);

		EditWrapper[] inputs = { income };
		ControlWrapper[] toClear = { income, curTotal, charity, other, aid};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous sales tax rate entered by the user if it exits.
		tithing.restore(prefs, fmtrDec, 10);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the tax rate entered by the user into the preferences file.
		tithing.save(editor);
	}


	@Override
	protected void compute() {
		double c = 0;
		double a = 0;
		double o = 0;
		double t = 0;

		if (income.notEmpty() && tithing.notEmpty()) {
			if (charity.notEmpty()) {
				c = charity.getCur();
			}
			if(aid.notEmpty()){
				a = aid.getCur();
			}
			if(tithing.notEmpty()){
				t = tithing.getDec();
			}
			if(other.notEmpty()){
				o = other.getCur();
			}

			double tithingDec = t / 100.00;
				double totalTithing = (tithingDec * income.getCur());
				double total = totalTithing + c + a + o;

				curTotal.setText(fmtrCur.format(total));

		}
		else {
			curTotal.clear();
			curTotal.clear();

		}
	}
}
