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


/** A calculator that computes tip rate, tip amount, and total amount. */
public final class Tip extends CalcFragment {
	private static final String KEY_TIP_RATE = "Tip.tipRate";

	// These formatters are used to format numbers
	// before the numbers are shown to the user.
	private final NumberFormat fmtrCur;
	private final NumberFormat fmtrPerc;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCur curCost, curTaxAmt, curTipAmt, curTotal;
	private EditDec percTipRate;
	private EditWrapper[] inputs;


	public Tip() {
		// Call the parent constructor.
		super();

		// Create the number formatter objects.
		fmtrCur = NumberFormat.getCurrencyInstance();
		fmtrPerc = NumberFormat.getInstance();
		fmtrPerc.setMaximumFractionDigits(1);
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.tip, container, false);

		// Get a reference to each of the text fields in this calculator.
		curCost = new EditCur(view, R.id.curCost, this);
		curTaxAmt = new EditCur(view, R.id.curTaxAmt, this);
		percTipRate = new EditDec(view, R.id.percTipRate, KEY_TIP_RATE, this);
		curTipAmt = new EditCur(view, R.id.curTipAmt, this);
		curTotal = new EditCur(view, R.id.curTotal, this);

		inputs = new EditWrapper[]{
				curCost, curTaxAmt, percTipRate, curTipAmt, curTotal
		};
		EditWrapper[][] groups = {{ percTipRate, curTipAmt, curTotal }};
		ControlWrapper[] toClear = { curCost, curTaxAmt, curTipAmt, curTotal };
		initialize(view, inputs, groups, toClear, R.id.btnClear);
		return view;
	}


	@Override
	protected void restorePrefs(SharedPreferences prefs) {
		percTipRate.restore(prefs, fmtrPerc, 15);
	}

	@Override
	protected void savePrefs(SharedPreferences.Editor editor) {
		if (percTipRate.hasUserInput()) {
			percTipRate.save(editor);
		}
	}


	@Override
	protected void compute() {
		if (curCost.hasUserInput()) {
			double cost = curCost.getCur();

			double taxAmt = 0;
			if (curTaxAmt.hasUserInput()) {
				taxAmt = curTaxAmt.getCur();
			}

			if (percTipRate.hasUserInput()) {
				/* Use case: The user wants to pay a tip of a known percentage.
				 * For example, the cost of a meal is 30, the tax amount is
				 * 1.5, and the user wants to leave a 15% tip. The user enters
				 * 30 in the cost text field, 1.5 in the tax amount text field,
				 * and 15 in the tip rate text field. This calculator then
				 * computes and displays the tip amount and the total amount. */
				double tipRate = percTipRate.getDec() / 100.0;
				double tipAmt = Consumer.Ratio.amount(tipRate, cost);
				curTipAmt.setText(fmtrCur.format(tipAmt));
				double total = cost + taxAmt + tipAmt;
				curTotal.setText(fmtrCur.format(total));
			}
			else if (curTipAmt.hasUserInput()) {
				/* Use Case: The user knows the amount she will leave for a tip
				 * and wants to know the corresponding tip rate. For example,
				 * the user enters 25 in the cost text field, 0.8 in the tax
				 * amount text field, and 3 in the tip amount text field. This
				 * calculator computes and displays the tip rate (12%) and the
				 * total amount (28.80) */
				double tipAmt = curTipAmt.getCur();
				double tipRate = Consumer.Ratio.rate(tipAmt, cost);
				percTipRate.setText(fmtrPerc.format(tipRate * 100.0));
				double total = cost + taxAmt + tipAmt;
				curTotal.setText(fmtrCur.format(total));
			}
			else if (curTotal.hasUserInput()) {
				/* Use Case: The user knows exactly how much he wants to spend
				 * for a meal. For example, the cost of a meal is $16.00, the
				 * tax is $0.50, and the user simply wants to pay $20.00 total
				 * for the meal. He enters 16 in the cost text field, 0.5 in
				 * the tax amount text field and 20 in the total text field.
				 * The calculator then computes and displays the tip amount and
				 * tip rate. */
				double total = curTotal.getCur();
				double tipAmt = total - (cost + taxAmt);
				double tipRate = Consumer.Ratio.rate(tipAmt, cost);
				percTipRate.setText(fmtrPerc.format(tipRate * 100.0));
				curTipAmt.setText(fmtrCur.format(tipAmt));
			}
			else {
				if (curTaxAmt.hasUserInput()) {
					double total = cost + taxAmt;
					curTotal.setText(fmtrCur.format(total));
				}
			}
		}
		else {
			clearOutput(inputs);
		}
	}
}

