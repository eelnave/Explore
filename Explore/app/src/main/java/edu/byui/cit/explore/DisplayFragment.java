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

    /**
      This method creates an object that is used to gain access to our database.
      For more info about which methods this PinDAO contains go to edu.byui.cit.model.PinDAO
     */

	private PinDAO db(){
		Activity act = getActivity();
		Context ctx = act.getApplicationContext();
		AppDatabase db = AppDatabase.getInstance(ctx);
		PinDAO dao = db.getPinDAO();
		return dao;
	}

    /**
     * This method clears the map of all markers. It then pulls all pins out of the database
     * and stores them into a list collection. It then creates a MarkerOptions object which is
     * a class used when working with Google Maps. It defines the data behind a marker
     * on the map. We then loop through the list and define the options for each pin in the
     * list and place it on the map. One line that may cause confusion is our BitmapDescriptor.
	 * In order to understand what this is doing we have to understand what R.drawable.icon_person
	 * is. It may seem like a simple path to our image file. What this returns is actually an int.
	 * We can't display an int on the map. What the BitmapDescriptor object does is turn our
	 * path into an actual image that's displayable on the map.
     */

	void showAllPins() {
		// Clear the map of all markers.
		mMap.clear();

		// Get the list of all pins that are stored currently in the database.
		List<Pin> allPins = db().getAll();

		// For each pin, place a marker on the map.
		MarkerOptions options = new MarkerOptions();
		for (Pin pin : allPins) {
			BitmapDescriptor bitmap =
                    BitmapDescriptorFactory.fromResource(pin.getIconName());
			options.icon(bitmap);
			LatLng latLng = new LatLng(pin.getLatitude(), pin.getLongitude());
			options.position(latLng);
			options.title(latLng.latitude + " : " + latLng.longitude);
			mMap.addMarker(options);
		}
	}

    /**
    For handling events we need to implement different listeners provided for us
	by Google. Those listeners are set up as interfaces meaning we need to use an
	inner class to implement those interfaces. If you're confused about interfaces
	and inner classes ask Brother Barzee. He explains it very well. Once we have our
	class created we have to implement the methods that are defined in the interface.
	That's where we put our code. So if we take the code below line by line this is
	what happens: We declare our class, we implement the listener interface, we override
	the interface method onMapClick. The code inside of this method is the code that
	creates a new marker on the map and then uses that data to create a database object
	representing that marker.
	*/

	private final class HandleMapClick
			implements GoogleMap.OnMapClickListener {
		@Override
		public void onMapClick(LatLng latLng) {
			MarkerOptions options = new MarkerOptions();
			BitmapDescriptor bitmap =
					BitmapDescriptorFactory.fromResource(R.drawable.new_pin);
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
            Pin pin = new Pin(R.drawable.icon_person,
					latLng.latitude, latLng.longitude, now, "");
			db().insert(pin);

			//Show the pin alongside the other pins
            showAllPins();
		}
	}

	/**
	This is another listener class. This one is a little special though. It has to
	pass data to the MainActivity. So first we have to make a MainActivity object.
	For this we use the getActivity method. getActivity returns the parent activity
	that is running when the method is called. For us this is always the MainActivity.
	The only problem is that this method returns an Activity object. Not a MainActivity
	object. It's important to know that those are different things. All activities extend
	the Activity class. Meaning that we have to take what it returns and cast it down into
	a MainActivity object that we can work with. After this is done we use that object to
	capture the currently displayed fragment, we pass some data into the MainActivity via
	a method we wrote, and then we call the two methods necessary to create a context
	menu. These methods require that we pass in the view that we want them to display within.
	 */

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
