package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.NumberFormat;

import edu.byui.cit.model.Statistics;
import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.widget.WidgetWrapper;


public final class Correlation extends CalcFragment {
	private final NumberFormat fmtrDec;
	private EditText txtSamp1, txtSamp2;
	private TextWrapper txtCorrel;

	public Correlation() {
		super();
		fmtrDec = NumberFormat.getInstance();
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.correlation, container, false);

		txtSamp1 = view.findViewById(R.id.decSamp1);
		txtSamp2 = view.findViewById(R.id.decSamp2);
		txtCorrel = new TextWrapper(view, R.id.decCorrel);

		new ButtonWrapper(view, R.id.btnCorrel, this);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	protected void compute(WidgetWrapper source) {
		String str1 = txtSamp1.getText().toString().trim();
		String str2 = txtSamp2.getText().toString().trim();
		if (str1.length() > 0 && str2.length() > 0) {
			double[] samp1 = EditDecimal.getDecimals(txtSamp1);
			double[] samp2 = EditDecimal.getDecimals(txtSamp2);
			if (samp1.length != samp2.length) {
				throw new IllegalArgumentException(
						getString(R.string.mismatchedSamples));
			}
			else if (samp1.length > 1) {
				double correl = Statistics.correlation(samp1, samp2);
				txtCorrel.setText(fmtrDec.format(correl));
			}
			else {
				txtCorrel.clear();
			}
		}
		else {
			txtCorrel.clear();
		}
	}


	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			txtSamp1.getText().clear();
			txtSamp2.getText().clear();
			txtCorrel.clear();
			txtSamp1.requestFocus();
		}
	}
}
