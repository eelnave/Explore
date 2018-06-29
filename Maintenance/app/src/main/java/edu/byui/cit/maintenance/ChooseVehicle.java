package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import edu.byui.cit.model.Vehicle;
import edu.byui.cit.widget.CITFragment;


public class ChooseVehicle extends CITFragment {

	private MaintenanceFrag fragAct;
	private Button v1;


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.choose_vehicle, container, false);

		// create v1 onClickListener
		// prepend "view" to view.findViewById(R.id.v1); because you are outside of MainActivity
		v1 = view.findViewById(R.id.v1);
		v1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (fragAct == null || fragAct.isDetached()) {
					fragAct = new MaintenanceFrag();
				}
				// hide FAB on fragment fragAct
				//fab.hide();
				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(fragAct);
			}
		});

		return view;
	}

	//added switchFragment for v1 onClickListener
	public void switchFragment(CITFragment fragment) {
		// Replace whatever is in the fragContainer view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment, "thing");
		trans.addToBackStack(null);
		trans.commit();
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


}