package edu.byui.cit.explore;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
//	private final ArrayList<Report> allReports;

	private GoogleMap mMap;

	public DisplayFragment() {
		super();

		// Create the list (TreeMap) that will hold all reports.
//		allReports = new ArrayList<>();

		// Create the category indexes that will hold references to the
		// reports.
	}

	@Override
	protected String getTitle() {
		return getString(R.string.appName);
	}

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.display_frag, container, false);

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
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			Category.loadIcons();

			Context ctx = getActivity().getApplicationContext();
			Location loc = LocationTracker.getInstance().getLocation(ctx);
			LatLng latlng = new LatLng(loc.getLatitude(), loc.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			mMap.moveCamera(CameraUpdateFactory.zoomTo(5));
		}
		catch (LocationException ex) {
			Log.e(MainActivity.TAG, "4: " + ex.getMessage());
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, "4: " + ex.getMessage());
		}
	}

			private void showIcons() {
		// Clear the map of all markers
		mMap.clear();

		// Get the list of reports that
		// corresponds to the user selected category.
		int which = spinType.getSelectedItemPosition();


		// Get the user selected duration to filter the reports.
		// which = spinTime.getSelectedItemPosition();
//		Duration durat;
//		if (which == 0) {
//			durat = Duration.AllTime;
//		}
//		else {
//			durat = Duration.get(which);
//		}

		// Create a sample report that has as its timestamp
		// the beginning of the user selected duration.
		long now = System.currentTimeMillis();


//		Log.i(MainActivity.TAG, "Position within list of reports: " + pos);

		// Populate the map with reports from the user selected category.

		}
	}
/*
 * When we get to it this is how we will send users to get directions.
 * we will have to send in the
 * https://www.google.com/maps/dir/?api=1&destination=43.12345,-76.12345
 */
