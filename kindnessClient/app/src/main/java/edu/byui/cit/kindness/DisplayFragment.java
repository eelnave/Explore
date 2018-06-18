package edu.byui.cit.kindness;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
		View view = inflater.inflate(R.layout.display_frag, container, false);
		timeSpin = new SpinString(view, R.id.timeSpinner, new spinnerChange());
		typeSpin = new SpinString(view, R.id.typeSpinner, new spinnerChange());

//		FragmentManager fm = getChildFragmentManager();
		SupportMapFragment mapFrag = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFrag);

		if (mMap == null) {
			mapFrag.getMapAsync(this);
		}
		return view;
	}

	private class spinnerChange implements ItemSelectedListener {

		@Override
		public void itemSelected(SpinWrapper source, int pos, long id) {
			// Clear the map of all markers

			// Get the current time
			Calendar calendar = Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH);

			//Log.i("Kailen's awesome debugging");

			// Based on the use's choice put markers whose corresonding reports fit within the time.


		}
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

				// Read from the database asynchronously
//				dbReports.addListenerForSingleValueEvent(new SingleReadHandler
// ());
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
				Report report = dataSnapshot.getValue(Report.class);

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
}
