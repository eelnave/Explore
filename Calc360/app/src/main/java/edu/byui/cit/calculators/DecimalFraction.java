package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class DecimalFraction extends CalcFragment {

	/*
	* fraction -> decimal
	* numerator/denominator
	*
	*
	* decimal -> fraction
	* if 1 decimal place then put over 10 (compare)
	* 	loop 1-10
	* 		if % of (10/i) = 0
	* 		and if % of (decimal/i) = 0
	* 		then divide (10/i) and (decimal/i)
* loop again using new denominator instead of 10
* if % never = 0 then end loop
* display decimal in numerator - and display new numerator
	* */

	private final NumberFormat fmtrDec;
	private TextWrapper dec;

	private EditDecimal decimal;
	private EditDecimal whole;
	private EditDecimal num;
	private EditDecimal denom;


	public DecimalFraction() {
		// Call the constructor in the parent class CalcFragment.
		super();

		// Create the two NumberFormat objects that this calculator will
		// use to format numbers before they are displayed to the user.
		fmtrDec = NumberFormat.getInstance();

	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {


		View view = inflater.inflate(R.layout.decimal_fraction, container, false);
		decimal = new EditDecimal(view, R.id.decimal,this);
		whole = new EditDecimal(view, R.id.wholeNumF,this);
		num = new EditDecimal(view, R.id.numerator,this);
		denom = new EditDecimal(view, R.id.denominator,this);

		EditWrapper[] inputs = { decimal, whole, num, denom };
		ControlWrapper[] toClear = { decimal, whole, num, denom };
		initialize(view, inputs, R.id.clearB, toClear);
		return view;
	}

	@Override
	protected void compute() {
		if (decimal.hasUserInput()) {
			double deci = decimal.getDec();
			double left = Math.floor(deci);
			double right = deci - left;
			double denomer = Math.pow(10,Double.toString(right).length());
			double numer = right * denomer;

			double[] outputs = simplify(numer, denomer);
			numer = outputs[0];
			denomer = outputs[1];
			whole.setText(Double.toString(left));
			num.setText(Double.toString(numer));
			denom.setText(Double.toString(denomer));
		}
		else if (whole.hasUserInput() && num.hasUserInput() && denom.hasUserInput()) {
			double right = num.getDec() / denom.getDec();
			double left = whole.getDec();
			String deci = Double.toString(left +  right);
			decimal.setText(deci);
		}
	}

	public double[] simplify(double n1, double n2){
		double[] out = new double[2];
		while (n1 % 2 == 0 || n2 % 2 == 0){
			if(n1 % 2 == 0){
				n1 = n1 / 2;
			}
			if(n2 % 2 == 0) {
				n2 = n2 / 2;
			}
		}
		out[0] = n1;
		out[1] = n2;
		return out;
	}
}
