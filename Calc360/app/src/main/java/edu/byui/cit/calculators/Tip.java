package edu.byui.cit.calculators;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.Calc360;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.EditCur;
import edu.byui.cit.text.EditInt;
import edu.byui.cit.text.TextChangedHandler;

import static edu.byui.cit.model.Consumer.Tip.*;


/** A calculator that computes tip rate, tip amount, and total amount. */
public final class Tip extends CalcFragment {
	// These formatters are used to format numbers
	// before the numbers are shown to the user.
	private final NumberFormat fmtrInt;
	private final NumberFormat fmtrCur;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditCur curCost, curTaxAmt, curTipAmt, curTotal;
	private EditInt percTipRate;


	public Tip() {
		// Call the parent constructor.
		super();

		// Create the number formatter objects.
		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrCur = NumberFormat.getCurrencyInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.tip, container, false);

		// Create the text changed listeners (watchers in Android).
		TextChangedHandler otherHandler = new OtherHandler();
		TextChangedHandler amountHandler = new AmountHandler();
		TextChangedHandler totalHandler = new TotalHandler();

		// Get a reference to each of the text fields in this calculator.
		curCost = new EditCur(view, R.id.curCost, otherHandler);
		curTaxAmt = new EditCur(view, R.id.curTaxAmt, otherHandler);
		percTipRate = new EditInt(view, R.id.percTipRate, otherHandler);
		curTipAmt = new EditCur(view, R.id.curTipAmt, amountHandler);
		curTotal = new EditCur(view, R.id.curTotal, totalHandler);

		// Add a button click listener to the clear button.
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());

		return view;
	}


	/**
	 * Handles text changes in all the text fields except the tip amount
	 * and the total text fields. The code in this listener is executed
	 * when a user enters an amount in the cost, tax amount, or total
	 * text fields.
	 * Use case: The user wants to pay a tip of a known percentage. For
	 * example, the cost of a meal is 30, the tax amount is 1.5, and the
	 * user wants to leave a 15% tip. The user enters 30 in the cost text
	 * field, 1.5 in the tax amount text field, and 15 in the tip rate
	 * text field. This calculator then computes and displays the tip
	 * amount and the total amount.
	 */
	private final class OtherHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable edit) {
			try {
				if (curCost.notEmpty()) {
					// Get the cost from the user.
					double cost = curCost.getCur();

					// Get the tax amount from the user, if the user entered it.
					double tax = curTaxAmt.getCur(0);

					// Get the tip rate from the user, if the user entered it.
					double rate = percTipRate.getInt(0) / 100.0;

					// Compute the tip amount from the
					// cost of the meal and the tip rate.
					double amt = computeTipAmount(cost, rate);

					// Compute the total amount due.
					double total = computeTotal(cost, tax, amt);

					curTipAmt.setText(fmtrCur.format(amt));
					curTotal.setText(fmtrCur.format(total));
				}
				else {
					curTipAmt.clear();
					curTotal.clear();
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


	/**
	 * Handles text changes in the tip amount text field.
	 * Use Case: The user knows the amount she will leave for a tip and
	 * wants to know the corresponding tip rate. For example, the user
	 * enters 25 in the cost text field, 0.8 in the tax amount text field,
	 * and 3 in the tip amount text field. This calculator computes and
	 * displays the tip rate (12%) and the total amount (28.80)
	 */
	private final class AmountHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable s) {
			try {
				if (curCost.notEmpty()) {
					double cost = curCost.getCur();
					double tax = curTaxAmt.getCur(0);
					double amt = curTipAmt.getCur(0);

					double rate = computeTipRate(amt, cost);
					double total = computeTotal(cost, tax, amt);

					percTipRate.setText(fmtrInt.format(Math.round(rate * 100.0)));
					curTotal.setText(fmtrCur.format(total));
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


	/**
	 * Handles text changes in the total text field.
	 * Use Case: The user knows exactly how much he wants to spend for
	 * a meal. For example, the cost of a meal is $16.00, the tax is
	 * $0.50, and the user simply wants to pay $20.00 total for the meal.
	 * He enters 16 in the cost text field, 0.5 in the tax amount text
	 * field and 20 in the total text field. The calculator then computes
	 * and displays the tip amount and tip rate.
	 */
	private final class TotalHandler extends TextChangedHandler {
		@Override
		public void afterTextChanged(Editable s) {
			try {
				if (curCost.notEmpty()) {
					double cost = curCost.getCur();
					double tax = curTaxAmt.getCur(0);
					double total = curTotal.getCur(0);

					double amt = computeTipAmount(cost, tax, total);
					double rate = computeTipRate(amt, cost);

					percTipRate.setText(fmtrInt.format(Math.round(rate * 100.0)));
					curTipAmt.setText(fmtrCur.format(amt));
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


	/** Handles a click on the clear button. */
	private final class ClearHandler implements OnClickListener {
		@Override
		public void onClick(View button) {
			curCost.clear();
			curTaxAmt.clear();
			curTipAmt.clear();
			curTotal.clear();
			percTipRate.setText(fmtrInt.format(15));
			curCost.requestFocus();
		}
	}
}
