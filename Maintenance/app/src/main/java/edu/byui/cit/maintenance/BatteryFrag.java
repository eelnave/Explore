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


public class BatteryFrag extends CITFragment {

	private EditText editText_LOC, editText_NOC, editText_NOC1;
	// create Spinner object
	Spinner spinner;


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.battery_frag, container, false);

		// find editTexts by ID
		editText_LOC = view.findViewById(R.id.editText_LOC);
		editText_NOC = view.findViewById(R.id.editText_NOC);
		editText_NOC1 = view.findViewById(R.id.editText_NOC1);

		// find button by id
		Button b_compute = view.findViewById(R.id.b_compute);
		// setOnClickListener
		b_compute.setOnClickListener(new computeNOC());

		// find spinner by ID
		//spinner = view.findViewById(R.id.spinner);
		//adapter = ArrayAdapter.createFromResource(this,R.array.oil_change_frequency,android.R.layout.simple_spinner_item);


		//ArrayAdapter<CharSequence> adapter;
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		//		R.array.oil_frequency_array edu.byui.cit.widget.SpinWrapper);
// Specify the layout to use when the list of choices appears
		//adapter.setDropDownViewResource(edu.byui.cit.widget.SpinWrapper);
// Apply the adapter to the spinner
	//	spinner.setAdapter(adapter);

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
				int frequency_selection = 30000;
				int frequency_selection1 = 50000;
				Number number = formatter.parse(input);
				double one = number.doubleValue();
				double result = one + frequency_selection;
				double result1 = one + frequency_selection1;
				editText_NOC.setText(formatter.format(result));
				editText_NOC1.setText(formatter.format(result1));
			}
			catch (Exception ex) {
				// Don't do anything for this exception
			}

		}


	}
}