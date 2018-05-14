package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.widget.CITFragment;


public class ChooseVehicle extends CITFragment {
	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		return inflater.inflate(R.layout.choose_vehicle, container, false);
	}

	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.appName);
	}
}
