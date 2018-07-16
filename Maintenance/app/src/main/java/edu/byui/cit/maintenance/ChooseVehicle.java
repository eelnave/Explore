package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Vehicle;
import edu.byui.cit.model.VehicleDAO;
import edu.byui.cit.widget.CITFragment;


public class ChooseVehicle extends CITFragment {

	private MaintenanceFrag fragAct;
	private ChooseVehicle chooseVehicle;
	private Button v1;
	private CarButton v2;
	private Button deleteButton;

	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.choose_vehicle, container, false);
		//list of vehicles
		AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
		final VehicleDAO vehicleDAO = db.getVehicleDAO();
		List<Vehicle> list =  vehicleDAO.getAll();
		Context context = getActivity();
		GridLayout grid = view.findViewById(R.id.ChooseVehicle);
		int columns = grid.getColumnCount();

		for (Vehicle v : list) {
			CarButton button = new CarButton(context);
			button.setText(v.getName());
			grid.addView(button);
			button.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			button.setLayoutParams(makeLayoutParams());

			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (fragAct == null || fragAct.isDetached()) {
						fragAct = new MaintenanceFrag();
						}
						//hide FAB on fragment fragAct
						//fab.hide();
						//switch to fragment fragAct (for viewing vehicle details)
					switchFragment(fragAct);
				}
			});

		}

		for (int i = columns - list.size(); i > 0; --i ) {
			TextView text = makeEmpty(context);
			grid.addView(text);

		}

		// create button for each vehicle
		// create v1 onClickListener
		// prepend "view" to view.findViewById(R.id.v1); because you are outside of MainActivity
		//v1 = view.findViewById(R.id.v1);
		//v1.setOnClickListener(new View.OnClickListener() {
		//	@Override
		//	public void onClick(View view) {
		//		if (fragAct == null || fragAct.isDetached()) {
		//			fragAct = new MaintenanceFrag();
		//		}
		//		// hide FAB on fragment fragAct
		//		//fab.hide();
		//		//switch to fragment fragAct (for viewing vehicle details)
		//		switchFragment(fragAct);
		//	}
		//});

		//v2 = view.findViewById(R.id.v2);
		//v2.setOnClickListener(new View.OnClickListener() {
		//	@Override
		//	public void onClick(View view) {
		//		if (fragAct == null || fragAct.isDetached()) {
		//			fragAct = new MaintenanceFrag();
		//		}
				// hide FAB on fragment fragAct
				//fab.hide();
				//switch to fragment fragAct (for viewing vehicle details)
		//		switchFragment(fragAct);
		//	}
		//});

		deleteButton = view.findViewById(R.id.deleteButton);
		deleteButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				vehicleDAO.deleteVehicles();
				if (chooseVehicle == null || chooseVehicle.isDetached()) {
					chooseVehicle = new ChooseVehicle();
				}
				//hide FAB on fragment fragAct
				//fab.hide();
				//switch to fragment chooseVehicle (for viewing vehicle details)
				switchFragment(chooseVehicle);
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

	private static TextView makeEmpty(Context ctx) {
		TextView text = new TextView(ctx);
		text.setLayoutParams(makeLayoutParams());
		return text;
	}

	private static LayoutParams makeLayoutParams() {
		LayoutParams params = new LayoutParams();
		params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1F);
		params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1F);
		params.setMargins(0, 0, 0, 0);
		params.width = 0;
		params.height = 0;
		return params;
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