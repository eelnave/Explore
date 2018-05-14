package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditString;
import edu.byui.cit.widget.WidgetWrapper;


public class AddVehicle extends CITFragment {
	private EditDecimal decYear;
	private EditString strMake, strModel;

	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.add_vehicle, container,
				false);

		decYear = new EditDecimal(view, R.id.year);
		strMake = new EditString(view, R.id.make);
		strModel = new EditString(view, R.id.model);

		new ButtonWrapper(view, R.id.btnAdd, new AddHandler());
		new ButtonWrapper(view, R.id.btnReset, new ResetHandler());
		return view;
	}


	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.addVehicle);
	}


	private final class AddHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			float year = (float)decYear.getDec();
			String make = strMake.getText();
			String model = strModel.getText();

			// TODO: Store the values in local storage.
			strModel.setText(year + " " + make + " " + model);
		}
	}


	private final class ResetHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			decYear.clear();
			strMake.clear();
			strModel.clear();
			decYear.requestFocus();
		}
	}
}
