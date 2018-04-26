package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Statistics;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.EditWrapper;
import edu.byui.cit.text.TextWrapper;


public final class BinDistProb extends CalcFragment {
	private EditInteger editN, editX;
	private EditDecimal editP;
	private EditWrapper[] inputs;
	private TextWrapper resultProb;
	private final NumberFormat fmtrPerc = NumberFormat.getInstance();

	public BinDistProb() {
		super();
		fmtrPerc.setMaximumFractionDigits(5);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bin_dist_prob, container, false);

		editN = new EditInteger(view, R.id.tri, this);
		editX = new EditInteger(view, R.id.def, this);
		editP = new EditDecimal(view, R.id.pro, this);
		resultProb = new TextWrapper(view, R.id.binPro);

		inputs = new EditWrapper[]{ editP, editN, editX };
		ControlWrapper[] toClear = { editP, editN, editX, resultProb };
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	protected void compute() {
		if (EditWrapper.allNotEmpty(inputs)) {
			int n = editN.getInt();
			int x = editX.getInt();
			double p = editP.getDec();

			double prob = Statistics.binDistProb(n, x, p);
			resultProb.setText(fmtrPerc.format(prob));
		}
	}
}
