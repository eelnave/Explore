package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AddVehicle extends CITFragment {

	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.add_vehicle, container, false);
		return view;
	}

	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.addVehicle);
	}
}
