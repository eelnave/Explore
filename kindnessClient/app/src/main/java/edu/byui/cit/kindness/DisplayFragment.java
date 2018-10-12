package edu.byui.cit.kindness;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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

	// This is a fragment within the main activity (main_activity.xml).
	// We had to do it this way because the spinners were showing up behind
	// the map, and now they have their own spot and can be used. This
	// fragment is different than the other fragments because it uses the
	// same toolbar as main_activity.xml and the floating action button (FAB).

	private SpinString spinTime, spinType;
	private final TreeMap<String, Report> allReports;
	private final HashMap<Category, TreeMap<String, Report>> indexes;

	private GoogleMap mMap;
	private DatabaseReference dbReports;

	public DisplayFragment() {
		super();

		// Create the list (TreeMap) that will hold all reports.
		allReports = new TreeMap<>();

		// Create the category indexes that will hold references to the
		// reports.
		indexes = new HashMap<>();
		for (Category cat : Category.values()) {
			indexes.put(cat, new TreeMap<String, Report>());
		}
	}

	@Override
	protected String getTitle() {
		return getString(R.string.appName);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		Log.i(MainActivity.TAG, "createView()");
		View view = inflater.inflate(R.layout.display_frag, container, false);
		spinTime = new SpinString(view, R.id.timeSpinner, new SpinnerChange());
		spinType = new SpinString(view, R.id.typeSpinner, new SpinnerChange());

		SupportMapFragment mapFrag = (SupportMapFragment)
				getChildFragmentManager().findFragmentById(R.id.mapFrag);

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
			Category.loadIcons(getResources());

			if (dbReports == null) {
				// Get a reference to the /reports node in the database.
				FirebaseDatabase database = FirebaseDatabase.getInstance();
				dbReports = database.getReference("/reports");

				dbReports.addChildEventListener(new ReportAddedHandler());
			}

			// Move the camera to the user's location.
			Context ctx = getActivity().getApplicationContext();
			Location loc = LocationTracker.getInstance().getLocation(ctx);
			LatLng latlng = new LatLng(loc.getLatitude(), loc.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
		}
		catch (DatabaseException ex) {
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
		}
		catch (LocationException ex) {
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
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
//				Log.e(MainActivity.TAG, ex.getLocalizedMessage());
//			}
//			catch (Exception ex) {
//				Log.e(MainActivity.TAG, ex.getLocalizedMessage());
//			}
//		}
//
//		@Override
//		public void onCancelled(DatabaseError error) {
//			// Failed to read value
//			Log.e(MainActivity.TAG, "DB error: " + error.toString());
//		}
//	}


	private final class ReportAddedHandler implements ChildEventListener {
		@Override
		public void onChildAdded(@NonNull DataSnapshot dataSnapshot,
				String prevChildKey) {
			try {
				// Get the report that was added.
				String key = dataSnapshot.getKey();
				Report report = dataSnapshot.getValue(Report.class);

				// Add this report to the list of all reports and to the
				// category index that corresponds to this report's category.
				allReports.put(key, report);
				indexes.get(report.category()).put(key, report);

				// Draw a marker for this report on the map.
				MarkerOptions opts = new MarkerOptions();
				opts.position(
						new LatLng(report.getLatitude(),
								report.getLongitude()));
				opts.icon(report.category().getIcon());
				mMap.addMarker(opts);
			}
			catch (DatabaseException ex) {
				Log.e(MainActivity.TAG, ex.getLocalizedMessage());
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG, ex.getLocalizedMessage());
			}
		}

		@Override
		public void onChildChanged(@NonNull DataSnapshot dataSnapshot,
				String s) {
		}

		@Override
		public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
		}

		@Override
		public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String
				s) {
		}

		@Override
		public void onCancelled(DatabaseError error) {
			// Failed to read value
			Log.e(MainActivity.TAG, "DB error: " + error.toString());
		}
	}


	private class SpinnerChange implements ItemSelectedListener {
		@Override
		public void itemSelected(SpinWrapper source, int pos, long id) {
			showIcons();
		}
	}


	private void showIcons() {
		// Clear the map of all markers
		mMap.clear();

		// Get the user selected category to filter the reports.
		int which = spinType.getSelectedItemPosition();
		TreeMap<String, Report> index;
		if (which == 0) {
			index = allReports;
		}
		else {
			Category cat = Category.get(which);
			index = indexes.get(cat);
		}

		// Get the user selected duration to filter the reports.
		which = spinTime.getSelectedItemPosition();
		Duration durat;
		if (which == 0) {
			durat = Duration.AllTime;
		}
		else {
			durat = Duration.get(which);
		}

		// Get the current time in number of
		// milliseconds elapsed since the Epoch.
		Calendar calendar = Calendar.getInstance();
		long now = calendar.getTimeInMillis();

		// Populate the map with reports from the user selected category.
		MarkerOptions opts = new MarkerOptions();
		for (String key : index.keySet()) {
			Report report = index.get(key);
			long when = report.timestamp();
			if (durat.isWithin(when, now)) {
				opts.icon(report.category().getIcon());
				opts.position(
						new LatLng(report.getLatitude(),
								report.getLongitude()));
				mMap.addMarker(opts);
			}
		}
	}
}
