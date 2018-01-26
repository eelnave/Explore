package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.EditDec;


public final class ShoeSize extends CalcFragment {
	private EditText decUSA, decMetric;
	private Map<Float, Float> inchesFromUSA;
	private NumberFormat fmtrDec;

	public ShoeSize() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inchesFromUSA = new HashMap<>();
		inchesFromUSA.put(6F, (9 + 1 / 4F));
		inchesFromUSA.put(6.5F, (9 + 1 / 2F));
		inchesFromUSA.put(7F, (9 + 5 / 8F));
		inchesFromUSA.put(7.5F, (9 + 3 / 4F));
		inchesFromUSA.put(8F, (9 + 15 / 16F));
		inchesFromUSA.put(8.5F, (10 + 1 / 8F));
		inchesFromUSA.put(9F, (10 + 1 / 4F));
		inchesFromUSA.put(9.5F, (10 + 7 / 16F));
		inchesFromUSA.put(10F, (10 + 9 / 16F));
		inchesFromUSA.put(10.5F, (10 + 3 / 4F));
		inchesFromUSA.put(11F, (10 + 15 / 16F));
		inchesFromUSA.put(11.5F, (11 + 1 / 8F));
		inchesFromUSA.put(12F, (11 + 1 / 4F));
		inchesFromUSA.put(13F, (11 + 9 / 16F));
		inchesFromUSA.put(14F, (11 + 7 / 8F));
		inchesFromUSA.put(15F, (12 + 3 / 16F));
		inchesFromUSA.put(16F, (12 + 1 / 2F));

		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMinimumFractionDigits(0);
		fmtrDec.setMaximumFractionDigits(1);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.shoe_size, container, false);

		decUSA = view.findViewById(R.id.shoeDecUSA);
		decMetric = view.findViewById(R.id.shoeDecMetric);
		new ButtonWrapper(view, R.id.shoeBtnCompute, this);
		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}

	@Override
	protected void compute() {
		String usa = decUSA.getText().toString();
		String metric = decMetric.getText().toString();
		if (usa.length() > 0) {
			float u = (float)EditDec.getDec(decUSA);
			float inches = inchesFromUSA.get(u);
			float millis = inches * 2.54F * 1.5F;
			decMetric.setText(fmtrDec.format(millis));
		}
		else if (metric.length() > 0) {

		}
	}

	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			decUSA.getText().clear();
			decMetric.getText().clear();
		}
	}
}
