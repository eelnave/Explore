package edu.byui.cit.calculators;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Consumer;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class Simple_CO_TM extends CalcFragment {
	private final NumberFormat fmtrDec, fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditDec totalMiles;
	private EditCur decReimRate;
	private TextWrapper curTotalReim;


	public Simple_CO_TM() {
		// Call the constructor in the parent class.
		super();

		fmtrDec = NumberFormat.getInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.simple_co_tm, container, false);

		// Get a reference to each of the text fields in this calculator.
		totalMiles = new EditDec(view, R.id.totalMiles, this);
		decReimRate = new EditCur(view, R.id.decReimRate, this);
		curTotalReim = new TextWrapper(view, R.id.curTotalReim);

		EditWrapper[] inputs = { totalMiles, decReimRate };
		ControlWrapper[] toClear = { totalMiles, curTotalReim, decReimRate};
		initialize(view, inputs, toClear, R.id.btnClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		// Get the previous sales tax rate entered by the user if it exits.
		decReimRate.restore(prefs, fmtrDec);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		// Write the tax rate entered by the user into the preferences file.
		decReimRate.save(editor);
	}


	@Override
	protected void compute() {
		if (totalMiles.notEmpty() && decReimRate.notEmpty()) {
			double miles = totalMiles.getDec();
			double reimRate = decReimRate.getCur() / 100.0;
			//returns total miles * reimbursement rate
			double reimbursement = Consumer.Ratio.amount(reimRate, miles);
			curTotalReim.setText(fmtrCur.format(reimbursement));
		}
		else {
			curTotalReim.clear();
		}
	}
}
