package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.NumberFormat;

import edu.byui.cit.widget.CITFragment;


public class TiresFrag extends CITFragment {

	private EditText editText_LOC, editText_NOC, editText_LNT, editText_NNT;

	// create Spinner object
	// Spinner spinner;
	// ArrayAdapter<CharSequence> adapter;


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.tires_frag, container, false);

		// find editTexts by ID for first (inspection) calculation
		editText_LOC = view.findViewById(R.id.editText_LOC);
		editText_NOC = view.findViewById(R.id.editText_NOC1);
		// find button by id
		Button b_compute = view.findViewById(R.id.b_compute);
		// setOnClickListener
		b_compute.setOnClickListener(new computeNOC());

		// find spinner by ID
		//spinner = view.findViewById(R.id.spinner);
		//adapter = ArrayAdapter.createFromResource(this,R.array.oil_change_frequency,android.R.layout.simple_spinner_item);


		// find editTexts by ID for second (new tires) calculation
		editText_LNT = view.findViewById(R.id.editText_LNT);
		editText_NNT = view.findViewById(R.id.editText_NNT);
		// find button by id
		Button b_compute1 = view.findViewById(R.id.b_compute1);
		// setOnClickListener
		b_compute1.setOnClickListener(new computeNNT());


		// find spinner by ID
		//spinner = view.findViewById(R.id.spinner);
		//adapter = ArrayAdapter.createFromResource(this,R.array.oil_change_frequency,android.R.layout.simple_spinner_item);


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
				// replace default "3000" miles with variable based on user input
				int frequency_selection = 3000;
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



	private class computeNNT implements View.OnClickListener {
		@Override
		public void onClick(View b_compute1) {
			NumberFormat formatter = NumberFormat.getInstance();
			String input = editText_LNT.getText().toString();
			try {
				// replace default "25000" miles with variable based on user input
				int frequency_selection = 25000;
				Number number = formatter.parse(input);
				double one = number.doubleValue();
				double result = one + frequency_selection;
				editText_NNT.setText(formatter.format(result));
			}
			catch (Exception ex) {
				// Don't do anything for this exception
			}

		}


	}

}