package edu.byui.cit.kindness;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeMap;

import edu.byui.cit.exception.LocationException;
import edu.byui.cit.widget.ItemSelectedListener;
import edu.byui.cit.widget.SpinString;
import edu.byui.cit.widget.SpinWrapper;

public final class DisplayFragment extends CITFragment
		implements OnMapReadyCallback {
	private SpinString timeSpin, typeSpin;
	private final TreeMap<String, Report> allReports;
	private final HashMap<String, TreeMap<String, Report>> indexes;
	private Report report;

	private GoogleMap mMap;
	private BitmapDescriptor heart, gifts, service, time, touch, words;
	private DatabaseReference dbReports;

	public DisplayFragment() {
		super();

		// Create the list (TreeMap) that will hold all reports.
		allReports = new TreeMap<>();

		// Create the category indexes that will hold references to the
		// reports.
		indexes = new HashMap<>();
		for (Report.Category cat : Report.Category.values()) {
			indexes.put(cat.name(), new TreeMap<String, Report>());
		}
	}

	@Override
	protected String getTitle() {
		return "Kindness";
}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(KindnessActivity.TAG, "createView()");
		View view = inflater.inflate(R.layout.display_frag, container, false);
		timeSpin = new SpinString(view, R.id.timeSpinner, new spinnerChange());
		typeSpin = new SpinString(view, R.id.typeSpinner, new spinnerChange());

		SupportMapFragment mapFrag = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFrag);

		if (mMap == null) {
			mapFrag.getMapAsync(this);
		}
		return view;
	}

	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the
	 * camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will
	 * be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered
	 * once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		try {
			mMap = googleMap;

			// Load the icons to put on the map.
			if (heart == null) {
				heart = BitmapDescriptorFactory.fromResource(
						R.drawable.mapicon);
			}
			if (gifts == null) {
				gifts = BitmapDescriptorFactory.fromResource(
						R.drawable.gift_icon);
			}
			if (service == null) {
				service = BitmapDescriptorFactory.fromResource(
						R.drawable.service_icon);
			}
			if (time == null) {
				time = BitmapDescriptorFactory.fromResource(
						R.drawable.time_icon);
			}
			if (touch == null) {
				touch = BitmapDescriptorFactory.fromResource(
						R.drawable.touch_icon);
			}
			if (words == null) {
				words = BitmapDescriptorFactory.fromResource(
						R.drawable.words_icon);
			}

			if (dbReports == null) {
				// Get a reference to the /reports node in the database.
				FirebaseDatabase database = FirebaseDatabase.getInstance();
				dbReports = database.getReference(KindnessActivity
						.REPORTS_KEY);

				dbReports.addChildEventListener(new ReportAddedHandler());
			}

			// Move the camera to the user's location.
			Context ctx = getActivity().getApplicationContext();
			Location loc = LocationTracker.getInstance().getLocation(ctx);
			LatLng latlng = new LatLng(loc.getLatitude(), loc.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
		}
		catch (DatabaseException ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
		catch (LocationException ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
		catch (Exception ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
	}


//	private final class SingleReadHandler implements ValueEventListener {
//		@Override
//		public void onDataChange(DataSnapshot dataSnapshot) {
//			try {
//				GenericTypeIndicator<HashMap<String, Report>> type =
//						new GenericTypeIndicator<HashMap<String, Report>>() {
//						};
//				HashMap<String, Report> reports = dataSnapshot.getValue(type);
//				if (reports != null) {
//					for (Report value : reports.values()) {
//						MarkerOptions opts = new MarkerOptions();
//						opts.position(new LatLng(
//								value.getLatitude(), value.getLongitude()));
//						opts.icon(heart);
//						mMap.addMarker(opts);
//					}
//				}
//			}
//			catch (DatabaseException ex) {
//				Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
//			}
//			catch (Exception ex) {
//				Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
//			}
//		}
//
//		@Override
//		public void onCancelled(DatabaseError error) {
//			// Failed to read value
//			Log.e(KindnessActivity.TAG, "DB error: " + error.toString());
//		}
//	}


	private final class ReportAddedHandler implements ChildEventListener {
		@Override
		public void onChildAdded(DataSnapshot dataSnapshot,
				String prevChildKey) {
			try {
				// Get the report that was added.
				String key = dataSnapshot.getKey();
				report = dataSnapshot.getValue(Report.class);

				// Add this report to the list of all reports and to the
				// category index that corresponds to this report's category.
				allReports.put(key, report);
				// use the indexes to do the filtering
				// report of the data and the index
				indexes.get(report.category().name()).put(key, report);

				// Draw a marker for this report on the map.
				MarkerOptions opts = new MarkerOptions();
				opts.position(
						new LatLng(report.getLatitude(),
								report.getLongitude()));
				//check to see which act was reported and return correct icon
				//to improve, report.category().icon enum instead of if
				// statements
				if (report.category().equals(Report.Category.Gifts)) {
					opts.icon(gifts);
				}
				else if (report.category().equals(Report.Category.Service)) {
					opts.icon(service);
				}
				else if (report.category().equals(Report.Category.Time)) {
					opts.icon(time);
				}
				else if (report.category().equals(Report.Category.Touch)) {
					opts.icon(touch);
				}
				else if (report.category().equals(Report.Category.Words)) {
					opts.icon(words);
				}
				else {
					opts.icon(heart);
				}
				mMap.addMarker(opts);
			}
			catch (DatabaseException ex) {
				Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
			}
			catch (Exception ex) {
				Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
			}
		}

		@Override
		public void onChildChanged(DataSnapshot dataSnapshot, String s) {
		}

		@Override
		public void onChildRemoved(DataSnapshot dataSnapshot) {
		}

		@Override
		public void onChildMoved(DataSnapshot dataSnapshot, String s) {
		}

		@Override
		public void onCancelled(DatabaseError error) {
			// Failed to read value
			Log.e(KindnessActivity.TAG, "DB error: " + error.toString());
		}
	}

	private class spinnerChange implements ItemSelectedListener {

		@Override
		public void itemSelected(SpinWrapper source, int pos, long id) {
			MarkerOptions opts = new MarkerOptions();
			TreeMap<String, Report> selRep;

			// Clear the map of all markers
			mMap.clear();

			//time spinner
			String selectedTime = timeSpin.getSelectedItem();
			// Get the current time of machine
			Calendar calendar = Calendar.getInstance();

			long millis = calendar.getTimeInMillis();
			//should I just use milliseconds? easier to convert and compare
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			//DAY_OF_YEAR day number within current year, can count for a week
			int week = calendar.get(Calendar.DAY_OF_YEAR);
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			//timezone differences?
			//server time of reports
			//Based on the user's choice put markers whose corresponding reports fit within the time.
			//last hour = 3600000 ms, last 24 hours = 86400000 ms,
			//last week = 604800016.56 ms, last month = 2629800000 ms
			//last year = 31557600000 ms, all time
			//return selected reports

			//get selected item in spinner
			String selectedType = typeSpin.getSelectedItem();
			//get reports
			indexes.get(report.category());

			//if selected item is gift, return only gift reports
			if(selectedType.equals("Gifts")){
				selRep = indexes.get("Gifts");
				opts.icon(gifts);
			} else if(selectedType.equals("Service")){
				selRep = indexes.get("Service");
				opts.icon(service);
			}else if(selectedType.equals("Time")) {
				selRep = indexes.get("Time");
				opts.icon(time);
			}else if(selectedType.equals("Touch")) {
				selRep = indexes.get("Touch");
				opts.icon(touch);
			}else if(selectedType.equals("Words")) {
				selRep = indexes.get("Words");
				opts.icon(words);
			}else{
				selRep = indexes.get("Category");
			}

			//populate map with reports for category selected
			for (String key : selRep.keySet()) {
				Report report = selRep.get(key);
				opts.position(
						new LatLng(report.getLatitude(),
								report.getLongitude()));
				mMap.addMarker(opts);
			}
		}
	}
}

