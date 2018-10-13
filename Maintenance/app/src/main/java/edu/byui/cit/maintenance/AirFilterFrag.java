package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.NumberFormat;

import edu.byui.cit.widget.CITFragment;


public class AirFilterFrag extends CITFragment {

	private EditText editText_LOC, editText_NOC;
	// create Spinner object
	Spinner spinner;

	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.airfilter_frag, container, false);

		// find editTexts by ID
		editText_LOC = view.findViewById(R.id.editText_LOC);
		editText_NOC = view.findViewById(R.id.editText_NOC1);
		// find button by id
		Button b_compute = view.findViewById(R.id.b_compute);
		// setOnClickListener
		b_compute.setOnClickListener(new computeNOC());

		return view;
	}

	@Override
	protected String getTitle() {
		return null;
	}

	private class computeNOC implements View.OnClickListener {
		@Override
		public void onClick(View b_compute) {
			NumberFormat formatter = NumberFormat.getInstance();
			String input = editText_LOC.getText().toString();
			try {
				int frequency_selection = 15000;
				Number number = formatter.parse(input);
				double one = number.doubleValue();
				double result = one + frequency_selection;
				editText_NOC.setText(formatter.format(result));
			}
			catch (Exception ex) {
				// Don't do anything for this exception
			}

		}


	}
}