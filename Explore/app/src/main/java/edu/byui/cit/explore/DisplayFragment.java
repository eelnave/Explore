package edu.byui.cit.explore;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;

import java.util.Date;
import java.util.List;

import edu.byui.cit.exception.LocationException;
import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Pin;
import edu.byui.cit.model.PinDAO;

import static edu.byui.cit.explore.MainActivity.TAG;


public final class DisplayFragment extends CITFragment
		implements OnMapReadyCallback {

	private GoogleMap mMap;

	public DisplayFragment() {
		super();
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
	 * Manipulates the map once it is available.
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
	public void onMapReady(final GoogleMap googleMap) {
		try {
			Category.loadIcons();
			mMap = googleMap;
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			showAllPins();

			// Add a marker to the map in Sydney, Australia
			LatLng sydney = new LatLng(-33.852, 151.211);
			googleMap.addMarker(new MarkerOptions().position(sydney)
					.title("Marker in Sydney").icon(
							BitmapDescriptorFactory.fromResource(
									R.drawable.icon_antelope)));

			// Move the map to the device's current location.
			Context ctx = getActivity().getApplicationContext();
			Location loc = LocationTracker.getInstance().getLocation(ctx);
			LatLng latlng = new LatLng(loc.getLatitude(), loc.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
			mMap.moveCamera(CameraUpdateFactory.zoomTo(5));

			mMap.setBuildingsEnabled(true);

			mMap.setOnCameraIdleListener(new HandleCameraIdle());
			mMap.setOnMapClickListener(new HandleMapClick());
			mMap.setOnMarkerClickListener(new HandleMarkerClick());
		}
		catch (LocationException ex) {
			Log.e(TAG, "4: " + ex.getMessage());
		}
		catch (Exception ex) {
			Log.e(TAG, "4: " + ex.getMessage());
		}
	}
	private PinDAO db(){
		Activity act = getActivity();
		Context ctx = act.getApplicationContext();
		AppDatabase db = AppDatabase.getInstance(ctx);
		PinDAO dao = db.getPinDAO();
		return dao;
	}

	public void DeleteAllPins(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				db().clearTable();
			}
		});
	}

	void showAllPins() {
		// Clear the map of all markers.
		mMap.clear();

		// Get the list of all pins that are stored currently in the database.
		List<Pin> allPins = db().getAll();

		// For each pin, place a marker on the map.
		MarkerOptions options = new MarkerOptions();
		for (Pin pin : allPins) {
			BitmapDescriptor bitmap =
					BitmapDescriptorFactory.fromResource(R.drawable.icon_person);
			options.icon(bitmap);
			LatLng latLng = new LatLng(pin.getLatitude(), pin.getLongitude());
			options.position(latLng);
			options.title(latLng.latitude + " : " + latLng.longitude);
			mMap.addMarker(options);
		}
	}

	private final class HandleCameraIdle
			implements GoogleMap.OnCameraIdleListener {
		@Override
		public void onCameraIdle() {
			Log.e(TAG, "==camera idle==" + mMap.getCameraPosition().target);
		}
	}

	private final class HandleMapClick
			implements GoogleMap.OnMapClickListener {
		@Override
		public void onMapClick(LatLng latLng) {
			mMap.clear();

			List<Pin> allPins = db().getAll();


			MarkerOptions options = new MarkerOptions();
//			options.draggable(true);
			for (Pin pin : allPins) {
				BitmapDescriptor bitmap =
						BitmapDescriptorFactory.fromResource(R.drawable.icon_antelope);
				options.icon(bitmap);
				LatLng allLatLng = new LatLng(pin.getLatitude(), pin.getLongitude());
				options.position(allLatLng);
				options.title(allLatLng.latitude + " : " + allLatLng.longitude);
				mMap.addMarker(options);
			}

			BitmapDescriptor bitmap =
					BitmapDescriptorFactory.fromResource(R.drawable.icon_person);
			options.icon(bitmap);
			//sets the marker's location from the click
			options.position(latLng);
			//creates a title for the marker using latitude and longitude
			options.title(latLng.latitude + " : " + latLng.longitude);
			//adds marker using the options listed above
			mMap.addMarker(options);
			//moves camera center to marker
			mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

			// Insert a pin into the Pin table of the database.
			Date now = new Date();
			Pin pin = new Pin("person",
					latLng.latitude, latLng.longitude, now, "");
			db().insert(pin);
		}
	}

	private final class HandleMarkerClick
			implements GoogleMap.OnMarkerClickListener {
		@Override
		public boolean onMarkerClick(Marker marker) {
			MainActivity act = (MainActivity) getActivity();
			View container = act.findViewById(R.id.fragContainer);
			act.setClickedMarker(marker);
			act.registerForContextMenu(container);
			act.openContextMenu(container);
			return true;
		}
	}
}
