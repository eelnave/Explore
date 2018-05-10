package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class AddVehicle extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.add_vehicle, container,
				false);
		view.findViewById(R.id.btnAdd).setOnClickListener(new AddHandler());
		return view;
	}

	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.addVehicle);
	}

	private final class AddHandler implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			View view = v.getRootView();
			EditText decYear = view.findViewById(R.id.year);
			EditText txtMake = view.findViewById(R.id.make);
			EditText txtModel = view.findViewById(R.id.model);

			float year = Float.parseFloat(decYear.getText().toString());
			String make = txtMake.getText().toString();
			String model = txtModel.getText().toString();

			// TODO: Store the values in local storage.
			txtModel.setText(year + " " + make + " " + model);
		}
	}
}
