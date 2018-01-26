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
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.TextWrapper;


public final class MeanEtc extends CalcFragment {
	private final NumberFormat fmtrInt, fmtrDec;
	private EditText decData;
	private TextWrapper intCount, decMin, decFirst, decSecond, decThird, decMax;
	private TextWrapper decSum, decMean, decVar, decStdDev;

	public MeanEtc() {
		super();
		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrDec = NumberFormat.getInstance();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.mean_etc, container, false);

		decData = view.findViewById(R.id.decData);
		intCount = new TextWrapper(view, R.id.intCount);
		decMin = new TextWrapper(view, R.id.decMin);
		decFirst = new TextWrapper(view, R.id.decFirst);
		decSecond = new TextWrapper(view, R.id.decSecond);
		decThird = new TextWrapper(view, R.id.decThird);
		decMax = new TextWrapper(view, R.id.decMax);
		decSum = new TextWrapper(view, R.id.decSum);
		decMean = new TextWrapper(view, R.id.decMean);
		decVar = new TextWrapper(view, R.id.decVar);
		decStdDev = new TextWrapper(view, R.id.decStdDev);

		new ButtonWrapper(view, R.id.btnCompute, this);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	@Override
	protected void compute() {
		String str = decData.getText().toString().trim();
		if (str.length() > 0) {
			double[] numbers = EditDec.getDecimals(str);
			if (numbers.length > 0) {
				Statistics stats = new Statistics(numbers);
				intCount.setText(fmtrInt.format(stats.count));
				decMin.setText(fmtrDec.format(stats.min));
				decFirst.setText(fmtrDec.format(stats.first));
				decSecond.setText(fmtrDec.format(stats.second));
				decThird.setText(fmtrDec.format(stats.third));
				decMax.setText(fmtrDec.format(stats.max));
				decSum.setText(fmtrDec.format(stats.sum));
				decMean.setText(fmtrDec.format(stats.mean));
				decVar.setText(fmtrDec.format(stats.var));
				decStdDev.setText(fmtrDec.format(stats.stdev));
			}
			else {
				clearResults();
			}
		}
		else {
			clearResults();
		}
	}


	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			decData.getText().clear();
			clearResults();
			decData.requestFocus();
		}

	}

	private void clearResults() {
		intCount.clear();
		decMin.clear();
		decFirst.clear();
		decSecond.clear();
		decThird.clear();
		decMax.clear();
		decSum.clear();
		decMean.clear();
		decVar.clear();
		decStdDev.clear();
	}
}
