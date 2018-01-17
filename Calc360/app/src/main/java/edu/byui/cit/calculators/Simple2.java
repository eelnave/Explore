package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.SolveSome;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.Control;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;
import edu.byui.cit.text.TextWrapper;


public final class Simple2 extends SolveSome {
	private final NumberFormat fmtrDec;

	public Simple2() {
		// Call the parent constructor.
		super();

		// Create a number formatter object that
		// will format numbers for the user to see.
		fmtrDec = NumberFormat.getNumberInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.simple, container, false);

		// Get a reference to each of the text fields in this calculator.
		final EditDec decimal1 = new EditDec(view, R.id.decimal1, this);
		final EditDec decimal2 = new EditDec(view, R.id.decimal2, this);
		final TextWrapper result = new TextWrapper(view, R.id.result);

		Input[] inputs = { decimal1, decimal2 };
		Control[] toClear = { decimal1, decimal2, result };

		Solver[] solvers = new Solver[]{
				new Solver(new Input[]{ decimal1, decimal2 },
						new Control[]{ result }) {
					@Override
					public void solve() {
						double num1 = decimal1.getDec();
						double num2 = decimal2.getDec();
						double sum = num1 + num2;
						result.setText(fmtrDec.format(sum));
					}
				}
		};

		// Call initialize in the parent class.
		initialize(view, inputs, solvers, R.id.btnClear, toClear);

		return view;
	}
}
