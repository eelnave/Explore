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


public class SubmitFragment extends CITFragment {
	private TextView longitudeView, latitudeView;
	private View view;

	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.howTo);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		view = inflater.inflate(R.layout.submit_fragment, container,
				false);

		latitudeView = view.findViewById(R.id.latitudeView);
		longitudeView = view.findViewById(R.id.longitudeView);
		TextView categoryView = view.findViewById(R.id.categoryView);

		Button submit = view.findViewById(R.id.submitButton);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent toMap = new Intent(getActivity(), MapActivity.class);
				getActivity().startActivity(toMap);
			}
		});

		//convert to actual category name
		//a collection of the names with the key being their associated id.
		// Grab that name from the collection using the id passed in.
		Bundle args = getArguments();
		TreeMap<Integer, String> categories = new TreeMap<>();
		categories.put(R.id.service, "Service");
		categories.put(R.id.time, "Time");
		categories.put(R.id.touch, "Touch");
		categories.put(R.id.gifts, "Gifts");
		categories.put(R.id.words, "Words");

		categoryView.setText("" + categories.get(args.getInt("id")));
		addReports();
		return view;
	}


	private void addReports() {
		double lat = 0, lon = 0;
		GPSTracker gps = new GPSTracker(view.getContext());
		Location loc = gps.getLocation();
		if (loc != null) {
			lat = Math.floor(loc.getLatitude() * 100) / 100;
			lon = Math.floor(loc.getLongitude() * 100) / 100;
			Toast.makeText(view.getContext(), "LAT: " + lat + " Lon: " + lon,
					Toast.LENGTH_LONG).show();
		}

		latitudeView.setText("" + lat);
		longitudeView.setText("" + lon);

		Report report = new Report(lat, lon);
		report.addReport();
	}
}
