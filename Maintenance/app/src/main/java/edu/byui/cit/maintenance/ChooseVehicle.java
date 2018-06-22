package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.model.Vehicle;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.WidgetWrapper;


public class ChooseVehicle extends CITFragment {

	private MaintenanceFrag v1;


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.choose_vehicle, container, false);
		new ButtonWrapper(view, R.id.v1, new selectVehicle());

		return view;
	}

	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.appName);
	}

	protected Vehicle getVehicle() {
		 Vehicle vehicle;

		 vehicle = new Vehicle();
		return vehicle;
	}

	private class selectVehicle implements ClickListener {


        @Override
        public void clicked(WidgetWrapper source) {
            if (v1 == null || v1.isDetached()) {
                v1 = new MaintenanceFrag();
            }
            // I forgot what I can put here to make it work.
            // I think everything else should be correct for now though.
//			(MainActivity) <Something?>.switchFragment(v1);
        }
    }
}