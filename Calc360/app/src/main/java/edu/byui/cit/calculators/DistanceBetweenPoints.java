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


public final class DistanceBetweenPoints extends CalcFragment {
	private final NumberFormat fmtrDec;

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditDecimal oneX, oneY, twoX, twoY;
	private EditWrapper[] inputs;

	private TextWrapper decDist;


	public DistanceBetweenPoints() {
		// Call the constructor in the parent class.
		super();

		fmtrDec = NumberFormat.getInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.distance_between_points, container, false);

		// Get a reference to each of the text fields in this calculator.
		oneX = new EditDecimal(view, R.id.oneX, this);
		oneY = new EditDecimal(view, R.id.oneY, this);
		twoX = new EditDecimal(view, R.id.twoX, this);
		twoY = new EditDecimal(view, R.id.twoY, this);
		decDist = new TextWrapper(view, R.id.decDist);

		inputs = new EditWrapper[]{ oneX, oneY, twoX, twoY };
		ControlWrapper[] toClear = { oneX, oneY, twoX, twoY, decDist };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {
			double oX = oneX.getDec();
			double tX = twoX.getDec();
			double oY = oneY.getDec();
			double tY = twoY.getDec();
			double dist = Math.sqrt(Math.pow(tX - oX, 2) + Math.pow(tY - oY, 2));
			decDist.setText(fmtrDec.format(dist));
		}
		else {
			decDist.clear();
		}
	}
}
