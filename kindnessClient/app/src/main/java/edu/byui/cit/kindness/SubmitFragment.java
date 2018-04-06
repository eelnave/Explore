package edu.byui.cit.kindness;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;


public class SubmitFragment extends InfoFragment {
	TextView longitudeView, latitudeView, categoryView;
	View view;
	Button submit;

	private void addReports() {

		double lat = 0, lon = 0;
		GPSTracker gps = new GPSTracker(view.getContext());
		Location loc = gps.getLocation();
		if(loc != null){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			Toast.makeText(view.getContext(), "LAT: " + lat + " Lon: " + lon, Toast.LENGTH_LONG).show();
		}

		latitudeView.setText("" + lat);
		longitudeView.setText("" + lon);

		Report report = new Report(lat, lon);
		report.addReport();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		view = inflater.inflate(R.layout.submit, container,
				false);

		latitudeView = view.findViewById(R.id.latitudeView);
		longitudeView = view.findViewById(R.id.longitudeView);
		categoryView = view.findViewById(R.id.categoryView);

		submit = view.findViewById(R.id.submitButton);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent toMap = new Intent(getActivity(), KindnessMap.class);
				getActivity().startActivity(toMap);
			}
		});

		//convert to actual category name
		//a collection of the names with the key being their associated id. Grab that name from the collection using the id passed in.
		Bundle args = getArguments();
		TreeMap categories = new TreeMap();
		categories.put(R.id.service, "Service");
		categories.put(R.id.time, "Time");
		categories.put(R.id.touch, "Touch");
		categories.put(R.id.gift, "Gifts");
		categories.put(R.id.words, "Words");

		categoryView.setText("" + categories.get(args.getInt("id")));

		addReports();
		return view;
	}
}
