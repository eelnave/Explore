package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.byui.cit.widget.CITFragment;


public class MaintenanceFrag extends CITFragment {

	private OilFrag oilFrag;
	private Button oil;
	private BrakesFrag brakesFrag;
	private Button Brakes;
	private TiresFrag tiresFrag;
	private Button Tires;
	private BatteryFrag batteryFrag;
	private Button Battery;
	private AirFilterFrag airFilterFrag;
	private Button airFilter;
	private OtherFrag otherFrag;
	private Button other;


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {

		View view = inflater.inflate(R.layout.maintenance_frag, container,
				false);

		// create oil onClickListener
		// prepend "view" to view.findViewById(R.id.oil); because you are
        // outside of MainActivity
		oil = view.findViewById(R.id.b_oil);
		oil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (oilFrag == null || oilFrag.isDetached()) {
					oilFrag = new OilFrag();
				}
				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(oilFrag);
			}
		});


		// create brakes onClickListener
		// prepend "view" to view.findViewById(R.id.oil); because you are
        // outside of MainActivity
		Brakes = view.findViewById(R.id.b_brakes);
		Brakes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (brakesFrag == null || brakesFrag.isDetached()) {
					brakesFrag = new BrakesFrag();
				}

				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(brakesFrag);
			}
		});

		// create tires onClickListener
		// prepend "view" to view.findViewById(R.id.oil); because you are
        // outside of MainActivity
		Tires = view.findViewById(R.id.b_tires);
		Tires.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (tiresFrag == null || tiresFrag.isDetached()) {
					tiresFrag = new TiresFrag();
				}

				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(tiresFrag);
			}
		});

		// create battery onClickListener
		// prepend "view" to view.findViewById(R.id.oil); because you are
        // outside of MainActivity
		Battery = view.findViewById(R.id.b_battery);
		Battery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (batteryFrag == null || batteryFrag.isDetached()) {
					batteryFrag = new BatteryFrag();
				}

				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(batteryFrag);
			}
		});

		// create air filter onClickListener
		// prepend "view" to view.findViewById(R.id.oil); because you are
        // outside of MainActivity
		airFilter = view.findViewById(R.id.b_air_filter);
		airFilter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (airFilterFrag == null || airFilterFrag.isDetached()) {
					airFilterFrag = new AirFilterFrag();
				}

				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(airFilterFrag);
			}
		});

		// create other maintenance onClickListener
		// prepend "view" to view.findViewById(R.id.oil); because you are
        // outside of MainActivity
		other = view.findViewById(R.id.b_other_maintenance);
		other.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (otherFrag == null || otherFrag.isDetached()) {
					otherFrag = new OtherFrag();
				}

				//switch to fragment fragAct (for viewing vehicle details)
				switchFragment(otherFrag);
			}
		});

		return view;
	}

	//added switchFragment for oil onClickListener
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
		return "Maintenance";
	}


}