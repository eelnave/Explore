package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;

import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;
import edu.byui.cit.model.Mathematics;


public final class Fraction extends CalcFragment
	implements AdapterView.OnItemSelectedListener {

	private final NumberFormat fmtrDec;
	private int num, den;
	private EditDecimal a1, b1, c1, d1;
	private TextWrapper fn1, fd1;
	private Spinner dropdown;
	String[] operation = {"+", "-", "x", "/"};

	public Fraction() {
		super();
		fmtrDec = NumberFormat.getInstance();
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstState) {
		View view = inflater.inflate(R.layout.fraction, container, false);

		a1 = new EditDecimal(view, R.id.a1, this);
		b1 = new EditDecimal(view, R.id.b1, this);
		c1 = new EditDecimal(view, R.id.c1, this);
		d1 = new EditDecimal(view, R.id.d1, this);

		dropdown = view.findViewById(R.id.spinner1);
		dropdown.setOnItemSelectedListener(this);

		ArrayAdapter<String> aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, operation);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dropdown.setAdapter(aa);
		fn1 = new TextWrapper(view, R.id.fn1);
		fd1 = new TextWrapper(view, R.id.fd1);

		EditWrapper[] inputs = {a1, b1, c1, d1};
		ControlWrapper[] toClear = {fn1, fd1, a1, b1, c1, d1};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	protected void compute() {
		String text = dropdown.getSelectedItem().toString();
		if (text.equals("+")) {
			num = (int)((a1.getDec() * d1.getDec()) + (b1.getDec() * c1.getDec()));
			den = (int)(b1.getDec() * d1.getDec());
		}

		if (text.equals("-")) {
			num = (int)((a1.getDec() * d1.getDec()) - (b1.getDec() * c1.getDec()));
			den = (int)(b1.getDec() * d1.getDec());
		}

		if (text.equals("x")) {
			num = (int)(a1.getDec() * c1.getDec());
			den = (int)(b1.getDec() * d1.getDec());
		}

		if (text.equals("/")) {
			num = (int)(a1.getDec() * d1.getDec());
			den = (int)(b1.getDec() * c1.getDec());
		}

		int gcd = (int)Mathematics.gcd(num,den);

		num = num / gcd;
		den = den / gcd;

		fn1.setText(fmtrDec.format(num));
		fd1.setText(fmtrDec.format(den));

	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i,
							   long l) {
		Toast.makeText(getActivity(), operation[i], Toast.LENGTH_LONG).show();
		callCompute();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
