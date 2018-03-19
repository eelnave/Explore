package edu.byui.cit.calculators;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditInteger;
import edu.byui.cit.text.TextWrapper;


public final class BinDistProb extends CalcFragment {

	private EditInteger editN, editX;
	private EditDecimal editP;
	private TextWrapper resultProb;
	private NumberFormat fmtrPerc;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		fmtrPerc = NumberFormat.getPercentInstance();

	}

}
