package edu.byui.cit.kindness;

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
		//getApplicationContext();
		GPSTracker gps = new GPSTracker(view.getContext());
		Location loc = gps.getLocation();
		if(loc != null){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			Toast.makeText(view.getContext(), "LAT: " + lat + " Lon: " + lon, Toast.LENGTH_LONG).show();
		}

		//change this, how do I just display the doubles?
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
		//convert to actual category name

		//a collection of the names with the key being their associated id. Grab that name from the collection using the id passed in.
		Bundle args = getArguments();
		TreeMap categories = new TreeMap();
		categories.put(2131165245, "Emotional");
		System.out.println(args.getInt("id"));
		categoryView.setText("" + categories.get(args.getInt("id")));

		addReports();
		return view;
	}
}
