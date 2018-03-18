package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import edu.byui.cit.text.EditDecimal;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;


/**
 * Created by Jordan on 3/16/2018.
 */

public final class Fraction extends CalcFragment{

	private EditDecimal a1, b1, c1, d1;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.fraction, container, false);

		a1 = new EditDecimal(view, R.id.a1, this);
		b1 = new EditDecimal(view, R.id.b1, this);
		c1 = new EditDecimal(view, R.id.c1, this);
		d1 = new EditDecimal(view, R.id.d1, this);

		return view;
	}
}
