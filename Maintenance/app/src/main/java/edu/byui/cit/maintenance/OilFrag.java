package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

import edu.byui.cit.widget.CITFragment;


public class OilFrag extends CITFragment {

	private EditText editText_LOC, editText_NOC;

	// create Spinner object
	// Spinner spinner;
	// ArrayAdapter<CharSequence> adapter;


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.oil_frag, container, false);

		// find editTexts by ID
		editText_LOC = view.findViewById(R.id.editText_LOC);
		editText_NOC = view.findViewById(R.id.editText_NOC);
		// find button by id
		Button b_compute = view.findViewById(R.id.b_compute);
		// setOnClickListener
		b_compute.setOnClickListener(new computeNOC());

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
				Number number = formatter.parse(input);
				double one = number.doubleValue();
				double result = one + 3000;
				editText_NOC.setText(formatter.format(result));
			}
			catch (Exception ex) {
				// Don't do anything for this exception
			}

		}


	}
}